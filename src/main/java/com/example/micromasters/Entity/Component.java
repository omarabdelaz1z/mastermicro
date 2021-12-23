package com.example.micromasters.Entity;

import java.util.Map;

public class Component {
    private String id;
    private String type;

    private Map<String, Map<String, Object>> info;

    private Map<String, Map<String, Object>> network; // HashMap

    public Component() {

    }

    public Component(String id, String type, Map<String, Map<String, Object>> info,
            Map<String, Map<String, Object>> network) {
        this.id = id;
        this.type = type;
        this.info = info;
        this.network = network;
    }

    public Component setId(String id) {
        this.id = id;
        return this;
    }

    public Component setType(String type) {
        this.type = type;
        return this;
    }

    public Component setNetwork(Map<String, Map<String, Object>> network) {
        this.network = network;
        return this;
    }

    public Component setInfo(Map<String, Map<String, Object>> info) {
        this.info = info;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public Map<String, Map<String, Object>> getNetwork() {
        return this.network;
    }

    public Map<String, Map<String, Object>> getInfo() {
        return this.info;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", type='" + getType() + "'" +
                ", extraInfo='" + getInfo() + "'" +
                ", network='" + getNetwork() + "'" +
                "}";
    }
}