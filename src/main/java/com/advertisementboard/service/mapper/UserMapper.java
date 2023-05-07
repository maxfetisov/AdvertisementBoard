package com.advertisementboard.service.mapper;

import com.advertisementboard.data.dto.user.UserDto;
import com.advertisementboard.data.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

}
