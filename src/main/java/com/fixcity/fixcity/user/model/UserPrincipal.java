package com.fixcity.fixcity.user.model;

import com.fixcity.fixcity.user.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;


public record UserPrincipal(Long id,
                            String username,
                            @Getter
                            String email,
                            String hash,
                            Set<GrantedAuthority> authorities,
                            boolean accountNonLocked,
                            boolean enabled) implements UserDetails {

    public UserPrincipal {
        authorities = Set.copyOf(authorities);
    }

    public static UserPrincipal from(User u) {
        Set<GrantedAuthority> auth = Set.of(new SimpleGrantedAuthority(u.getRole().name()));

        boolean nonLocked = u.getStatus() != Status.LOCKED;
        boolean isEnabled = u.getStatus() == Status.ACTIVE;

        return new UserPrincipal(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getPassword(),
                auth,
                nonLocked,
                isEnabled
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return hash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "UserPrincipal{id=%d, email=%s, username=%s, roles=%s, enabled=%s, nonLocked=%s}"
                .formatted(
                        id,
                        username,
                        email,
                        authorities.stream().map(GrantedAuthority::getAuthority).toList(),
                        enabled,
                        accountNonLocked
                );
    }
}
