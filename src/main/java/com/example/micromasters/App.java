package com.example.micromasters;

import com.example.micromasters.Retriever.FileRetriever;
import com.example.micromasters.Retriever.IRetriever;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
        IRetriever iRetriever = new FileRetriever();

        String content = iRetriever.retrieve("topology.json");

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(content);

        System.out.println(jsonNode.get("id").asText());
    }
}
