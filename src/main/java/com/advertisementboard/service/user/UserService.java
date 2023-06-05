package com.advertisementboard.service.user;

import com.advertisementboard.data.dto.user.UserDto;

public interface UserService {

    void createUser(UserDto user);

    UserDto getUser(String login);

}
