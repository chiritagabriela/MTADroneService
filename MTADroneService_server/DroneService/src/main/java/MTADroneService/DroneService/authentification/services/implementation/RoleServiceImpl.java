package MTADroneService.DroneService.authentification.services.implementation;

import MTADroneService.DroneService.authentification.daos.RoleDAO;
import MTADroneService.DroneService.authentification.dtos.RoleDTO;
import MTADroneService.DroneService.authentification.models.RoleModel;
import MTADroneService.DroneService.authentification.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
        RoleDAO roleDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void createRole(RoleDTO roleDTO) {

        RoleModel roleModel = modelMapper.map(roleDTO, RoleModel.class);

        roleDAO.save(roleModel);

        modelMapper.map(roleModel, roleDTO);
    }

    @Override
    public RoleDTO roleInfo(String roleId) {

        Optional<RoleModel> roleModel = roleDAO.findById(roleId);

        if (roleModel.isPresent()) {
            final RoleModel markdownRoleModel = roleModel.get();

            return modelMapper.map(markdownRoleModel, RoleDTO.class);
        }

        return null;
    }
}
