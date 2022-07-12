package com.mhj.ms.acess.control.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mhj.ms.acess.control.auth.entity.Role;
import com.mhj.ms.acess.control.auth.entity.User;
import com.mhj.ms.acess.control.dto.response.MongoUserDetails;
import com.mhj.ms.acess.control.exception.CustomException;
import com.mhj.ms.acess.control.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null || user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
        String [] authorities = new String[user.getRoles().size()];
        int count=0;
        for (Role role : user.getRoles()) {
            //NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically for us.
            //Since we are using custom token using JWT we should add ROLE_ prefix
            authorities[count] = "ROLE_"+role.getDescricao();
            count++;
        }
        MongoUserDetails userDetails = new MongoUserDetails(user.getEmail(),user.getPassword(),user.getActive(),
                user.isLoacked(), user.isExpired(),user.isEnabled(),authorities);
        return userDetails;
    }

}
