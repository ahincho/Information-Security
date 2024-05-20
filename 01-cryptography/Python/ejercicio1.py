import sys

def open_file(file_name):
    with open(file_name, encoding='utf-8') as file:
        return list(file.read())

def debug_text(content):
    for c in content:
        print(c, end="") 
   
def replace_special_letters(content):
    
    # Creating a dictionary with the content
    letters = "ahñkvwzr"
    letters = letters + letters.upper()
    replaced_letters = "utelfbyp"
    replaced_letters = replaced_letters + replaced_letters.upper()

    # Iterating the content and replacing letters
    for i in range(len(content)):
            # Iterating letters
            for j in range(len(letters)):
                if (letters[j] ==content[i]):
                    content[i] = replaced_letters[j]

def main():
  
    # Getting arguments
    file_name = sys.argv[1]

    # Loading content
    content = open_file(file_name)

    # Task 4.1: Replacing special letters
    replace_special_letters(content)

    debug_text(content)

if __name__ == "__main__":
    main()
