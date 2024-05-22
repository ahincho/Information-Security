package com.unsa.information.security.main;

import com.unsa.information.security.configuration.ConfigurationLoader;
import com.unsa.information.security.encryption.CaesarEncryptor;
import com.unsa.information.security.preprocessing.Preprocessing;
import com.unsa.information.security.utils.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Application {
    private static final ConfigurationLoader configurationLoader = ConfigurationLoader.getConfigurationLoaderInstance();
    private static List<Character> characters;
    private static List<Character> alphabet;
    public static void main(String[] args) {
        try {
            setUp();
            preprocessing();
            caesarEncryptor();
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
}