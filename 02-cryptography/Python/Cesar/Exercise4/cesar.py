from source import *
from cesar_source import *


def main():
    # Getting file name
    file_name = read_args2()

    # Reading content
    content = open_file(file_name)

    # Cleaning the content
    content = clean_content(content)

    # Getting encode content (Cesar version)
    displacement = 3
    encoding_content = encode_cesar(content, displacement)

    # Printing the letter counting
    letter_counting(5, encoding_content)

    print(encoding_content)


if __name__ == "__main__":
    main()
