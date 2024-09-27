package com.example.atc.domain.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsOptionsController {

    @RequestMapping(value = "/api/**", method = RequestMethod.OPTIONS)
    public void handleOptions() {
        // No need to return anything, just allow the request to pass
    }
}
