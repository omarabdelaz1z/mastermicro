package com.example.micromasters.Mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.example.micromasters.Entity.Component;
import com.example.micromasters.Retriever.FileRetriever;
import com.example.micromasters.Retriever.IRetriever;

public class ComponentJsonMapper implements IDataMapper<Component> {

    public ComponentJsonMapper() {

    }

    @Override
    public String serialize(Component component) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(component);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Component deserialize(String data) {
        try {
            Component component = new Component();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonObject = mapper.readTree(data);
            Iterator<Entry<String, JsonNode>> iterator = jsonObject.fields();

            while (iterator.hasNext()) {
                Entry<String, JsonNode> keyValuePair = iterator.next();

                String key = keyValuePair.getKey();
                JsonNode value = keyValuePair.getValue();

                if (value.isContainerNode()) {
                    Map<String, Map<String, Object>> root = new HashMap<>();

                    Map<String, Object> children = mapper.convertValue(value,
                            new TypeReference<Map<String, Object>>() {
                            });

                    root.put(key, children);

                    if (key.equalsIgnoreCase("netlist"))
                        component.setNetwork(root);

                    else
                        component.setInfo(root);

                    continue;
                }

                if (value.isTextual()) {
                    Field specifiedField = component.getClass().getDeclaredField(key);
                    specifiedField.setAccessible(true);
                    specifiedField.set(component, value.textValue());
                }
            }

            return component;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String args[]) {
        IRetriever iRetriever = new FileRetriever();
        // ITopologyStore topologyStore = new InMemoryTopologyStore();
        String data = iRetriever.retrieve("component.json");
        IDataMapper<Component> cMapper = new ComponentJsonMapper();
        Component component = cMapper.deserialize(data);

        System.out.println(cMapper.serialize(component));
    }
}
