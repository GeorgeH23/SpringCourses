package com.george.restapp.api.domain;

import java.io.Serializable;

public class Geo implements Serializable {

    private static final  long serialVersionUID = 279557596527326964L;

    public String lat;
    public String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
