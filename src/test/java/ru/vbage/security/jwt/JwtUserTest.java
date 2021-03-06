package ru.vbage.security.jwt;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertSame;


class JwtUserTest {
    @Test
    void testConstructor() {
        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        JwtUser actualJwtUser = new JwtUser("janedoe", "iloveyou", grantedAuthorityList);

        assertSame(grantedAuthorityList, actualJwtUser.getAuthorities());
        assertEquals("iloveyou", actualJwtUser.getPassword());
        assertEquals("janedoe", actualJwtUser.getUsername());
        assertTrue(actualJwtUser.isAccountNonExpired());
        assertTrue(actualJwtUser.isAccountNonLocked());
        assertTrue(actualJwtUser.isCredentialsNonExpired());
        assertTrue(actualJwtUser.isEnabled());
    }

    @Test
    void testConstructor2() {
        JwtUser actualJwtUser = new JwtUser("janedoe", "iloveyou", new SimpleGrantedAuthority("Role"));

        assertEquals(1, actualJwtUser.getAuthorities().size());
        assertEquals("iloveyou", actualJwtUser.getPassword());
        assertEquals("janedoe", actualJwtUser.getUsername());
    }
}

