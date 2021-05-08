package com.george.restapp.api.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Job implements Serializable {

    private static final long serialVersionUID = -4985150429002262656L;

    private String title;
    private String company;
    private final Map<String, Object> additionalProperties = new HashMap<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
