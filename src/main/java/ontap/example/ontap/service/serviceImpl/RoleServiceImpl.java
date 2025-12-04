package ontap.example.ontap.service.serviceImpl;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ontap.example.ontap.dto.RoleDTO;
import ontap.example.ontap.entity.Role;
import ontap.example.ontap.repository.RoleRepository;
import ontap.example.ontap.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void createRole(Role newRole) {
         roleRepository.save(newRole);
    }

    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }

    public Role updateRoleById(UUID idRole,RoleDTO roleDTO){
        Role role = roleRepository.findById(idRole)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy role"));
        role.setNameRole(roleDTO.getNameRole());
        return roleRepository.save(role);
    }

    public void deleteRoleById(UUID idRole){
        if(roleRepository.existsById(idRole)){
            roleRepository.deleteById(idRole);
        }
    }

    public Role getRoleById(UUID idRole){
        return roleRepository.findById(idRole)
               .orElseThrow(() -> new RuntimeException("Role không tồn tại"));
    }
}
