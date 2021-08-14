package com.mdwairy.momentsapi.app.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsJPA implements AppUserDetailsService {

    private final AppUserDetailsRepository detailsRepository;

}
