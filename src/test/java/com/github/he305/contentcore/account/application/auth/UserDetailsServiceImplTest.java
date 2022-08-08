package com.github.he305.contentcore.account.application.auth;

import com.github.he305.contentcore.account.domain.exceptions.AccountNotFoundException;
import com.github.he305.contentcore.account.domain.model.Account;
import com.github.he305.contentcore.account.domain.model.enums.Role;
import com.github.he305.contentcore.account.domain.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private AccountService accountService;
    @InjectMocks
    private UserDetailsServiceImpl underTest;

    @Test
    void loadUserByUsername_notFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        String data = id.toString();
        Mockito.when(accountService.loginById(id)).thenThrow(AccountNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () ->
                underTest.loadUserByUsername(data));
    }

    @Test
    void loadByUsername_valid() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, "name", "pass", Role.ADMIN);
        User expected = User.create(account);

        Mockito.when(accountService.loginById(id)).thenReturn(account);

        UserDetails actual = underTest.loadUserByUsername(id.toString());
        assertEquals(expected, actual);
    }
}