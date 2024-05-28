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
    #letter_counting(5, encoding_content)

    # Getting the decoding conent (Cesar version)
    decoding_content = decode_cesar(encoding_content, displacement)

    print(f"Original text: {content}")
    print(f"Encoding text: {encoding_content}")
    print(f"Decoding text: {decoding_content}")


if __name__ == "__main__":
    main()
