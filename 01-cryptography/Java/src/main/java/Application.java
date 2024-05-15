import utils.FileUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {
    private static List<Character> characterList;
    public static void main(String[] args) {
        load("input", "data.txt", Arrays.asList('E', 'P', 'I', 'S'));
        // load("input", "data.txt");
        preprocessing();
    }
    public static void load(String inputDirectory, String dataFilename) {
        try {
            // Step 0: Load text, alphabet and substitutions map from resource files
            // Load data from resource file
            System.out.println("STEP 0: LOAD TEXT");
            characterList = FileUtils.getCharArrayListFromResources(inputDirectory, dataFilename);
            System.out.println(characterList);
            FileUtils.saveListToResourceFile("output", "step-0-load-text.txt", characterList);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    public static void load(String inputDirectory, String dataFilename, List<Character> padding) {
        try {
            System.out.println("STEP 0: LOAD TEXT");
            characterList = FileUtils.getCharArrayListFromResources(inputDirectory, dataFilename);
            if(padding == null || padding.isEmpty()) {
                throw new IOException("Padding is empty: " + padding);
            }
            characterList = IntStream.range(0, characterList.size())
                    .mapToObj(i -> (i + 1) % 17 == 0 ? padding : List.of(characterList.get(i)))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            int paddingLength = padding.size();
            int remainder = characterList.size() % paddingLength;
            if (remainder != 0) {
                int paddingToAdd = paddingLength - remainder;
                characterList.addAll(Collections.nCopies(paddingToAdd, 'Z'));
            }
            System.out.println(characterList);
            FileUtils.saveListToResourceFile("output", "step-0-load-text.txt", characterList);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    public static void preprocessing() {
        try {
            // Step 1: Apply alphabetic substitutions
            alphabeticSubstitutions();
            // Step 2: Remove accent marks
            removeAccentMarks();
            // Step 3: Convert all letters to uppercase
            convertLettersToUpperCase();
            // Step 4: Remove whitespace and punctuation marks
            removeWhitespacesAndPunctuationMarks();
            // Step 5: Calculate a frequency table for each letter
            frequencyTable();
            // Step 6: Apply Kasiski Method
            kasiskiMethod(characterList, 4);
            // Step 7: Change each character according to UNICODE-8
            encodeToUnicode8();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    public static void alphabeticSubstitutions() throws IOException {
        System.out.println("STEP 1: ALPHABETIC SUBSTITUTIONS");
        Map<Character, Character> alphabeticSubstitutions = FileUtils
                .getHashMapFromResource("input", "alphabetic-substitutions.txt");
        characterList = characterList.stream().map(c -> alphabeticSubstitutions.getOrDefault(c, c)).toList();
        System.out.println(characterList);
        FileUtils.saveListToResourceFile("output", "step-1-alphabetic-substitutions.txt", characterList);
    }
    public static void removeAccentMarks() throws IOException {
        System.out.println("STEP 2: REMOVE ACCENT MARKS");
        Map<Character, Character> accentsSubstitutions = FileUtils
                .getHashMapFromResource("input", "accents-substitutions.txt");
        characterList = characterList.stream().map(c -> accentsSubstitutions.getOrDefault(c, c)).toList();
        System.out.println(characterList);
        FileUtils.saveListToResourceFile("output", "step-2-remove-accents-marks", characterList);
    }
    public static void convertLettersToUpperCase() throws IOException {
        System.out.println("STEP 3: CONVERT ALL LETTERS TO UPPERCASE");
        int offset = 'A' - 'a';
        UnaryOperator<Character> toUpperCase = c -> {
            if (c >= 'a' && c <= 'z') {
                return (char) (c + offset);
            } else {
                return c;
            }
        };
        characterList = characterList.stream().map(toUpperCase).toList();
        System.out.println(characterList);
        FileUtils.saveListToResourceFile("output", "step-3-convert-letters-to-upper-case.txt", characterList);
    }
    public static void removeWhitespacesAndPunctuationMarks() throws IOException {
        System.out.println("STEP 4: REMOVE WHITESPACES AND PUNCTUATION MARKS");
        characterList = characterList.stream()
                .filter(c -> (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
                .toList();
        System.out.println(characterList);
        FileUtils.saveListToResourceFile("output", "step-4-remove-whitespaces-and-punctuation-marks.txt", characterList);
    }
    public static void  frequencyTable() throws IOException {
        System.out.println("STEP 5: CALCULATE FREQUENCY TABLE");
        Map<Character, Long> sortedFrequencyMap = calculateFrequencyMapFromFile("output",
                "step-4-remove-whitespaces-and-punctuation-marks.txt");
        System.out.println(sortedFrequencyMap);
        FileUtils.saveMapToResourceFile("output", "step-5-frequency-table.txt", sortedFrequencyMap);
    }
    public static Map<Character, Long> calculateFrequencyMapFromFile(String directory, String filename) throws IOException {
        Set<Character> characterSet = IntStream.concat(IntStream.rangeClosed('A', 'Z'), IntStream.of('Ñ'))
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(HashSet::new));
        List<Character> characters = FileUtils.getCharArrayListFromResources(directory, filename);
        Map<Character, Long> frequencyMap = characterSet.stream()
                .collect(Collectors.toMap(
                        c -> c,
                        c -> characters.stream().filter(ch -> ch.equals(c)).count()
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
    public static void kasiskiMethod(List<Character> characterList, int nGramLength) throws IOException {
        System.out.println("STEP 6: APPLY KASISKI METHOD");
        Map<String, List<Integer>> nGramsMap = IntStream.range(0, characterList.size() - nGramLength + 1)
                .boxed()
                .collect(Collectors.toMap(
                        i -> characterList.subList(i, i + nGramLength).stream().map(Object::toString).collect(Collectors.joining()),
                        Collections::singletonList,
                        (existing, replacement) -> {
                            List<Integer> combined = new ArrayList<>(existing);
                            combined.addAll(replacement);
                            return combined;
                        }
                ));
        Map<String, List<Integer>> repeatedNGrams = nGramsMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .flatMap(entry -> {
                    List<Integer> positions = entry.getValue();
                    return IntStream.range(0, positions.size() - 1)
                            .mapToObj(i -> positions.get(i + 1) - positions.get(i))
                            .toList()
                            .stream()
                            .map(distance -> new AbstractMap.SimpleEntry<>(entry.getKey(), distance));
                })
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
        System.out.println(repeatedNGrams);
        FileUtils.saveMapToResourceFile("output", "step-6-kasiski-method.txt", repeatedNGrams);
    }
    public static void encodeToUnicode8() throws IOException {
        System.out.println("STEP 7: CHANGE EACH CHARACTER ACCORDING TO UNICODE-8");
        List<String> encodedCharacterList = characterList.stream()
                .map(c -> String.format("%02X ", (int) c))
                .toList();
        System.out.println(encodedCharacterList);
        FileUtils.saveListToResourceFile("output", "step-7-encode-to-unicode-8.txt", encodedCharacterList);
    }
}
