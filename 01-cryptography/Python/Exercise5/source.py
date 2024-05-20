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
    with open(file_name, encoding='utf-8') as file:
        return list(file.read())


def debug_text(content):
    for c in content:
        print(c, end="")


def save_content(file_name, content):
    # Transforming to just a text
    content = "".join(content)

    # Saving content
    with open(file_name, "w") as file:
        file.write(content)
        

def replace_content(content, dictionary_letters):

    # Iterating the content and replacing letters
    for i in range(len(content)):
            # Iterating letters
            if (content[i] in dictionary_letters):
                content[i] = dictionary_letters[content[i]]