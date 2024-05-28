import sys

def read_args1():
    if len(sys.argv) != 3:
        print("You must write a loaded file and a saved file name\n\tExample: ´python script.py loaded_file.txt saved_file.txt´", file=sys.stderr)
        exit(1)

    return sys.argv[1], sys.argv[2]


def read_args2():
    if len(sys.argv) != 2:
        print("You must write a loaded file name\n\tExample: ´python script.py loaded_file.txt´", file=sys.stderr)
        exit(1)

    return sys.argv[1]


def open_file(file_name):
    with open(file_name) as file:
        return file.read()


def save_content(file_name, content):
    # Transforming to just a text
    content = "".join(content)

    # Saving content
    with open(file_name, "w") as file:
        file.write(content)


def clean_content(content):
    # Lower case
    content = content.lower()

    # Filtering just alphabet letters
    out = ""

    for c in content:
        if c >= 'a' and c <= 'z': # or c == 'ñ':
            out += c

    return out


