package com.example.micromasters.Mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.example.micromasters.Entity.Component;
import com.example.micromasters.Entity.Detail;
import com.example.micromasters.Retriever.FileRetriever;
import com.example.micromasters.Retriever.IRetriever;

public class ComponentJsonMapper implements IDataMapper<Component> {

    public ComponentJsonMapper() {

    }

    @Override
    public String serialize(Component component) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode wholeComponent = mapper.createObjectNode();
        try {

            for (Field field : component.getClass().getDeclaredFields()) {
                String key = field.getName();
                Object value = field.get(component);

                if (!(value instanceof Map)) {
                    wholeComponent.put(key, (String) value);
                }

                else {
                    Map<String, Detail> info = (Map<String, Detail>) (value);

                    String infoKey = info.keySet().iterator().next();
                    JsonNode infoDetail = mapper.valueToTree(info.values().stream().findFirst().get().detail);

                    if (key.equals("info")) {
                        wholeComponent.set(infoKey, infoDetail);
                    } else {
                        wholeComponent.set(infoKey, infoDetail);
                    }
                }
            }
            return wholeComponent.toPrettyString();
        } catch (Exception e) {
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
                    Map<String, Detail> root = new HashMap<>();

                    Map<String, Object> children = mapper.convertValue(value,
                            new TypeReference<Map<String, Object>>() {
                            });

                    root.put(key, new Detail(children));

                    if (key.equalsIgnoreCase("netlist"))
                        component.network = root;

                    else
                        component.info = root;

                    continue;
                }

                if (value.isTextual()) {
                    Field specifiedField = component.getClass().getDeclaredField(key);
                    specifiedField.set(component, value.textValue());
                }
            }

            return component;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
