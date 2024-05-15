package utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileUtils {
    private static final ClassLoader classLoader = FileUtils.class.getClassLoader();
    public static List<Character> getCharArrayListFromResources(String directory, String filename) throws IOException {
        List<Character> charList = new ArrayList<>();
        String filePath = directory + "/" + filename;
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("Can not find file " + filePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    for (char c : line.toCharArray()) {
                        charList.add(c);
                    }
                    charList.add(' ');
                }
            }
        }
        return charList;
    }
    public static Set<Character> getCharSetFromResource(String directory, String filename) throws IOException {
        Set<Character> charSet = new HashSet<>();
        String filePath = directory + "/" + filename;
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("Can not find file " + filePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.isEmpty()) {
                        charSet.add(line.charAt(0));
                    }
                }
            }
        }
        return charSet;
    }
    public static Map<Character, Character> getHashMapFromResource(String directory, String filename) throws IOException {
        Map<Character, Character> hashMap = new HashMap<>();
        String filePath = directory + "/" + filename;
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("Can not find file " + filePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        char key = parts[0].charAt(0);
                        char value = parts[1].charAt(0);
                        hashMap.put(key, value);
                    } else {
                        System.err.println("Line ignored due to incorrect format: " + line);
                        System.err.println("Format required: Key,Value");
                    }
                }
            }
        }
        return hashMap;
    }
    public static <T> void saveListToResourceFile(String directory, String filename, List<T> list) throws IOException {
        Path resourceDirectory = Paths.get("src", "main", "resources", directory);
        String resourcePath = resourceDirectory.toAbsolutePath().toString();
        String filePath = Paths.get(resourcePath, filename).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T item : list) {
                writer.write(item.toString());
            }
        }
    }
    public static <K, V> void saveMapToResourceFile(String directory, String filename, Map<K, V> map) throws IOException {
        Path resourceDirectory = Paths.get("src", "main", "resources", directory);
        String resourcePath = resourceDirectory.toAbsolutePath().toString();
        String filePath = Paths.get(resourcePath, filename).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                writer.write(entry.getKey().toString() + "," + entry.getValue().toString());
                if (iterator.hasNext()) {
                    writer.write("\n");
                }
            }
        }
    }
}