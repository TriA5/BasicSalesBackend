package ontap.example.ontap.service.serviceImpl;

import java.util.List;
import java.util.UUID;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ontap.example.ontap.JWT.JwtService;
import ontap.example.ontap.dto.LoginDTO;
import ontap.example.ontap.dto.UserDTO;
import ontap.example.ontap.entity.Role;
import ontap.example.ontap.entity.User;
import ontap.example.ontap.exception.BadRequestException;
import ontap.example.ontap.repository.RoleRepository;
import ontap.example.ontap.repository.UserRepository;
import ontap.example.ontap.security.JwtResponse;
import ontap.example.ontap.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User createUser(UserDTO userDTO) {
        // kiểm tra tên người dùng tồn tại
        if (userRepository.existsByUsername(userDTO.getUsername()) == true) {
            throw new RuntimeException("username đã tồn tại");
        }
        // kiếm role
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role không tồn tại"));

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(role);

        return userRepository.save(user);

    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User updateUser(UUID idUser, UserDTO userDTO) {
        // set user
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        user.setUsername(userDTO.getUsername());
        // mã hóa password
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // set role
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));

        user.setRole(role);

        return userRepository.save(user);
    }

    public void deleteUser(UUID idUser) {
        if (userRepository.existsById(idUser)) {
            userRepository.deleteById(idUser);
        }
    }

    public User getUserById(UUID idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
    }

    public JwtResponse login(LoginDTO loginDTO) {
        // Xác thực thông tin đăng nhập
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(), loginDTO.getPassword()));

        // Nếu tới đây là đã authenticated thành công
        if (authentication.isAuthenticated()) {
            // Tạo JWT token
            final String jwtToken = jwtService.generateToken(loginDTO.getUsername());
            return new JwtResponse(jwtToken);
        } else {
            throw new BadRequestException("Đăng nhập thất bại");
        }
    }

}
