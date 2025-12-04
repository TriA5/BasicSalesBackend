package ontap.example.ontap.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ontap.example.ontap.dto.AddressDTO;
import ontap.example.ontap.entity.Address;
import ontap.example.ontap.service.AddressService;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/{id}")
    public Address createAddress(@PathVariable UUID id,@RequestBody Address address){
        return addressService.createAddress(id, address);
    }

    @PutMapping
    public Address updateAddress(@RequestParam UUID idUser,@RequestParam UUID idAddress,@RequestBody AddressDTO addressDTO){
        return addressService.updateAddress(idUser, idAddress, addressDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable UUID id){
         addressService.deleteAddress(id);
    }
}
