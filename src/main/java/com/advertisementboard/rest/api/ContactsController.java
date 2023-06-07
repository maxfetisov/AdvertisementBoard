package com.advertisementboard.rest.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
public class ContactsController {

    @Value("#{${advertisement-board.contacts}}")
    private Map<String, String> contacts;

    @GetMapping
    public ResponseEntity<Map<String, String>> getContacts() {
        return ResponseEntity.ok().body(contacts);
    }

}
