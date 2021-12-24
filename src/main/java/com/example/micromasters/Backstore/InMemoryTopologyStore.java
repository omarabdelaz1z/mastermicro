package com.example.micromasters.Backstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import java.util.NoSuchElementException;

import com.example.micromasters.Entity.Topology;

/**
 * InMemory implementation of ITopologyStore that uses List data stucture to
 * store topologies in memory.
 */
public class InMemoryTopologyStore implements ITopologyStore {
    private List<Topology> store;

    public InMemoryTopologyStore() {
        this.store = new ArrayList<>();
    }

    @Override
    public Topology create(Topology topology) {
        // TODO: Add topology to the store.
        /**
         * 1. Check if the given topology is already exists.
         * 1.1 if exists, don't insert. Otherwise, (2)
         * 2. Insert the topology in the store.
         */
        return null;
    }

    @Override
    public List<Topology> read(HashMap<String, String> query) {
        if (query == null || query.size() == 0) {
            return store;
        }
        // TODO: filter store with the given query.
        return new ArrayList<Topology>();
    }

    @Override
    public Topology delete(String topologyId) {

        // TODO: Refactor Delete Code
        try {
            Optional<Topology> optional = store.stream()
                    .filter(topology -> (topology.id.equalsIgnoreCase(topologyId))).findFirst();

            if (!optional.isPresent()) {
                throw new NoSuchElementException("Topology with name: " + topologyId + "is not found.");
            }

            Topology topology = optional.get();
            store.remove(topology);

            return topology;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
