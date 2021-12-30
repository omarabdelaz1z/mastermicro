package com.example.micromasters;

import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.example.micromasters.Backstore.ITopologyStore;
import com.example.micromasters.Backstore.InMemoryTopologyStore;
import com.example.micromasters.Entity.Topology;
import com.example.micromasters.Mapper.IDataMapper;
import com.example.micromasters.Mapper.TopologyJsonMapper;
import com.example.micromasters.Retriever.FileRetriever;
import com.example.micromasters.Retriever.IRetriever;
import com.example.micromasters.Utils.Utils;

public class App {
    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
        IRetriever iRetriever = new FileRetriever();
        ITopologyStore topologyStore = new InMemoryTopologyStore();
        IDataMapper<Topology> iMapper = new TopologyJsonMapper();

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
            inputScanner.nextLine();

            switch (choice) {
                case 1: {
                    System.out.print("- Filepath: ");
                    String path = inputScanner.nextLine();

                    String response = iRetriever.retrieve(path);
                    System.out.println("Content Retrieved Successfully");
                    Topology deserialized = iMapper.deserialize(response);
                    topologyStore.create(deserialized);
                    break;
                }
                case 2: {
                    System.out.print("- Filepath: ");
                    String path = inputScanner.nextLine();

                    System.out.print("- Topology ID: ");
                    String id = inputScanner.nextLine();

                    List<Topology> resultSet = topologyStore.read(id);

                    if (resultSet == null) {
                        System.err.println("Element is not found.");
                        break;
                    }

                    String serialized = iMapper.serialize(resultSet.get(0));
                    Utils.writeToFile(serialized, path);
                    System.out.println("Content Written Successfully");
                    break;
                }
                case 3:
                    topologyStore.read(null).forEach(result -> System.out.println(iMapper.serialize(result)));
                    break;
                case 4: {
                    System.out.print("- Topology ID: ");
                    String id = inputScanner.nextLine();

                    try {
                        topologyStore.delete(id);

                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                    break;
                case 5:
                    System.out.print("- Topology ID: ");
                    String id = inputScanner.nextLine();
                    System.out.println(iMapper.serialize(topologyStore.read(id).get(0)));

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
