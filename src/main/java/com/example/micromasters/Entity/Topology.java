package com.example.micromasters.Entity;

import java.util.List;

public class Topology {
    public String id;
    public List<Component> components;

    public Topology() {

    }

    public Topology(String id, List<Component> components) {
        this.id = id;
        this.components = components;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + this.id + "'" +
                ", components='" + this.components + "'" +
                "}";
    }
}
