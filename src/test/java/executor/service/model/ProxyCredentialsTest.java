package executor.service.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyCredentialsTest {
    private ProxyCredentials proxyCredentials;

    @Before
    public void setUp() {
        proxyCredentials = new ProxyCredentials("test name", "test password");
    }

    @Test
    public void testGetUsername() {
        assertEquals("test name", proxyCredentials.getUsername());
    }

    @Test
    public void testSetUsername() {
        proxyCredentials.setUsername("some name");
        assertEquals("some name", proxyCredentials.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("test password", proxyCredentials.getPassword());
    }

    @Test
    public void testSetPassword() {
        proxyCredentials.setPassword("12345");
        assertEquals("12345", proxyCredentials.getPassword());
    }

    @Test
    public void testEquals() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        proxyCredentials1.setUsername("test name");
        proxyCredentials1.setPassword("test password");
        assertEquals(proxyCredentials, proxyCredentials1);
    }

    @Test
    public void testEqualsWithEmptyObjects() {
        proxyCredentials.setUsername(null);
        proxyCredentials.setPassword(null);
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        assertEquals(proxyCredentials, proxyCredentials1);
    }

    @Test
    public void testNotEqualsByUsername() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        proxyCredentials1.setUsername("other name");
        assertNotEquals(proxyCredentials, proxyCredentials1);
    }

    @Test
    public void testNotEqualsByPassword() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        proxyCredentials1.setPassword("wrong password");
        assertNotEquals(proxyCredentials, proxyCredentials1);
    }

    @Test
    public void testHashCode() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials("test name", "test password");
        assertEquals(proxyCredentials.hashCode(), proxyCredentials1.hashCode());
    }

    @Test
    public void testHashCodeNotMatch() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        assertNotEquals(proxyCredentials.hashCode(), proxyCredentials1.hashCode());
    }
}