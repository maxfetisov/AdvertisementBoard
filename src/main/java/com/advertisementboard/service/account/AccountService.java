package com.advertisementboard.service.account;

import com.advertisementboard.data.dto.authentication.AuthenticationRequestDto;
import com.advertisementboard.data.dto.authentication.AuthenticationResponseDto;
import com.advertisementboard.data.dto.authentication.RegistrationRequestDto;
import com.advertisementboard.data.dto.role.RoleDto;

public interface AccountService {

    AuthenticationResponseDto register(RegistrationRequestDto request);

    AuthenticationResponseDto register(RegistrationRequestDto request, RoleDto role);

    AuthenticationResponseDto authenticate(AuthenticationRequestDto request);

}
