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

  submitted = false;
  searchForm!:FormGroup;
  surveillanceForm!:FormGroup;
  deliveryForm!:FormGroup;

  constructor(private router:Router, private missionDroneService:DronemissionService,
    private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.deliveryForm = this.formBuilder.group({
      longitudePickUp:['',Validators.required],
      latitudePickUp:['',Validators.required],
      longitudeDelivery:['',Validators.required],
      latitudeDelivery:['',Validators.required]
    });

    this.surveillanceForm =  this.formBuilder.group({
      longitudeSurveill:['',Validators.required],
      latitudeSurveill:['',Validators.required]
    });

    this.searchForm = this.formBuilder.group({
      longitudeSearch:['',Validators.required],
      latitudeSearch:['',Validators.required]
    });
  }

  onSubmitSearch(){
    this.submitted = true;

    if (this.searchForm.invalid) {
      return;
    }

    this.missionDroneService.startSearchMission(this.searchForm)
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

  onSubmitSurveillance(){

  }
  onSubmitDelivery(){

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
