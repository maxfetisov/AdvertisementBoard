package com.advertisementboard.service.impl;

import com.advertisementboard.data.dto.user.UserDto;
import com.advertisementboard.data.entity.Role;
import com.advertisementboard.data.entity.User;
import com.advertisementboard.data.enumeration.UserRole;
import com.advertisementboard.exception.entity.EntityNotExistException;
import com.advertisementboard.repository.UserRepository;
import com.advertisementboard.service.UserService;
import com.advertisementboard.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    //private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(final UserDto user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userMapper.userDtoToUser(user));
    }

    @Override
    public UserDto getUser(String login) {
        return userMapper.userToUserDto(userRepository.findByLogin(login).orElseThrow(()
                -> new EntityNotExistException(login)));
    }

}
