import { Component, OnInit } from '@angular/core';
import { AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MissionModel } from 'src/app/models/mission-model';
import { DronemissionService } from 'src/app/services/dronemission.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})

export class HistoryComponent implements OnInit {

  missionArray!:any;

  constructor(private formBuilder: FormBuilder, private dronemissionService: DronemissionService, private router: Router){

  }

  ngOnInit(): void {
    window.scrollTo(0, 960);
    this.updateHistory();
  }

  updateHistory(){
    this.dronemissionService.getAllMissions().subscribe(
      data => {
        this.missionArray = data;
      },
      error =>{
        alert("Please reload the page.");
      }
    );
  }

}
