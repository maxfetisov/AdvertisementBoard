package com.advertisementboard.rest.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping
    public String start() {
        return "index";
    }

    @GetMapping("advertisements")
    public String getAdvertisementsPage() {
        return "advertisements";
    }

    @GetMapping("home")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("createAdvertisement")
    public String getCreateAdvertisementPage() {
        return "createAdvertisement";
    }

}
