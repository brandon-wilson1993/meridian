package com.meridian.api.author;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Author {

    @SequenceGenerator(
            name = "idx_seq",
            sequenceName = "idx_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idx_seq")
    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    // bookid

    // seriesid

    public Author() {}

    public Author(Long id, String firstName, String lastName) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Author))
            return false;
        Author platform = (Author) o;
        return Objects.equals(this.id, platform.id) && Objects.equals(this.firstName, platform.firstName)
                && Objects.equals(this.lastName, platform.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName);
    }

    @Override
    public String toString() {
        return "Authors{" + "id=" + this.id + ", first_name='" + this.firstName + '\'' + ", lsat_name='" + this.lastName + '\'' + '}';
    }
}
