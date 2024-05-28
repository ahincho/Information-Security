package com.unsa.information.security.preprocessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class Preprocessing {
    public static List<Character> verifyAlphabet(List<Character> characters, Set<Character> alphabet) {
        return characters.stream()
                .filter(alphabet::contains)
                .toList();
    }
    public static List<Character> deleteWhitespacesAndPunctuations(List<Character> characters) {
        return characters.stream()
                .filter(c ->
                        !Character.isWhitespace(c) && !Character.isSpaceChar(c) && Character.isLetterOrDigit(c)
                ).toList();
    }
    public static List<Character> toLowerCase(List<Character> characters) {
        return characters.stream()
                .map(Character::toLowerCase)
                .toList();
    }
    public static List<Character> toUpperCase(List<Character> characters) {
        return characters.stream()
                .map(Character::toUpperCase)
                .toList();
    }
    public static List<Character> substitution(List<Character> characters, Map<Character, Character> replacements) {
        return characters.stream().map(character -> replacements.getOrDefault(character, character)).toList();
    }
    public static List<Character> includeMeaninglessCharacters(List<Character> characters , List<Character> meaninglessCharacters) {
        List<Character> result = new ArrayList<>(characters);
        IntStream.range(0, characters.size() - 1)
                .filter(index -> characters.get(index).equals(characters.get(index + 1)))
                .forEach(match -> {
                    Collections.shuffle(meaninglessCharacters);
                    result.addAll(match + 1, meaninglessCharacters);
                });
        return result;
    }
}