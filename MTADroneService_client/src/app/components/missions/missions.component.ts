import { Component, NgModule, OnInit } from '@angular/core';
import { AgmCoreModule } from '@agm/core';



@Component({
  selector: 'app-missions',
  templateUrl: './missions.component.html',
  styleUrls: ['./missions.component.scss']
})
export class MissionsComponent implements OnInit {

  lat = 22.4064172;
  long = 69.0750171;
 
  markers = [
    {
        lat: 21.1594627,
        lng: 72.6822083,
        label: 'Surat'
    },
    {
        lat: 23.0204978,
        lng: 72.4396548,
        label: 'Ahmedabad'
    },
    {
        lat: 22.2736308,
        lng: 70.7512555,
        label: 'Rajkot'
    }
];

  constructor() { }

  ngOnInit(): void {
  }

}
