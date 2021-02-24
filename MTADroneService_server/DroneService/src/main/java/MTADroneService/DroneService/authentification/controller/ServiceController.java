package MTADroneService.DroneService.authentification.controller;

import MTADroneService.DroneService.authentification.dtos.MissionInfoDTO;
import MTADroneService.DroneService.authentification.services.ServiceService;
import MTADroneService.DroneService.authentification.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/services")
@Slf4j
public class ServiceController {

    private final static String UPLOADED_FOLDER = "src\\main\\resources";

    @Autowired
    ServiceService serviceService;

    @PostMapping(value = "/search", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createMission(@RequestBody MissionInfoDTO missionInfoDTO,
                                        @RequestParam("file") MultipartFile file,
                                        RedirectAttributes redirectAttributes) {
        checkNotNull(missionInfoDTO);
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return missionInfoDTO;
    }
}
