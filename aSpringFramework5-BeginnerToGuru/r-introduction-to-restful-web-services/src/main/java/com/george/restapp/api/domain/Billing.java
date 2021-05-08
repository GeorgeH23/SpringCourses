package com.george.restapp.api.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Billing implements Serializable {

    private static final long serialVersionUID = 6577338081290507077L;

    private Card card;
    private final Map<String, Object> additionalProperties = new HashMap<>();


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
