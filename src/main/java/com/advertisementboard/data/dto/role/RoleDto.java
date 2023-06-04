package com.advertisementboard.data.dto.role;

import com.advertisementboard.data.enumeration.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {

    private Integer id;

    private String name;

}
