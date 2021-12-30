package com.example.micromasters.Utils;

import java.io.FileOutputStream;

/**
 * Provide basic helper functions to assist the program.
 */
public class Utils {
    /**
     * Write content to a file given path.
     * 
     * @param content "String"
     * @param path    "String"
     * @return true if it is written successfully. Otherwise, false.
     */
    public static void writeToFile(String content, String path) {
        try {
            FileOutputStream fStream = new FileOutputStream(path);
            byte[] bytes = content.getBytes();
            fStream.write(bytes);
            fStream.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
