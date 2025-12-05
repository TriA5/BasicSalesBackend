package ontap.example.ontap.userdetail;

import org.springframework.security.core.userdetails.UserDetailsService;

import ontap.example.ontap.entity.User;

public interface UserSecurityService extends UserDetailsService {
    public User findByUsername(String username);
}
