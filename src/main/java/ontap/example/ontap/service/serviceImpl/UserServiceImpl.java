package ontap.example.ontap.service.serviceImpl;

import java.util.List;
import java.util.UUID;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ontap.example.ontap.dto.LoginDTO;
import ontap.example.ontap.dto.UserDTO;
import ontap.example.ontap.entity.Role;
import ontap.example.ontap.entity.User;
import ontap.example.ontap.repository.RoleRepository;
import ontap.example.ontap.repository.UserRepository;
import ontap.example.ontap.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUser(UserDTO userDTO){
        //kiểm tra tên người dùng tồn tại
        if(userRepository.existsByUsername(userDTO.getUsername()) == true){
            throw new RuntimeException("username đã tồn tại");
        }
        //kiếm role
        Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role không tồn tại"));

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRole(role);

        return userRepository.save(user);

    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User updateUser(UUID idUser, UserDTO userDTO){
        //set user
        User user = userRepository.findById(idUser)
                    .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        //set role
        Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(()-> new RuntimeException("Category không tồn tại"));

        user.setRole(role);

        return userRepository.save(user);
    }
    public void deleteUser(UUID idUser){
        if(userRepository.existsById(idUser)){
            userRepository.deleteById(idUser);
        }
    }
    public User getUserById(UUID idUser){
        return userRepository.findById(idUser)
               .orElseThrow(()-> new RuntimeException("User không tồn tại"));
    }

    public User login(LoginDTO loginDTO){
        //Lấy user trong db theo userName và gán vào user nếu không tìm thấy ném ra lỗi
        User user = userRepository.findByUsername(loginDTO.getUsername())
                    .orElseThrow(()-> new RuntimeException("Tên đăng nhập không đúng"));
        //vừa mới lấy user ở trong db ở trên nên mới lấy mật khẩu 
        //và so sánh với mật khẩu của FE gửi về(loginDTO.getPassWord) nếu ! ném throw
        if(!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new RuntimeException("Sai mật khẩu");
        }
        return user;
    }
}
