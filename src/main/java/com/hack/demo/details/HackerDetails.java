package com.hack.demo.details;

import com.hack.demo.entity.Hacker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class HackerDetails implements UserDetails {

    private Hacker hacker;

    public HackerDetails(Hacker hacker){
        this.hacker = hacker;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(hacker.getRole()));
    }

    @Override
    public String getPassword() {
        return hacker.getPassword();
    }

    @Override
    public String getUsername() {
        return hacker.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return hacker.isEnable();
    }
}
