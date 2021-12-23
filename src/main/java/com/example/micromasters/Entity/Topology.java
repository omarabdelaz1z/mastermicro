package com.example.micromasters.Entity;

import java.util.ArrayList;

public class Topology {
    private String id;
    private ArrayList<Component> components;

    public Topology() {
        this.id = "";
        this.components = new ArrayList<>();
    }

    public Topology(String id, ArrayList<Component> components) {
        this.id = id;
        this.components = components;
    }

    public Topology setId(String id) {
        this.id = id;
        return this;
    }

    public Topology setComponents(ArrayList<Component> components) {
        this.components = components;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public ArrayList<Component> getComponents() {
        return this.components;
    }
}
