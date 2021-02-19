package com.hack.demo.service;

import com.hack.demo.details.HackerDetails;
import com.hack.demo.entity.Hacker;
import com.hack.demo.repository.HackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HackerDetailsService implements UserDetailsService {

    @Autowired
    private HackerRepository hackerRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Hacker hacker = hackerRepository.findHackerByEmail(s);
        if (hacker == null)
            throw new UsernameNotFoundException("Hacker Not Found");
        return new HackerDetails(hacker);
    }
}
