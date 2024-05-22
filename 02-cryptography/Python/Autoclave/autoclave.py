from source import *
from autokey_source import *


def main():
    # Getting file name
    encoding_content_file_name, key_file_name = read_args1()

    # Reading content
    encoded_content = open_file(encoding_content_file_name)
    key = open_file(key_file_name)

    # Processing input data (cleaning)
    encoded_content = clean_content(encoded_content)
    key = clean_content(key)

    # Decoding content (Autoclave version)
    print(f"key: {key}")
    decoded_content = decode_autokey(encoded_content, key)

    print(f"Encoded content: {encoded_content}")
    print(f"Decoded content: {decoded_content}")


if __name__ == "__main__":
    main()
