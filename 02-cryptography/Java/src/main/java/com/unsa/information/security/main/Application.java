package com.unsa.information.security.main;

import com.unsa.information.security.configuration.ConfigurationLoader;
import com.unsa.information.security.encryption.CaesarEncryptor;
import com.unsa.information.security.encryption.VigenereEncryptor;
import com.unsa.information.security.preprocessing.Preprocessing;
import com.unsa.information.security.utils.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {
    private static final ConfigurationLoader configurationLoader = ConfigurationLoader.getConfigurationLoaderInstance();
    private static List<Character> characters;
    private static List<Character> alphabet;
    public static void main(String[] args) {
        try {
            setUp();
            preprocessing();
            caesarEncryptor();
            vigenereEncryptor();
            vigenereAnalysis();
            vigenereDecryptionExample();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    public static void setUp() throws IOException {
        System.out.println("STEP 0: SETTING UP ENVIRONMENT");
        System.out.format("Application name: %s\n", configurationLoader.getProperty("application.name"));
        System.out.format("Application version: %s\n", configurationLoader.getProperty("application.version"));
        alphabet = FileUtils.getCharListFromResource(configurationLoader.getProperty("application.files.alphabet"));
        characters = FileUtils.getCharListFromResource(configurationLoader.getProperty("application.files.text"));
        System.out.format("Plain text: %s\n", characters);
    }
    public static void preprocessing() throws IOException {
        System.out.println("STEP 1: PREPROCESSING INPUT TEXT");
        characters = Preprocessing.toLowerCase(characters);
        characters = Preprocessing.deleteWhitespacesAndPunctuations(characters);
        String accentsSubstitutionFile = configurationLoader.getProperty("application.files.substitutions.accents");
        Map<Character, Character> accentsSubstitution = FileUtils.getCharMapFromResource(accentsSubstitutionFile);
        characters = Preprocessing.substitution(characters, accentsSubstitution);
        String validAlphabetFile = configurationLoader.getProperty("application.files.alphabet");
        Set<Character> validAlphabet = FileUtils.getCharSetFromResource(validAlphabetFile);
        characters = Preprocessing.verifyAlphabet(characters, validAlphabet);
        System.out.format("Processed text: %s\n", characters);
    }
    public static void caesarEncryptor() throws IOException {
        System.out.println("STEP 2: APPLY CAESAR ENCRYPTOR");
        characters = CaesarEncryptor.encrypt(characters, alphabet, 3);
        String caesarEncryptionFile = configurationLoader.getProperty("application.files.caesar.encryption");
        FileUtils.saveListToResourceFile("output", caesarEncryptionFile, characters);
        System.out.format("Encrypted text: %s\n", characters);
        characters = CaesarEncryptor.decrypt(characters, alphabet, 3);
        String caesarDecryptionFile = configurationLoader.getProperty("application.files.caesar.decryption");
        FileUtils.saveListToResourceFile("output", caesarDecryptionFile, characters);
        System.out.format("Decrypted text: %s\n", characters);
    }
    public static void vigenereEncryptor() throws IOException {
        System.out.println("STEP 3: APPLY VIGENERE ENCRYPTOR");
        String vigenereEncryptionFile = configurationLoader.getProperty("application.files.vigenere.encryption");
        String vigenereDecryptionFile = configurationLoader.getProperty("application.files.vigenere.decryption");
        String vigenereSecretKeyFile = configurationLoader.getProperty("application.files.vigenere.secret.key");
        List<Character> vigenereSecretKey = FileUtils.getCharListFromResource(vigenereSecretKeyFile);
        System.out.printf("Vigenere Secret Key: %s\n", vigenereSecretKey);
        characters = VigenereEncryptor.encrypt(characters, alphabet, vigenereSecretKey);
        FileUtils.saveListToResourceFile("output", vigenereEncryptionFile, characters);
        System.out.format("Encrypted text: %s\n", characters);
        characters = VigenereEncryptor.decrypt(characters, alphabet, vigenereSecretKey);
        FileUtils.saveListToResourceFile("output", vigenereDecryptionFile, characters);
        System.out.printf("Decrypted text: %s\n", characters);
    }
    public static void vigenereAnalysis() throws IOException {
        System.out.println("STEP 4: VIGENERE ENCRYPTION ANALYSIS");
        String vigenereEncryptionFile = String.format("%s/%s", "output", configurationLoader.getProperty("application.files.vigenere.encryption"));
        String validAlphabetFile = configurationLoader.getProperty("application.files.alphabet");
        Set<Character> validAlphabet = FileUtils.getCharSetFromResource(validAlphabetFile);
        Map<Character, Long> frequencyMap = FileUtils.getFrequencyMapFromFile("output", vigenereEncryptionFile, validAlphabet);
        System.out.println("Frequency Map");
        mapFormat(frequencyMap, 7);
    }
    public static void vigenereDecryptionExample() throws IOException {
        System.out.println("STEP 5: VIGENERE DECRYPTION EXAMPLE");
        String vigenereSecretKeyFile = configurationLoader.getProperty("application.example.vigenere.secret.key");
        String vigenereEncryptionFile = configurationLoader.getProperty("application.example.vigenere.encryption");
        String vigenereDecryptionFile = configurationLoader.getProperty("application.example.vigenere.decryption");
        List<Character> vigenereSecretKey = FileUtils.getCharListFromResource(vigenereSecretKeyFile);
        List<Character> vigenereEncryption = Preprocessing.toLowerCase(FileUtils.getCharListFromResource(vigenereEncryptionFile));
        System.out.format("Secret key: %s\n",vigenereSecretKey);
        System.out.format("Encrypted text: %s\n", vigenereEncryption);
        List<Character> decryptedText = VigenereEncryptor.decrypt(vigenereEncryption, alphabet, vigenereSecretKey);
        FileUtils.saveListToResourceFile("output", vigenereDecryptionFile, decryptedText);
        System.out.format("Decrypted text: %s\n", decryptedText);
    }
    private static <K, V> void mapFormat(Map<K, V> map, Integer buffer) {
        IntStream.range(0, (map.size() + buffer - 1) / buffer)
                .mapToObj(i -> map.entrySet().stream()
                        .skip(i * buffer)
                        .limit(buffer)
                        .map(entry -> entry.getKey() + ": " + entry.getValue() + ",")
                        .collect(Collectors.joining("  ")))
                .forEach(System.out::println);
    }
}