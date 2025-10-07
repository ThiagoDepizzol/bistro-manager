package com.app.core.domain.location;

public class Location {

    private Long id;

    private String address;

    private String number;

    private String neighborhood;

    private String complement;

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

    public Location() {
    }

    public Location(String address, String number, String neighborhood, String complement, String zipCode, String city, String state) {
        this.address = address;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }

    public Location(Long id, String address, String number, String neighborhood, String complement, String zipCode, String city, String state) {
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
