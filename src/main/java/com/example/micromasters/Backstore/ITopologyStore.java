package com.example.micromasters.Backstore;

import java.util.HashMap;
import java.util.List;
import com.example.micromasters.Entity.Topology;

public interface ITopologyStore {
    public Topology create(Topology topology);

    public List<Topology> read(HashMap<String, String> query);

    public Topology delete(String topologyId);
}
