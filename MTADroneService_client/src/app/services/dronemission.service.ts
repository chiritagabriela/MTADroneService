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

  startSearchMission(formValue:any): Observable<MissionModel>{
    const url = `http://localhost:8888/services/search`;
    return this.httpClient.post<MissionModel>(url, formValue)
    .pipe(map(missionModel =>{
      return missionModel;
    })
    );
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

