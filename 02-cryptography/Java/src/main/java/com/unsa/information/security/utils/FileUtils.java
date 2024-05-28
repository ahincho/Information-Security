package com.unsa.information.security.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FileUtils {
    private static final ClassLoader classLoader = FileUtils.class.getClassLoader();
    public static Set<Character> getCharSetFromResource(String filename) throws IOException {
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        if (inputStream == null) { throw new IOException(String.format("Can not load file %s\n", filename)); }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.lines()
                .filter(line -> !line.isEmpty())
                .flatMap(line -> line.codePoints().mapToObj(codePoint -> (char) codePoint))
                .collect(Collectors.toSet());
    }
    public static List<Character> getCharListFromResource(String filename) throws IOException {
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        if (inputStream == null) { throw new IOException(String.format("Can not load file %s\n", filename)); }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.lines()
                .flatMapToInt(CharSequence::chars)
                .mapToObj(character -> (char) character)
                .collect(Collectors.toList());
    }
    public static Map<Character, Character> getCharMapFromResource(String filename) throws IOException {
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        if (inputStream == null) { throw new IOException(String.format("Can not load file %s", filename)); }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines()
                .map(line -> line.split(","))
                .filter(parts -> {
                    if (parts.length == 2) {
                        return true;
                    } else {
                        System.err.println("Line ignored due to incorrect format: " + String.join(",", parts));
                        System.err.println("Format required: Key,Value");
                        return false;
                    }
                })
                .collect(Collectors.toMap(
                        parts -> parts[0].charAt(0),
                        parts -> parts[1].charAt(0)
                ));
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                bufferedWriter.write(entry.getKey().toString() + "," + entry.getValue().toString());
                if (iterator.hasNext()) {
                    bufferedWriter.write("\n");
                }
            }
        }
    }
    public static Map<Character, Long> getFrequencyMapFromFile(String directory, String filename, Set<Character> alphabet) throws IOException {
        List<Character> characters = FileUtils.getCharListFromResource(filename);
        Map<Character, Long> frequencyMap = alphabet.stream()
                .collect(Collectors.toMap(
                        character -> character,
                        character -> characters.stream().filter(c -> c.equals(character)).count()
                ));
        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }
}