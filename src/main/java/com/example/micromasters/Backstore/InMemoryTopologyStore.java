package com.example.micromasters.Backstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public boolean create(Topology topology) {
        Optional<Topology> opt = store.stream()
                .filter(stored -> stored.id.equals(topology.id))
                .findAny();

        if (opt.isPresent())
            return false;

        store.add(topology);
        return true;
    }

    @Override
    public List<Topology> read(String id) {
        if (Objects.isNull(id))
            return store;

        Optional<Topology> opt = store.stream()
                .filter(stored -> stored.id.equals(id))
                .findAny();

        if (!opt.isPresent())
            return null;

        return new ArrayList<Topology>(Arrays.asList(opt.get()));
    }

    @Override
    public boolean delete(String id) throws Exception {
        if (Objects.isNull(id))
            throw new Exception("Id must be given.");

        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).id.equals(id)) {
                store.remove(i);
                return true;
            }
        }
        return false;
    }
}
