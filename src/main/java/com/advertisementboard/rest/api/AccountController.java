package com.advertisementboard.rest.api;

import com.advertisementboard.data.dto.authentication.AuthenticationRequestDto;
import com.advertisementboard.data.dto.authentication.AuthenticationResponseDto;
import com.advertisementboard.data.dto.authentication.RegistrationRequestDto;
import com.advertisementboard.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody final RegistrationRequestDto request){
        return ResponseEntity.ok(accountService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody final AuthenticationRequestDto request){
        return ResponseEntity.ok(accountService.authenticate(request));
    }

}
