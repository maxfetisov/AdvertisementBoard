package com.advertisementboard.service.user.impl;

import com.advertisementboard.data.dto.user.UserDto;
import com.advertisementboard.exception.entity.EntityAlreadyExistException;
import com.advertisementboard.exception.entity.EntityNotExistException;
import com.advertisementboard.repository.UserRepository;
import com.advertisementboard.service.user.UserService;
import com.advertisementboard.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public void createUser(final UserDto user) {
        if(userRepository.existsByLogin(user.getLogin()))
            throw new EntityAlreadyExistException(user.toString());
        userRepository.save(userMapper.userDtoToUser(user));
    }

    @Override
    public UserDto getUser(String login) {
        return userMapper.userToUserDto(userRepository.findByLogin(login).orElseThrow(()
                -> new EntityNotExistException(login)));
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(()
                -> new UsernameNotFoundException(login));
    }
}
