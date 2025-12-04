package ontap.example.ontap.service;

import java.util.List;
import java.util.UUID;

import ontap.example.ontap.dto.LoginDTO;
import ontap.example.ontap.dto.UserDTO;
import ontap.example.ontap.entity.Address;
import ontap.example.ontap.entity.User;

public interface UserService {
    public User createUser(UserDTO userDTO);

    public List<User> getAllUser();

    public User updateUser(UUID idUser, UserDTO userDTO);

    public void deleteUser(UUID idUser);

    public User getUserById(UUID idUser);

    public User login(LoginDTO loginDTO);
}
