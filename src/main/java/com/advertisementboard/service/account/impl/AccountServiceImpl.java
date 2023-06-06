package com.advertisementboard.service.account.impl;

import com.advertisementboard.data.dto.authentication.AuthenticationRequestDto;
import com.advertisementboard.data.dto.authentication.AuthenticationResponseDto;
import com.advertisementboard.data.dto.authentication.RegistrationRequestDto;
import com.advertisementboard.data.dto.role.RoleDto;
import com.advertisementboard.data.dto.user.UserDto;
import com.advertisementboard.data.enumeration.UserRole;
import com.advertisementboard.service.account.AccountService;
import com.advertisementboard.service.mapper.UserMapper;
import com.advertisementboard.service.security.JwtService;
import com.advertisementboard.service.user.RoleService;
import com.advertisementboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDto register(final RegistrationRequestDto request) {
        return register(request, roleService.getRoleByName(UserRole.USER));
    }

    @Override
    public AuthenticationResponseDto register(final RegistrationRequestDto request, RoleDto role) {
        UserDto user = UserDto.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(role)
                .build();
        userService.createUser(user);
        return AuthenticationResponseDto.builder()
                .token("Bearer " + jwtService.generateToken(userMapper.userDtoToUser(user)))
                .build();
    }

    @Override
    public AuthenticationResponseDto authenticate(final AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );
        UserDto user = userService.getUser(request.getLogin());
        return AuthenticationResponseDto.builder()
                .token("Bearer " + jwtService.generateToken(userMapper.userDtoToUser(user)))
                .build();
    }

}
