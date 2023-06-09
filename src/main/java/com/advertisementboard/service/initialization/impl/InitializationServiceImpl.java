package com.advertisementboard.service.initialization.impl;

import com.advertisementboard.data.dto.authentication.RegistrationRequestDto;
import com.advertisementboard.data.dto.category.CategoryDto;
import com.advertisementboard.data.dto.role.RoleDto;
import com.advertisementboard.data.enumeration.UserRole;
import com.advertisementboard.exception.entity.EntityAlreadyExistException;
import com.advertisementboard.service.account.AccountService;
import com.advertisementboard.service.category.CategoryService;
import com.advertisementboard.service.initialization.InitializationService;
import com.advertisementboard.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitializationServiceImpl implements InitializationService {

    private final AccountService accountService;

    private final RoleService roleService;

    private final CategoryService categoryService;

    @Override
    public void initialize() {

        initializeRoles();
        initializeUsers();
        initializeCategories();
    }

    private void initializeCategories() {
        List.of(
                CategoryDto.builder()
                        .name("Недвижимость")
                        .description("Описание недвижимости")
                        .build(),
                CategoryDto.builder()
                        .name("Работа")
                        .description("Описание работы")
                        .build(),
                CategoryDto.builder()
                        .name("Товары")
                        .description("Описание товаров")
                        .build(),
                CategoryDto.builder()
                        .name("Услуги")
                        .description("Описание услуг")
                        .build(),
                CategoryDto.builder()
                        .name("Бизнес")
                        .description("Описание бизнесов")
                        .build(),
                CategoryDto.builder()
                        .name("Животные")
                        .description("Описание животных")
                        .build(),
                CategoryDto.builder()
                        .name("Транспорт")
                        .description("Описание транспорта")
                        .build(),
                CategoryDto.builder()
                        .name("Зарубежная недвижимость")
                        .description("Описание зарубежной недвижимости")
                        .build()
        ).forEach(this::initializeCategory);
    }

    private void initializeRoles() {
        List.of(UserRole.USER, UserRole.MODERATOR, UserRole.ADMINISTRATOR).forEach(this::initializeRole);
    }

    private void initializeUsers() {
        initializeUser(
                RegistrationRequestDto.builder()
                        .login("admin")
                        .name("admin")
                        .password("admin")
                        .build(),
                roleService.getRoleByName(UserRole.ADMINISTRATOR)
        );
        initializeUser(
                RegistrationRequestDto.builder()
                        .login("moderator")
                        .name("moderator")
                        .password("moderator")
                        .build(),
                roleService.getRoleByName(UserRole.MODERATOR)
        );
        initializeUser(
                RegistrationRequestDto.builder()
                        .login("user")
                        .name("user")
                        .password("user")
                        .build(),
                roleService.getRoleByName(UserRole.USER)
        );
    }

    private void initializeCategory(final CategoryDto category) {
        try {
            categoryService.createCategory(category);
        } catch (EntityAlreadyExistException e) {
            log.info(e.getMessage());
        }
    }

    private void initializeRole(final UserRole role) {
        try {
            roleService.createRole(RoleDto.builder().name(role.toString()).build());
        } catch (EntityAlreadyExistException e) {
            log.info(e.getMessage());
        }
    }

    private void initializeUser(final RegistrationRequestDto request, final RoleDto role) {
        try {
            accountService.register(request, role);
        } catch (EntityAlreadyExistException e) {
            log.info(e.getMessage());
        }
    }

}
