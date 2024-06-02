package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.utils;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain.Role;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain.User;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {

    @Autowired
    private UserService userService ;

    public boolean isAdminOrResourceOwner(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().toArray()[0].toString();
        User user = userService.findByEmail(username, role);
        // Verifica si el usuario es nulo
        if (user == null) {
            return false;
        }
        return user.getId().equals(id) || user.getRole().equals(Role.ADMIN) ;
    }

    public boolean isAdminOrStaff() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().toArray()[0].toString();
        User user = userService.findByEmail(username, role);
        // Verifica si el usuario es nulo
        if (user == null) {
            return false;
        }
        return user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.STAFF);
    }

    public boolean isAdminOrStaffOrResourceOwner(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().toArray()[0].toString();
        User user = userService.findByEmail(username, role);
        // Verifica si el usuario es nulo
        if (user == null) {
            return false;
        }
        return user.getId().equals(id) || user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.STAFF);
    }
    

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        catch (ClassCastException e) {
            return null;
        }
    }



}