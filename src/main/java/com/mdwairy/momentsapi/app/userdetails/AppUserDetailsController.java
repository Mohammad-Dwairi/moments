package com.mdwairy.momentsapi.app.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/details", consumes = APPLICATION_JSON_VALUE)
public class AppUserDetailsController {

    private final AppUserDetailsService detailsService;

}
