package com.unsa.information.security.encryption;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VigenereEncryptor {
    public static List<Character> encrypt(List<Character> characters, List<Character> alphabet, List<Character> secretKey) {
        List<Character> validatedSecretKey = secretKey.stream().filter(alphabet::contains).toList();
        AtomicInteger iterator = new AtomicInteger();
        return characters.stream()
                .filter(alphabet::contains)
                .map(character -> {
                    iterator.set(iterator.get() % secretKey.size());
                    int index = alphabet.indexOf(character);
                    int padding = alphabet.indexOf(validatedSecretKey.get(iterator.get()));
                    int newIndex = (index + padding) % alphabet.size();
                    iterator.getAndIncrement();
                    return alphabet.get(newIndex);
                })
                .toList();
    }
    public static List<Character> decrypt(List<Character> characters, List<Character> alphabet, List<Character> secretKey) {
        List<Character> validatedSecretKey = secretKey.stream().filter(alphabet::contains).toList();
        AtomicInteger iterator = new AtomicInteger();
        return characters.stream()
                .filter(alphabet::contains)
                .map(character -> {
                    iterator.set(iterator.get() % secretKey.size());
                    int index = alphabet.indexOf(character);
                    int padding = alphabet.indexOf(validatedSecretKey.get(iterator.get()));
                    int newIndex = Math.floorMod(index - padding, alphabet.size());
                    iterator.getAndIncrement();
                    return alphabet.get(newIndex);
                })
                .toList();
    }
}
