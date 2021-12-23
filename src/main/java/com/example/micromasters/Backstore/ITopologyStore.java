package com.example.micromasters.Backstore;

import java.util.HashMap;
import java.util.List;
import com.example.micromasters.Entity.Topology;

/**
 * ITopologyStore is a storage API to create, read and delete operations.
 */
public interface ITopologyStore {
    public Topology create(Topology topology);

    public List<Topology> read(HashMap<String, String> query);

    public Topology delete(String topologyId);
}
