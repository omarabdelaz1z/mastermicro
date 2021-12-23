package com.example.micromasters.Retriever;

/**
 * An interface to retrieve data from a data source.
 */
public interface IRetriever {
    /**
     * @param path "String"
     * @return "String" representation of data retrieved from the data source.
     */
    public String retrieve(String path);
}
