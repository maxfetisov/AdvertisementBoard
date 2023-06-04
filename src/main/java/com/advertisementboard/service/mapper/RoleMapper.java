package com.advertisementboard.service.mapper;

import com.advertisementboard.data.dto.role.RoleDto;
import com.advertisementboard.data.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {

    Role roleDtoToRole(RoleDto roleDto);

    RoleDto roleToRoleDto(Role role);

}
