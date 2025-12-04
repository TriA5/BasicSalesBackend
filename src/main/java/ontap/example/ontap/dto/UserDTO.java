package ontap.example.ontap.dto;

import java.util.UUID;

import lombok.Data;
import ontap.example.ontap.entity.Role;

@Data
public class UserDTO {
    // private UUID id;

    private String username;

    private String password;

    private UUID roleId;
}
