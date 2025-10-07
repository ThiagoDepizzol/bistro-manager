package com.app.infra.entity.location;

import com.app.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "loc_locations")
public class LocationEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String number;

    private String neighborhood;

    private String complement;

    @NotNull
    @Column(nullable = false)
    private String zipCode;

    private String city;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocationEntity() {
    }

    public LocationEntity(Long id, String address, String number, String neighborhood, String complement, String zipCode, String city, String state) {
        this.id = id;
        this.address = address;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }
}
