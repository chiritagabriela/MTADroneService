import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DronemissionService } from 'src/app/services/dronemission.service';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.scss']
})
export class ServicesComponent implements OnInit {

  submittedSearch = false;
  submittedSurveill = false;
  submittedDelivery = false;
  searchForm!:FormGroup;
  surveillanceForm!:FormGroup;
  deliveryForm!:FormGroup;
  searchFile!: File;

  constructor(private router:Router, private missionDroneService:DronemissionService,
    private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.deliveryForm = this.formBuilder.group({
      missionLatitudeStart:['',Validators.required],
      missionLatitudeEnd:['',Validators.required],
      missionLongitudeStart:['',Validators.required],
      missionLongitudeEnd:['',Validators.required],
    });

    this.surveillanceForm =  this.formBuilder.group({
      missionLatitudeEnd:['',Validators.required],
      missionLongitudeEnd:['',Validators.required]
    });

    this.searchForm = this.formBuilder.group({
      missionLatitudeEnd:['',Validators.required],
      missionLongitudeEnd:['',Validators.required],
      fileField:['',Validators.required]
    });
  }

  onSubmitSearch(){
    this.submittedSearch = true;

    if (this.searchForm.invalid) {
      return;
    }


    if (this.searchFile != null || this.searchFile != undefined) {
      this.missionDroneService.startSearchMission(this.searchForm.value, this.searchFile)
      .subscribe(
        data => {
          this.searchForm.reset();
          console.log("succes");
          this.router.navigate(['/history']);
        },
        error =>{
          alert("Please reload the page.");
        }
      );
    }

  }

  refreshChosenFile(files: FileList) {
      this.searchFile = files[0];
  }

  onSubmitSurveillance(){
    this.submittedSurveill = true;
      this.missionDroneService.startSurveilMission(this.surveillanceForm.value)
      .subscribe(
        data => {
          console.log("succes");
         this.router.navigate(['/history']);
        },
        error =>{
          alert("Please reload the page.");
        }
      );
  }

  onSubmitDelivery(){
    this.submittedDelivery = true;
    this.missionDroneService.startDeliveryMission(this.surveillanceForm.value)
      .subscribe(
        data => {
          console.log("succes");
         this.router.navigate(['/history']);
        },
        error =>{
          alert("Please reload the page.");
        }
      );
  }

  get fSearch(){
    return this.searchForm.controls;
  }

  get fSurveillance(){
    return this.surveillanceForm.controls;
  }

  get fDelivery(){
    return this.deliveryForm.controls;
  }
}
