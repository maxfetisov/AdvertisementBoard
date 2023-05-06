package com.advertisementboard.service.impl;

import com.advertisementboard.data.entity.Role;
import com.advertisementboard.data.entity.User;
import com.advertisementboard.data.enumeration.UserRole;
import com.advertisementboard.repository.UserRepository;
import com.advertisementboard.service.UserService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    //private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(final User user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.builder().name(UserRole.USER).build());
        userRepository.save(user);
    }

}
