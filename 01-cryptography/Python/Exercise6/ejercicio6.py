from source import *


def replace_special_letters(content):
    
    # Creating a dictionary with the content
    letters = "ahñkvwzr"
    letters = letters + letters.upper()
    replaced_letters = "utelfbyp"
    replaced_letters = replaced_letters + replaced_letters.upper()

    # Creating dictionary
    letters_dictionary = dict()

    for i in range(len(letters)):
        letters_dictionary[letters[i]] = replaced_letters[i]

    # Replacing letters
    replace_content(content, letters_dictionary)


def delete_quotes(content):

    # Defining qoute vowels
    qoute_vowels = "áéíóú"
    replaced_qoute_vowels = "aeiou"

    # Creating dictionary
    qoutes_vowels_dictionary = dict()

    for i in range(len(qoute_vowels)):
        qoutes_vowels_dictionary[qoute_vowels[i]] = replaced_qoute_vowels[i]
    
    replace_content(content, qoutes_vowels_dictionary)


def transform_to_upper_letters(content):

    # Iterating the content
    for i in range(len(content)):
        content[i] = content[i].upper()


def delete_white_spaces_and_punctuation_marks(content):
    
    # Defining special symbols
    symbols = " ,;.:-_{}?¿¡![]<>'\"\n"
    

    # Creating dictionary
    symbols_dictionary = dict()

    for i in range(len(symbols)):
        symbols_dictionary[symbols[i]] = ""

    replace_content(content, symbols_dictionary)

def get_letters_frecuency(content):
    
    # Creating dictionary to count letters frecuency
    letters_frecuency = dict()

    # Iterating content
    for letter in content:
        letters_frecuency[letter] = letters_frecuency[letter] + 1 if letter in letters_frecuency else 1

    # Sorting by values
    letters_frecuency = sorted(letters_frecuency.items(), key=lambda item: item[1])
    letters_frecuency.reverse()
    
    # Debugging 5 characters
    LIMIT = min(6, len(letters_frecuency))

    for i in range(LIMIT):
        print(f"{i + 1}. {letters_frecuency[i][0]}: {letters_frecuency[i][1]}")

def kasiski(content):
    
    # Creating dictionary
    trigram = dict()

    # Process content
    for i in range(len(content) - 2):
        trigram_word = content[i : i + 3]

        # To completed word
        trigram_word = "".join(trigram_word)

        # Calculating the trigrams
        if trigram_word in trigram:
            trigram[trigram_word].append(i)
        else:
            trigram[trigram_word] = [i]

    print(trigram)
    # TODO: Calculate distances


def main():
    
    # Getting arguments
    file_to_read, file_to_save = read_args1()

    # Loading content
    content = open_file(file_to_read)

    # Task 4.1: Replacing special letters
    replace_special_letters(content)

    # Task 4.2: Deleting quotes
    delete_quotes(content)

    # Task 4.3: Transforming to uppers letters
    transform_to_upper_letters(content)

    # Task 4.4: Deleting white spaces and punctation marks
    delete_white_spaces_and_punctuation_marks(content)
    # Task 4.5: Getting letters frecuency
    get_letters_frecuency(content)
    # Task 4.6: Kasiski implementation
    kasiski(content)
    # Saving the content
    save_content(file_to_save, content)

    debug_text(content)


if __name__ == "__main__":
    main()