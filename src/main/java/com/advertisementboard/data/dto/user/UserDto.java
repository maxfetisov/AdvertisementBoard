package com.advertisementboard.data.dto.user;

import com.advertisementboard.data.dto.role.RoleDto;
import com.advertisementboard.data.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private String login;

    private String name;

    @JsonIgnore
    private String password;

    private RoleDto role;

}
