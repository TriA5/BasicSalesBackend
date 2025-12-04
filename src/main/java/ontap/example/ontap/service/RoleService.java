package ontap.example.ontap.service;

import java.util.List;
import java.util.UUID;

import ontap.example.ontap.dto.RoleDTO;
import ontap.example.ontap.entity.Role;

// import ontap.example.ontap.dto.RoleDTO;

public interface RoleService {
    public void createRole(Role newRole);

    public List<Role> getAllRole();

    public Role updateRoleById(UUID idRole,RoleDTO roleDTO);

    public void deleteRoleById(UUID idRole);

    public Role getRoleById(UUID idRole);
}
