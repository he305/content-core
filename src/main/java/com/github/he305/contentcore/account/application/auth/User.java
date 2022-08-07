package com.github.he305.contentcore.account.application.auth;

import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.model.enums.Role;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Setter
public class User implements UserDetails {

    private final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    private UUID id;
    private String username;
    private String password;

    public User(UUID id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        authorities.add(new SimpleGrantedAuthority(role.name()));
    }

    public User(UUID id, String username, String password) {
        this(id, username, password, Role.USER);
    }

    public static User create(Account account) {

        return new User(
                account.getId(),
                account.getName(),
                account.getPassword(),
                account.getRole()
        );
    }

    public UUID getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }
}
