package com.advertisementboard.startup;

import com.advertisementboard.service.initialization.InitializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartUp implements CommandLineRunner {

    private final InitializationService initializationService;

    @Override
    public void run(String... args) throws Exception {
        initializationService.initialize();
    }

}
