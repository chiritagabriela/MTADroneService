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


  constructor(private router:Router, private missionDroneService:DronemissionService,
    private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    window.scrollTo(0, 960);
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
      missionLongitudeEnd:['',Validators.required]
    });
  }

  onSubmitSearch(){
    this.submittedSearch = true;
      this.missionDroneService.startSearchMission(this.searchForm.value)
      .subscribe(
        data => {
          console.log("succes");
         this.router.navigate(['/missions']);
        },
        error =>{
          alert("Please reload the page.");
        }
      );
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
    this.missionDroneService.startDeliveryMission(this.deliveryForm.value)
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
