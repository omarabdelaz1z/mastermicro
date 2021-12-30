package com.example.micromasters.Backstore;

import java.util.List;
import com.example.micromasters.Entity.Topology;

/**
 * ITopologyStore is a storage API to create, read and delete topologies from a
 * given backstorage.
 */
public interface ITopologyStore {
    public boolean create(Topology topology);

    public List<Topology> read(String id);

    public boolean delete(String id) throws Exception;
}
