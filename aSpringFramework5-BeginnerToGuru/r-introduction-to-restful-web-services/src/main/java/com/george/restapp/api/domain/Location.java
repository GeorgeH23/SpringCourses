package com.george.restapp.api.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Location implements Serializable {

    private static final long serialVersionUID = -3532048267747973846L;

    private String street;
    private String city;
    private String state;
    private String postcode;
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
