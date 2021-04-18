import {DroneModel} from 'src/app/models/drone-model'
import { DroneCoordinatesModel } from './drone-coord-model';
export class MissionModel{
    missionID:string="";
    missionUserID:string="";
    missionDroneInfo!:DroneModel;
    missionType:string="";
    missionLatitudeEnd:string="";
    missionLongitudeEnd:string="";
    missionDate:string="";
    missionStatus:string="";
}