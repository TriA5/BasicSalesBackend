package ontap.example.ontap.service;

import java.util.List;
import java.util.UUID;

import ontap.example.ontap.dto.AddressDTO;
import ontap.example.ontap.entity.Address;

public interface AddressService {
    public Address createAddress(UUID idUser,Address newAddress);

    public Address updateAddress(UUID idUser,UUID idAddress, AddressDTO addressDTO);

    public void deleteAddress(UUID idAddress);
}
