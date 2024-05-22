def fill_alphabet(alpha_len):
    """
    alpha = "".join(chr(ord('a') + i) for i in range(14))
    alpha += 'ñ'
    alpha += "".join(chr(ord(o') + i) for i in range(12))
    """
    alpha = "".join(chr(ord('a') + i) for i in range(alpha_len))
    return alpha


def decode_autokey(encoded_content, key):
    alpha_len = 26

    # Filling alphabet
    alpha = fill_alphabet(alpha_len)

    # Process
    out = ""

    for i in range(len(encoded_content)):
        c = ord(encoded_content[i]) - ord('a')
        k = ord(key[i]) if i < len(key) else ord(out[i-len(key)])
        k -= ord('a')
        out += alpha[(c + (alpha_len - k)) % alpha_len]
    return out
