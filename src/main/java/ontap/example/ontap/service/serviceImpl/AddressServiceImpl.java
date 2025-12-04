package ontap.example.ontap.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ontap.example.ontap.dto.AddressDTO;
import ontap.example.ontap.entity.Address;
import ontap.example.ontap.entity.User;
import ontap.example.ontap.exception.BadRequestException;
import ontap.example.ontap.repository.AddressRepository;
import ontap.example.ontap.repository.UserRepository;
import ontap.example.ontap.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public Address createAddress(UUID idUser, Address newAddress) {
        // Kiểm tra user có tồn tại không
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new BadRequestException("User không tồn tại"));

        newAddress.setUser(user);
        // Kiểm tra địa chỉ mặc định hiện tại (nếu có)
        Optional<Address> currentDefault = addressRepository.findByUserIdAndIsDefaultTrue(idUser);

        // Nếu user chưa có default address → auto set newAddress = default
        if (currentDefault.isEmpty()) {
            newAddress.setDefault(true);
        } else {
            // Nếu user đã có default → mà newAddress yêu cầu làm default
            if (newAddress.isDefault()) {
                Address oldDefault = currentDefault.get();
                oldDefault.setDefault(false);
                addressRepository.save(oldDefault); // cập nhật default cũ
            }
        }
        return addressRepository.save(newAddress);
    }

    public Address updateAddress(UUID idUser, UUID idAddress, AddressDTO addressDTO) {
        // Kiểm tra user có tồn tại không
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new BadRequestException("User không tồn tại"));

        Address address = addressRepository.findById(idAddress)
                .orElseThrow(() -> new BadRequestException("Address không tồn tại"));

        address.setPhone(addressDTO.getPhone());
        address.setStreet(addressDTO.getStreet());
        address.setWard(addressDTO.getWard());
        address.setDistrict(addressDTO.getDistrict());
        address.setCity(addressDTO.getCity());

        // nếu user muốn đổi thành default = true
        if (addressDTO.isDefault()) {

            // Tìm địa chỉ có của người dùng có default = true set lại false
            Optional<Address> oldDefault = addressRepository.findByUserIdAndIsDefaultTrue(idUser);

            // nếu tồn tại
            if (oldDefault.isPresent()) {
                // Nếu default cũ KHÔNG PHẢI address đang update → tắt default cũ
                if (!oldDefault.get().getId().equals(idAddress)) {
                    Address oldAddress = oldDefault.get();
                    oldAddress.setDefault(false);
                    addressRepository.save(oldAddress);
                }
            }
            //xét default = true
            address.setDefault(true);

        } else {
            address.setDefault(false);
        }
        return addressRepository.save(address);
    }

    public void deleteAddress(UUID idAddress){
        if(addressRepository.existsById(idAddress)){
            addressRepository.deleteById(idAddress);
        }else{
            throw new BadRequestException("Address không tồn tại");
        }
    }
}
