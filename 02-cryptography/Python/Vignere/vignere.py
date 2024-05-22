from vignere_source import *


def main():
    # Getting file name
    content_file_name, key_file_name = read_args1()

    # Reading content and key
    content = open_file(content_file_name)
    key = open_file(key_file_name)

    # Cleaning the content
    content = clean_content(content)

    # Getting encode content (Vignere version)
    encoding_content = encode_vignere(content, key)

    print(f"Original text: {content}")
    print(f"Encoding text: {encoding_content}")


if __name__ == "__main__":
    main()
