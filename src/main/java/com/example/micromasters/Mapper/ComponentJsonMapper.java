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

public class ComponentJsonMapper implements IDataMapper<Component> {
    @Override
    public String serialize(Component component) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode componentNode = mapper.createObjectNode();

        try {

            for (Field field : component.getClass().getDeclaredFields()) {
                String key = field.getName();
                Object value = field.get(component);

                if (!(value instanceof Map))
                    componentNode.put(key, (String) value);

                else {
                    Map<String, Detail> data = (Map<String, Detail>) (value);

                    String dataKey = data.keySet().iterator().next();
                    JsonNode dataDetail = mapper.valueToTree(
                            data.values()
                                    .stream()
                                    .findFirst()
                                    .get().detail);

                    if (key.equals("info"))
                        componentNode.set(dataKey, dataDetail);

                    else
                        componentNode.set(dataKey, dataDetail);
                }
            }
            return componentNode.toPrettyString();
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
            JsonNode componentNode = mapper.readTree(data);
            Iterator<Entry<String, JsonNode>> iterator = componentNode.fields();

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
