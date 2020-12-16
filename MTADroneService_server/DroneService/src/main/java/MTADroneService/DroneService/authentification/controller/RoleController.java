package MTADroneService.DroneService.authentification.controller;

import MTADroneService.DroneService.authentification.dtos.RoleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/role*")
public class RoleController {

    @PreAuthorize("hasAnyRole('USER','ANONYMOUS')")
    @PostMapping("/create")
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO){

        return null;
    }

    @GetMapping("/info/{roleId}")
    public RoleDTO getRoleInfo(@PathVariable String roleId){

        return null;
    }
}
