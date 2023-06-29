package Modele;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class modeleApplication {
    /**
     * Cette fonction permet de lire un fichier .DAT
     */
    public static Map<String, String> readDATFile(File file) throws Exception {
        Map<String, String> data = new HashMap<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(" ")) {
                String[] parts = line.split(" ", 2);
                data.put(parts[0], parts[1]);
            } else {
                data.put(line, "");
            }
        }
        scanner.close();
        return data;
    }


}
