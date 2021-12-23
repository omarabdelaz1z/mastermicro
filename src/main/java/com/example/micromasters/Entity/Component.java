package com.example.micromasters.Entity;

import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Component {
    private String id;
    private String type;
    private JsonNode extraInfo;

    private ArrayList<Device> network;

    public Component() {

    }

    public Component(String id, String type, JsonNode extraInfo, ArrayList<Device> network) {
        this.id = id;
        this.type = type;
        this.extraInfo = extraInfo;
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

    public Component setNetwork(ArrayList<Device> network) {
        this.network = network;
        return this;
    }

    public Component setExtraInfo(JsonNode extraInfo) {
        this.extraInfo = extraInfo;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public ArrayList<Device> getNetwork() {
        return this.network;
    }

    public JsonNode getExtraInfo() {
        return this.extraInfo;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

            return mapper
                    .readTree(mapper.writeValueAsString(this))
                    .toPrettyString();

        } catch (JsonProcessingException e) {

        }
        return id;
    }

    public static void main(String args[]) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode info = objectMapper.createObjectNode();
        ObjectNode internal = objectMapper.createObjectNode();

        internal.put("t1", "vdd");
        internal.put("t2", "n1");

        info.put("resistance", internal.toString());

        Component component = new Component("res1", "resistor", info, new ArrayList<>());

        System.out.println(component);

    }
}