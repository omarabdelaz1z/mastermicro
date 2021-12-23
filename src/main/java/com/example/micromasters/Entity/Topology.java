package com.example.micromasters.Entity;

import java.util.ArrayList;
import java.util.List;

public class Topology {
    private String id;
    private List<Component> components;

    public Topology() {
        this.id = "";
        this.components = new ArrayList<>();
    }

    public Topology(String id, List<Component> components) {
        this.id = id;
        this.components = components;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public String getId() {
        return this.id;
    }

    public List<Component> getComponents() {
        return this.components;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", components='" + getComponents() + "'" +
                "}";
    }
}
