package com.advertisementboard.service.user;

import com.advertisementboard.data.dto.user.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createUser(UserDto user);

    UserDto getUser(String login);

}
