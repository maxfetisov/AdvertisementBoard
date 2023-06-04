package com.advertisementboard.service;

import com.advertisementboard.data.dto.role.RoleDto;
import com.advertisementboard.data.enumeration.UserRole;

public interface RoleService {

    Integer createRole(RoleDto role);

    RoleDto getRoleByName(UserRole name);

}
