package com.meridian.api.users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsersEntityTests {

    @Test
    void testEquals_isEqual() {

        Users user1 = new Users(123L, "testing", "equals");
        Users user2 = new Users(123L, "testing", "equals");

        assertEquals(user1, user2);
    }

    @Test
    void testEquals_idIsNotEqual() {

        Users user1 = new Users(123L, "testing", "equals");
        Users user2 = new Users(12L, "testing", "equals");

        assertNotEquals(user1, user2);
    }

    @Test
    void testEquals_firstNameIsNotEqual() {

        Users user1 = new Users(123L, "testing", "equals");
        Users user2 = new Users(123L, "testing1", "equals");

        assertNotEquals(user1, user2);
    }

    @Test
    void testEquals_lastNameIsNotEqual() {

        Users user1 = new Users(123L, "testing", "equals");
        Users user2 = new Users(123L, "testing", "equals2");

        assertNotEquals(user1, user2);
    }

    @Test
    void testEquals_sameObject() {

        Users user = new Users(123L, "testing", "equals");

        assertEquals(user, user);
    }

    @Test
    void testEquals_wrongObjectType() {

        Users user = new Users(123L, "testing", "equals");
        String tester = "test string";

        assertNotEquals(user, tester);
    }

    @Test
    void testHashCode_isEqual() {

        Users user1 = new Users(123L, "testing", "hash");
        Users user2 = new Users(123L, "testing", "hash");

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testHashCode_isNotEqual() {

        Users user1 = new Users(123L, "testing", "hash");
        Users user2 = new Users(12L, "testing", "hash2");

        assertNotEquals(user1, user2);
    }

    @Test
    void testToString() {

        Users user = new Users(123L, "testing", "string");

        String expected = "Users{ id=123, first_name='testing', last_name='string'}";

        assertEquals(expected, user.toString());
    }
}
