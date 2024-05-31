package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.infrastructure.StaffRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.exceptions.UnauthorizeOperationException;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.infrastructure.TenantRepository;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository<User> userRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private StaffRepository staffRepository;

    public User findByEmail(String username, String role) {
        User user;
        if (role.equals("ROLE_STAFF"))
            user = staffRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        else
            user = tenantRepository.findByEmail(username).orElseThrow(() -> new UnauthorizeOperationException("User nottt found"));

        return user;
    }
    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return (UserDetails) user;
        };
}}
