package MTADroneService.DroneService.authentification.services;

import MTADroneService.DroneService.authentification.dtos.RoleDTO;

public interface RoleService {

    void createRole(RoleDTO roleDTO);

    RoleDTO roleInfo(String roleId);

}
