import sys


def read_args1():
    if len(sys.argv) != 3:
        print("You must write a content file and a key file name\n\tExample: ´python script.py content.txt key.txt´", file=sys.stderr)
        exit(1)

    return sys.argv[1], sys.argv[2]


def read_args2():
    if len(sys.argv) != 2:
        print("You must write a content file name\n\tExample: ´python script.py content.txt´", file=sys.stderr)
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


def letter_counting(top, content):
    count = dict()

    for c in content:
        count[c] = count[c] + 1 if c in count else 1

    # Sorting by value
    count = dict(reversed(sorted(count.items(), key=lambda item: item[1])))

    # Printing just the top counts
    i = 0

    for key, value in count.items():
        print(f"{i+1}. {key}: {value}")
            
        if i >= top:
            break
        i += 1


def fill_alphabet(alpha_len):
    """
    alpha = "".join(chr(ord('a') + i) for i in range(14))
    alpha += 'ñ'
    alpha += "".join(chr(ord('o') + i) for i in range(12))
    """
    alpha = "".join(chr(ord('a') + i) for i in range(alpha_len))
    return alpha
