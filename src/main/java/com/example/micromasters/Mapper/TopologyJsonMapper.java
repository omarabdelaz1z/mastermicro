package com.example.micromasters.Mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.example.micromasters.Entity.Component;
import com.example.micromasters.Entity.Topology;

public class TopologyJsonMapper implements IDataMapper<Topology> {

    private IDataMapper<Component> cMapper;

    public TopologyJsonMapper() {
        cMapper = new ComponentJsonMapper();
    }

    @Override
    public String serialize(Topology topology) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        ObjectNode wholeComponent = mapper.createObjectNode();

        try {
            for (Field field : topology.getClass().getDeclaredFields()) {
                String key = field.getName();
                Object value = field.get(topology);

                if (!(value instanceof List)) {
                    wholeComponent.put(key, (String) value);
                }

                else {
                    List<Component> components = ((List<Component>) value);
                    List<JsonNode> nodes = components.stream()
                            .map(component -> {
                                try {
                                    return mapper.readTree(this.cMapper.serialize(component));
                                } catch (JsonMappingException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (JsonProcessingException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                return wholeComponent;
                            }).toList();

                    ArrayNode elements = mapper.createArrayNode();

                    elements.addAll(nodes);
                    wholeComponent.set(key, elements);
                }
            }
            return mapper.writeValueAsString(wholeComponent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Topology deserialize(String data) {
        try {
            Topology topology = new Topology();
            List<Component> components = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonObject = mapper.readTree(data);
            Iterator<Entry<String, JsonNode>> iterator = jsonObject.fields();

            while (iterator.hasNext()) {
                Entry<String, JsonNode> keyValuePair = iterator.next();
                String key = keyValuePair.getKey();

                JsonNode value = keyValuePair.getValue();

                if (value.isArray()) {

                    for (JsonNode component : value) {
                        Component componentObject = this.cMapper.deserialize(
                                component.toPrettyString());

                        components.add(componentObject);
                    }
                    continue;
                }

                if (value.isTextual()) {
                    Field specifiedField = topology.getClass().getDeclaredField(key);
                    specifiedField.set(topology, value.asText());
                }
            }

            topology.components = components;
            return topology;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
