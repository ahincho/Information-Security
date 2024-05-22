def fill_alphabet(alpha_len):

    alpha = "".join(chr(ord('a') + i) for i in range(alpha_len))
    return alpha

def encode_cesar(content, displacement):
    alpha_len = 26

    # Filling alphabet
    alpha = fill_alphabet(alpha_len)

    # Process
    out = "".join(alpha[(ord(c)-ord('a')+displacement) % alpha_len] for c in content)
    return out
