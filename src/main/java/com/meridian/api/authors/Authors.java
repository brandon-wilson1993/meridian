package com.meridian.api.authors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Authors {

    @GeneratedValue @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    // bookid

    // seriesid


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
        if (!(o instanceof Authors))
            return false;
        Authors platform = (Authors) o;
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
