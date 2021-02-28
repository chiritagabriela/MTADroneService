import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import { Observable, of, empty, BehaviorSubject } from 'rxjs';
import { MissionModel } from '../models/mission-model';
@Injectable({
  providedIn: 'root'
})
export class DronemissionService {

  constructor(private httpClient:HttpClient) { }

  startSearchMission(formValue:any, file:any): Observable<boolean>{
    const formData = new FormData();  
    formData.append("file", file, file.name); 
    const url = `http://localhost:8888/services/search?longitude=${formValue.missionLatitudeEnd}&latitude=${formValue.missionLatitudeEnd}`;
    return this.httpClient.post(url,formData)
    .pipe(map(() => {return true}));
  }

  startSurveilMission(formValue:any): Observable<MissionModel>{
    const url = `http://localhost:8888/services/surveil`;
    return this.httpClient.post<MissionModel>(url, formValue)
    .pipe(map(missionModel =>{
      return missionModel;
    })
    );
  }

  startDeliveryMission(formValue:any): Observable<MissionModel>{
    const url = `http://localhost:8888/services/delivery`;
    return this.httpClient.post<MissionModel>(url, formValue)
    .pipe(map(missionModel =>{
      return missionModel;
    })
    );
  }
}

