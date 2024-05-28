package com.unsa.information.security.encryption;

import java.util.List;

public class CaesarEncryptor {
    public static List<Character> encrypt(List<Character> characters, List<Character> alphabet, Integer shift) {
        return characters.stream()
                .filter(alphabet::contains)
                .map(character -> {
                    int index = alphabet.indexOf(character);
                    int newIndex = (index + shift) % alphabet.size();
                    return alphabet.get(newIndex);
                })
                .toList();
    }
    public static List<Character> decrypt(List<Character> characters, List<Character> alphabet, Integer shift) {
        return characters.stream()
                .filter(alphabet::contains)
                .map(character -> {
                    int index = alphabet.indexOf(character);
                    int newIndex = Math.floorMod(index - shift, alphabet.size());
                    return alphabet.get(newIndex);
                })
                .toList();
    }
}