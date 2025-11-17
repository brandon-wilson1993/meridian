package com.meridian.api.users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorEntityTests {

    @Test
    void testEquals_isEqual() {

        Users author1 = new Users(123L, "testing", "equals");
        Users author2 = new Users(123L, "testing", "equals");

        assertEquals(author1, author2);
    }

    @Test
    void testEquals_idIsNotEqual() {

        Users author1 = new Users(123L, "testing", "equals");
        Users author2 = new Users(12L, "testing", "equals");

        assertNotEquals(author1, author2);
    }

    @Test
    void testEquals_firstNameIsNotEqual() {

        Users author1 = new Users(123L, "testing", "equals");
        Users author2 = new Users(123L, "testing1", "equals");

        assertNotEquals(author1, author2);
    }

    @Test
    void testEquals_lastNameIsNotEqual() {

        Users author1 = new Users(123L, "testing", "equals");
        Users author2 = new Users(123L, "testing", "equals2");

        assertNotEquals(author1, author2);
    }

    @Test
    void testEquals_sameObject() {

        Users author = new Users(123L, "testing", "equals");

        assertEquals(author, author);
    }

    @Test
    void testEquals_wrongObjectType() {

        Users author = new Users(123L, "testing", "equals");
        String tester = "test string";

        assertNotEquals(author, tester);
    }

    @Test
    void testHashCode_isEqual() {

        Users author1 = new Users(123L, "testing", "hash");
        Users author2 = new Users(123L, "testing", "hash");

        assertEquals(author1.hashCode(), author2.hashCode());
    }

    @Test
    void testHashCode_isNotEqual() {

        Users author1 = new Users(123L, "testing", "hash");
        Users author2 = new Users(12L, "testing", "hash2");

        assertNotEquals(author1, author2);
    }

    @Test
    void testToString() {

        Users author = new Users(123L, "testing", "string");

        String expected = "Authors{ id=123, first_name='testing', last_name='string'}";

        assertEquals(expected, author.toString());
    }
}
