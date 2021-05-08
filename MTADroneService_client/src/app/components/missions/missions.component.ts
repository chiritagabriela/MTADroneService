import { Component, NgModule, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MissionModel } from 'src/app/models/mission-model';
import { DronemissionService } from 'src/app/services/dronemission.service';
import { DomSanitizer} from '@angular/platform-browser'; 
import { saveAs} from 'file-saver';

@Component({
  selector: 'app-missions',
  templateUrl: './missions.component.html',
  styleUrls: ['./missions.component.scss']
})
export class MissionsComponent implements OnInit {

  lat = 45.139659;
  long = 27.486923;
  pollingInterval!: any;
  droneID = "";
  missionModel!: any;
  imageObject: Array<any> = []
  feDroneImages: Array<string> = [];
  image!:any;
  imageType : string = 'data:image/PNG;base64,'; 
  nrImages! : number;
  missionStart = false;
  missionStatus!:any;
  serverDroneImages: string[] = [];
  downloadableImages: string[] = [];

  constructor(private formBuilder: FormBuilder, private dronemissionService: DronemissionService, 
    private router: Router, private sanitizer:DomSanitizer){
      this.nrImages = 0;
  }

  markers = [
    {
      currentLatitude: 45.139659,
      currentLongitude: 27.486923,
      label: 'current'
    }];

  ngOnInit(): void {
    window.scrollTo(0, 960);
    this.getCurrentMission();
  }
  
  updateDronePosition(){
    this.dronemissionService.getCurrentPosition(this.missionModel.missionDroneInfo.droneID).subscribe(
      data => {
        if(data.currentDroneCoordinates.missionStatus == "FINISHED"){
          for(let imageName in this.serverDroneImages) {
            let image = this.imageObject.filter(x => x.title === imageName)
            let img = image[0];
            let im2 = img.image;
            saveAs(img.image.toString(), imageName);
          }
          this.imageObject = [];
          this.feDroneImages = [];
          clearInterval(this.pollingInterval);
          this.router.navigate(['/history']);
        }

        this.markers[0].currentLatitude = parseInt(data.currentDroneCoordinates.currentLatitude);
        this.markers[0].currentLongitude = parseInt(data.currentDroneCoordinates.currentLongitude);
        this.missionStatus = data.currentDroneCoordinates.missionStatus;
 
        this.serverDroneImages = data.currentDroneCoordinates.images;
  
        for(let imageName in this.serverDroneImages) {
          this.downloadImage(imageName, '.png');
        }
      },
      error =>{
        alert("Please reload the page.");
      }
    );
  }

  downloadImage(imageName: string, extension: string) {
    if(this.feDroneImages.includes(imageName)) return
    this.feDroneImages.push(imageName);
      this.dronemissionService.downloadImage(imageName + extension).subscribe(
        data => {
          if(data.content !== null){
            this.image = this.imageType + data.content;
            this.imageObject.push({
              image: this.image,
              thumbImage: this.image,
              alt: '',
              title: imageName
            });
            this.imageObject.sort((imageObj1, imageObj2) => imageObj1.title > imageObj2.title ? 1 : -1);           
          }
        },
        error =>{
          alert("Please reload the page.");
        }
      );
  }

  pollDroneCurrentPosition(){
    this.pollingInterval = setInterval(() => this.updateDronePosition(), 5000);
  }

  getCurrentMission(){ this.dronemissionService.getCurrentMission().subscribe(
    data => {
      this.missionModel = data;
      if(this.missionModel !== null){
        this.missionStart = true;
        this.pollDroneCurrentPosition();
      }
    },
    error =>{
      alert("Please reload the page.");
    }
  );
  }

  ngOnDestroy() {
    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
    }
  }


}