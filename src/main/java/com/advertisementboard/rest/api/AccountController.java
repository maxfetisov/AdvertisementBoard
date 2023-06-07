package com.advertisementboard.rest.api;

import com.advertisementboard.data.dto.authentication.AuthenticationRequestDto;
import com.advertisementboard.data.dto.authentication.AuthenticationResponseDto;
import com.advertisementboard.data.dto.authentication.RegistrationRequestDto;
import com.advertisementboard.data.dto.role.RoleDto;
import com.advertisementboard.data.dto.user.UserDto;
import com.advertisementboard.data.entity.User;
import com.advertisementboard.exception.athentication.NoAuthenticationException;
import com.advertisementboard.exception.role.NoPrivilegeException;
import com.advertisementboard.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<UserDto> getAccount(final Authentication authentication) {
        if(authentication == null)
            throw new NoAuthenticationException();
        final User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(UserDto.builder()
                .login(user.getLogin())
                .name(user.getName())
                .role(RoleDto.builder()
                        .id(user.getRole().getId())
                        .name(user.getRole().getName())
                        .build())
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody final RegistrationRequestDto request) {
        return ResponseEntity.ok(accountService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody final AuthenticationRequestDto request) {
        return ResponseEntity.ok(accountService.authenticate(request));
    }

}
