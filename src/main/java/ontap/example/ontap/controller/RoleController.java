package ontap.example.ontap.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ontap.example.ontap.dto.RoleDTO;
import ontap.example.ontap.entity.Role;
import ontap.example.ontap.service.RoleService;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public void createRole(@RequestBody Role newRole){
         roleService.createRole(newRole);
    }

    @GetMapping
    public List<Role> getAllRole(){
        return roleService.getAllRole();
    }

    @PutMapping("/{id}")
    public Role updatRole(@PathVariable UUID id,@RequestBody RoleDTO roleDTO){
        return roleService.updateRoleById(id,roleDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRoleById(@PathVariable UUID id){
         roleService.deleteRoleById(id);
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable UUID id){
        return roleService.getRoleById(id);
    }
}
