package com.advertisementboard.data.dto.user;

import lombok.Builder;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Builder
public class UserDto {

    private String login;

    private String name;

    @JsonIgnore
    private String password;

}
