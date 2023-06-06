package com.advertisementboard.rest.api;

import com.advertisementboard.data.dto.advertisement.AdvertisementDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageRequestDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementPageResponseDto;
import com.advertisementboard.data.dto.advertisement.AdvertisementRequestDto;
import com.advertisementboard.data.entity.User;
import com.advertisementboard.data.enumeration.UserRole;
import com.advertisementboard.exception.role.NoPrivilegeException;
import com.advertisementboard.service.advertisement.AdvertisementService;
import com.advertisementboard.service.mapper.AdvertisementRequestMapper;
import com.advertisementboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    private final AdvertisementRequestMapper advertisementRequestMapper;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<AdvertisementDto>> getAdvertisementsByFilter() {
        return ResponseEntity.ok().body(advertisementService.getAdvertisements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> getAdvertisementsByFilter(@PathVariable("id") final long id) {
        return ResponseEntity.ok().body(advertisementService.getAdvertisement(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<AdvertisementPageResponseDto> getAdvertisementsByFilter(
            @RequestBody final AdvertisementPageRequestDto request
    ) {
        return ResponseEntity.ok().body(advertisementService.getAdvertisements(request));
    }

    @PostMapping
    public ResponseEntity<Long> createAdvertisement(
            final Authentication authentication,
            @RequestBody final AdvertisementRequestDto request
    ) {
        return new ResponseEntity<>(
                advertisementService.createAdvertisement(
                        advertisementRequestMapper.advertisementRequestDtoToAdvertisementDto(request),
                        userService.getUser(((User)authentication.getPrincipal()).getLogin())
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<?> updateAdvertisement(
            final Authentication authentication,
            @RequestBody final AdvertisementRequestDto request
    ) {
        User user = (User)authentication.getPrincipal();
        if(!(user.getRole().getName().equals(UserRole.ADMINISTRATOR.name())
                || List.of(UserRole.USER.name(), UserRole.MODERATOR.name()).contains(user.getRole().getName())
                && advertisementService.getAdvertisement(request.getId()).getUser().getLogin().equals(user.getLogin())))
            throw new NoPrivilegeException();
        advertisementService.updateAdvertisement(
                advertisementRequestMapper.advertisementRequestDtoToAdvertisementDto(request),
                userService.getUser(((User)authentication.getPrincipal()).getLogin())
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvertisement(
            final Authentication authentication,
            @PathVariable("id") final Long id
    ) {
        User user = (User)authentication.getPrincipal();
        if(!(user.getRole().getName().equals(UserRole.ADMINISTRATOR.name())
        || List.of(UserRole.USER.name(), UserRole.MODERATOR.name()).contains(user.getRole().getName())
                && advertisementService.getAdvertisement(id).getUser().getLogin().equals(user.getLogin())))
            throw new NoPrivilegeException();
        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectAdvertisement(@PathVariable("id") final Long id) {
        advertisementService.rejectAdvertisement(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmAdvertisement(@PathVariable("id") final Long id) {
        advertisementService.confirmAdvertisement(id);
        return ResponseEntity.ok().build();
    }

}
