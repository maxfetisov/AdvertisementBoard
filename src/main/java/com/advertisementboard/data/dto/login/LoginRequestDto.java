package com.advertisementboard.data.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDto {

    private String login;

    private String password;

}
