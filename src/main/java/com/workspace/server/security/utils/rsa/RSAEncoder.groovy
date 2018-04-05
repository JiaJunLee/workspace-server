package com.workspace.server.security.utils.rsa

import javax.crypto.Cipher
import java.security.PublicKey

/**
 * Created by 51998 on 2018/2/26.
 */
class RSAEncoder {

    private PublicKey publicKey

    RSAEncoder (PublicKey publicKey) {
        this.publicKey = publicKey
    }

    String[] encode(String str) {
        int blockSize = str.length() / RSA.RSA_MAX_SIZE
        blockSize = str.length() % RSA.RSA_MAX_SIZE == 0 ? blockSize : blockSize + 1
        String[] textBlock = new String[blockSize]
        String[] splitStr = new String[blockSize]
        for (int i = 0; i < blockSize; i++) {
            int endIndex
            if (i == (blockSize - 1))
                endIndex = str.length()
            else
                endIndex = (i + 1) * RSA.RSA_MAX_SIZE
            textBlock[i] = str.substring(i * RSA.RSA_MAX_SIZE, endIndex)
        }
        Cipher cipher = Cipher.getInstance(RSA.CIPHER_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        for (int i = 0; i < splitStr.length; i++) {
            splitStr[i] = Base64.getEncoder().encodeToString(cipher.doFinal(textBlock[i].getBytes()))
        }
        return splitStr
    }

}
