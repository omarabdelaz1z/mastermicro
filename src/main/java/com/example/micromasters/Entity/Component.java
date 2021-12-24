package com.example.micromasters.Entity;

import java.util.Map;

public class Component {
    public String id;
    public String type;
    public Map<String, Detail> info;
    public Map<String, Detail> network;

    public Component() {

    }

    public Component(String id, String type, Map<String, Detail> info,
            Map<String, Detail> network) {
        this.id = id;
        this.type = type;
        this.info = info;
        this.network = network;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + this.id + "'" +
                ", type='" + this.type + "'" +
                ", extraInfo='" + this.info + "'" +
                ", network='" + this.network + "'" +
                "}";
    }
}