package ontap.example.ontap.userdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ontap.example.ontap.entity.Role;
import ontap.example.ontap.entity.User;
import ontap.example.ontap.exception.BadRequestException;
import ontap.example.ontap.repository.RoleRepository;
import ontap.example.ontap.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("username không tồn tại"));
    }

    // Khi "dap" ở Security Configuration được gọi thì nó sẽ chay hàm này để lấy ra
    // user trong csdl
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Tài khoản không tồn tại!");
        }

        org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), roleToAuthorities(user.getRole()));
        return userDetail;
    }

    // Hàm để lấy role
    private Collection<? extends GrantedAuthority> roleToAuthorities(Role role) {
        return java.util.Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + role.getNameRole()));
    }

}
