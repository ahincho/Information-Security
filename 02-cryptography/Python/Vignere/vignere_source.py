from source import *


def encode_vignere(content, key):
    alpha_len = 26

    # Filling alphabet
    alpha = fill_alphabet(alpha_len)

    # Process
    out = ""

    for i in range(len(content)):
        posCont = ord(content[i])-ord('a')
        posKey = ord(key[i % len(key)])-ord('a')
        encripPos = (posCont + posKey) % alpha_len
        out += alpha[encripPos]
    
    return out

