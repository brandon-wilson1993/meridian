package com.meridian.api.author;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorEntityTests {

    @Test
    void testEquals_isEqual() {

        Author author1 = new Author(123L, "testing", "equals");
        Author author2 = new Author(123L, "testing", "equals");

        assertEquals(author1, author2);
    }

    @Test
    void testEquals_idIsNotEqual() {

        Author author1 = new Author(123L, "testing", "equals");
        Author author2 = new Author(12L, "testing", "equals");

        assertNotEquals(author1, author2);
    }

    @Test
    void testEquals_firstNameIsNotEqual() {

        Author author1 = new Author(123L, "testing", "equals");
        Author author2 = new Author(123L, "testing1", "equals");

        assertNotEquals(author1, author2);
    }

    @Test
    void testEquals_lastNameIsNotEqual() {

        Author author1 = new Author(123L, "testing", "equals");
        Author author2 = new Author(123L, "testing", "equals2");

        assertNotEquals(author1, author2);
    }

    @Test
    void testEquals_sameObject() {

        Author author = new Author(123L, "testing", "equals");

        assertEquals(author, author);
    }

    @Test
    void testEquals_wrongObjectType() {

        Author author = new Author(123L, "testing", "equals");
        String tester = "test string";

        assertNotEquals(author, tester);
    }

    @Test
    void testHashCode_isEqual() {

        Author author1 = new Author(123L, "testing", "hash");
        Author author2 = new Author(123L, "testing", "hash");

        assertEquals(author1.hashCode(), author2.hashCode());
    }

    @Test
    void testHashCode_isNotEqual() {

        Author author1 = new Author(123L, "testing", "hash");
        Author author2 = new Author(12L, "testing", "hash2");

        assertNotEquals(author1, author2);
    }

    @Test
    void testToString() {

        Author author = new Author(123L, "testing", "string");

        String expected = "Authors{ id=123, first_name='testing', last_name='string'}";

        assertEquals(expected, author.toString());
    }
}
