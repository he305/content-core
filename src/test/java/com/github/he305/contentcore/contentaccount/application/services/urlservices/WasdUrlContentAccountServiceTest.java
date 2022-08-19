package com.github.he305.contentcore.contentaccount.application.services.urlservices;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WasdUrlContentAccountServiceTest {

    private final WasdUrlContentAccountService underTest = new WasdUrlContentAccountService();

    @Test
    void getUrl() {
        String name = "smth";
        String expected = "https://wasd.tv/smth";
        String actual = underTest.getUrl(name);
        assertEquals(expected, actual);
    }

    @Test
    void getType() {
        Platform expected = Platform.WASD;
        Platform actual = underTest.getType();
        assertEquals(expected, actual);
    }
}