package com.meridian.api.platform;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Platform {

    @GeneratedValue @Id
    private Long id;

    @Column(name = "platform_name")
    private String platormName;

    private String manufacturer;

    public Long getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getPlatormName() {
        return platormName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPlatormName(String platormName) {
        this.platormName = platormName;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Platform))
            return false;
        Platform platform = (Platform) o;
        return Objects.equals(this.id, platform.id) && Objects.equals(this.platormName, platform.platormName)
                && Objects.equals(this.manufacturer, platform.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.platormName, this.manufacturer);
    }

    @Override
    public String toString() {
        return "Platform{" + "id=" + this.id + ", platform_name='" + this.platormName + '\'' + ", manufacturer='" + this.manufacturer + '\'' + '}';
    }
}
