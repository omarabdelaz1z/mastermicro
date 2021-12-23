package com.example.micromasters.Retriever;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;

/**
 * FileRetriever is an IRetriever implementation to retrieve data from a file.
 */
public class FileRetriever implements IRetriever {
    @Override
    public String retrieve(String path) {
        try {
            Path filePath = Paths.get(path);

            if (!Files.exists(filePath))
                throw new NoSuchFileException(filePath.toString(), null, "is not found.");

            return Files.readString(filePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
