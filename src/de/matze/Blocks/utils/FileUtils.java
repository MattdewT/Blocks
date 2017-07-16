package de.matze.Blocks.utils;

import java.io.*;

/**
 * Nimmt Textdatein, parst diese und lädt sie zu
 * einem String
 *
 * @author matze tiroch
 * @version 1.1
 */

public class FileUtils {

    private FileUtils() {
    }

    /**
     * Textdatei auslesen und den String zurückgeben
     */
    public static String loadAsString(String file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer + '\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void saveAsString(String location, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(location))) {
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}