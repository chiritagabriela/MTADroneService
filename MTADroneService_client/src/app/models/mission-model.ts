import {DroneModel} from 'src/app/models/drone-model'
export class MissionModel{
    missionID:string="";
    missionDrone:DroneModel=new DroneModel("","","");
    missionType:string="";
    missionLongitudeStart:string="";
    missionLatitudeEnd:string="";
    missionLongitudeEnd:string="";
    missionLatitudeStart:string="";
    missionDate:string="";
    missionStatus:string="";
}