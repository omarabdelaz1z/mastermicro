package com.example.micromasters.Mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.example.micromasters.Entity.Component;
import com.example.micromasters.Entity.Topology;
import com.example.micromasters.Retriever.FileRetriever;
import com.example.micromasters.Retriever.IRetriever;

public class TopologyJsonMapper implements IDataMapper<Topology> {
    @Override
    public String serialize(Topology topology) {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

            return mapper.writeValueAsString(topology);
        } catch (JsonProcessingException e) {
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
                    IDataMapper<Component> cMapper = new ComponentJsonMapper();

                    for (JsonNode component : value) {
                        Component componentObject = cMapper.deserialize(
                                component.toPrettyString());

                        components.add(componentObject);
                    }
                    continue;
                }

                if (value.isTextual()) {
                    Field specifiedField = topology.getClass().getDeclaredField(key);
                    specifiedField.setAccessible(true);
                    specifiedField.set(topology, value.asText());
                }
            }

            topology.setComponents(components);
            return topology;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String args[]) {
        IRetriever iRetriever = new FileRetriever();
        // ITopologyStore topologyStore = new InMemoryTopologyStore();
        String data = iRetriever.retrieve("topology.json");
        IDataMapper<Topology> topologyMapper = new TopologyJsonMapper();
        Topology topology = topologyMapper.deserialize(data);
        System.out.println(topologyMapper.serialize(topology));
    }
}
