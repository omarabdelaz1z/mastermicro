package com.example.micromasters;

import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.example.micromasters.Backstore.ITopologyStore;
import com.example.micromasters.Backstore.InMemoryTopologyStore;
import com.example.micromasters.Retriever.FileRetriever;
import com.example.micromasters.Retriever.IRetriever;

public class App {
    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
        IRetriever iRetriever = new FileRetriever();
        ITopologyStore topologyStore = new InMemoryTopologyStore();

        // String content = iRetriever.retrieve("topology.json");

        // // ObjectMapper objectMapper = new ObjectMapper();

        // // JsonNode jsonNode = objectMapper.readTree(content);

        // // System.out.println(jsonNode.get("id").asText());

        Scanner inputScanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1) Read a topology from a file");
            System.out.println("2) Write a topology to a file");
            System.out.println("3) Query topologies");
            System.out.println("4) Delete a topology");
            System.out.println("5) Query devices of a certain topology");
            System.out.println("6) Network Node");
            System.out.println("7) Exit");

            System.out.printf("- Choice: ");
            choice = inputScanner.nextInt();

            switch (choice) {
                case 1: {
                    System.out.print("- Filepath: ");
                    String filepath = inputScanner.nextLine();
                    String topologyJson = iRetriever.retrieve(filepath);
                    // topologyStore.create(topologyJson);
                    break;
                }
                case 2: {
                    System.out.print("- Filepath: ");
                    String filepath = inputScanner.nextLine();
                    break;
                }
                case 3:
                    topologyStore.read(null).forEach(System.out::println);
                    break;
                case 4: {
                    System.out.print("- Topology ID: ");
                    String id = inputScanner.nextLine();
                    System.out.println(topologyStore.delete(id));
                }
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
        } while (choice != 7);

        inputScanner.close();
    }
}
