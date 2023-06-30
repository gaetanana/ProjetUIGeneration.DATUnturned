package Modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class modeleApplication {

    public static Map<String, String> readDataWithOutBluePrints(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        Map<String, String> data = new HashMap<>();
        String line;

        while ((line = reader.readLine()) != null) {
            // Skip Blueprints section
            if (line.startsWith("Blueprints")) {
                break;
            }

            // Split the line into key-value pair and put into map
            String[] parts = line.split(" ");
            if (parts.length > 1) {
                data.put(parts[0], parts[1]);
            }
        }
        return data;
    }

    public static Map<String, String> readBlueprintsData(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        Map<String, String> data = new LinkedHashMap<>();  // Utilisation de LinkedHashMap au lieu de HashMap
        String line;
        boolean isBlueprintsSection = false;

        while ((line = reader.readLine()) != null) {
            // Start reading from Blueprints section
            if (line.startsWith("Blueprints")) {
                isBlueprintsSection = true;
                continue;
            }

            if (isBlueprintsSection) {
                // Split the line into key-value pair and put into map
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    data.put(parts[0], parts[1]);
                }
            }
        }

        return data;
    }


    /**
     * Cette fonction permet de récupérer
     */
    public static int getNumberOfBlueprints(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;
        Pattern pattern = Pattern.compile("Blueprints (\\d+)");

        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        }

        throw new IllegalArgumentException("No blueprints line found in the file.");
    }
}





