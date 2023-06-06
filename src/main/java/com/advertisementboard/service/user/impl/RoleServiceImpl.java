package com.advertisementboard.service.user.impl;

import com.advertisementboard.data.dto.role.RoleDto;
import com.advertisementboard.data.enumeration.UserRole;
import com.advertisementboard.exception.entity.EntityAlreadyExistException;
import com.advertisementboard.exception.entity.EntityNotExistException;
import com.advertisementboard.repository.RoleRepository;
import com.advertisementboard.service.user.RoleService;
import com.advertisementboard.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Override
    public Integer createRole(RoleDto role) {
        if(roleRepository.existsByName(role.getName()))
            throw new EntityAlreadyExistException(role.toString());
        return roleRepository.save(roleMapper.roleDtoToRole(role)).getId();
    }

    @Override
    public RoleDto getRoleByName(UserRole name) {
        return roleMapper.roleToRoleDto(roleRepository.findByName(name.toString()).orElseThrow(()
                -> new EntityNotExistException("name", name.toString())));
    }

}
