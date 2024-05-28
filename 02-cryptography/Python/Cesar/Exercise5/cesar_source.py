from source import *


def encode_cesar(content, displacement):
    alpha_len = 26

    # Filling alphabet
    alpha = fill_alphabet(alpha_len)

    # Process
    out = "".join(alpha[(ord(c)-ord('a')+displacement) % alpha_len] for c in content)
    return out


def decode_cesar(enconding_content, displacement):
    alpha_len = 26

    # Filling alphabet
    alpha = fill_alphabet(alpha_len)

    # Calculate new displacement to the right
    new_displacement = alpha_len - displacement

    # Process
    out = "".join(alpha[(ord(c)-ord('a')+new_displacement) % alpha_len] for c in enconding_content)
    return out
