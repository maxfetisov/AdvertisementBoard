package com.advertisementboard.service.initialization.impl;

import com.advertisementboard.data.dto.role.RoleDto;
import com.advertisementboard.data.dto.user.UserDto;
import com.advertisementboard.data.enumeration.UserRole;
import com.advertisementboard.exception.entity.EntityAlreadyExistException;
import com.advertisementboard.service.initialization.InitializationService;
import com.advertisementboard.service.user.RoleService;
import com.advertisementboard.service.user.UserService;
import com.advertisementboard.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitializationServiceImpl implements InitializationService {

    private final UserService userService;

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    @Override
    public void initialize() {

        initializeRoles();
        initializeUsers();
    }

    private void initializeRoles() {
        List.of(UserRole.USER, UserRole.MODERATOR, UserRole.ADMINISTRATOR).forEach(this::initializeRole);
    }

    private void initializeUsers() {
        List.of(
                UserDto.builder()
                        .login("admin")
                        .name("admin")
                        .password("admin")
                        .role(roleMapper.roleDtoToRole(roleService.getRoleByName(UserRole.ADMINISTRATOR)))
                        .build(),
                UserDto.builder()
                        .login("moderator")
                        .name("moderator")
                        .password("moderator")
                        .role(roleMapper.roleDtoToRole(roleService.getRoleByName(UserRole.MODERATOR)))
                        .build(),
                UserDto.builder()
                        .login("user")
                        .name("user")
                        .password("user")
                        .role(roleMapper.roleDtoToRole(roleService.getRoleByName(UserRole.USER)))
                        .build()
        ).forEach(this::initializeUser);
    }

    private void initializeRole(UserRole role) {
        try {
            roleService.createRole(RoleDto.builder().name(role.toString()).build());
        } catch (EntityAlreadyExistException e) {
            log.info(e.getMessage());
        }
    }

    private void initializeUser(UserDto user) {
        try {
            userService.createUser(user);
        } catch (EntityAlreadyExistException e) {
            log.info(e.getMessage());
        }
    }

}
