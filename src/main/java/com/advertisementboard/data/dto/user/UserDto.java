package com.advertisementboard.data.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String login;

    private String name;

    @JsonIgnore
    private String password;

}
