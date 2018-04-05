package com.workspace.server.security.utils.rsa

import javax.crypto.Cipher
import java.security.PrivateKey

/**
 * Created by 51998 on 2018/2/26.
 */
class RSADecoder {

    private PrivateKey privateKey

    RSADecoder (PrivateKey privateKey) {
        this.privateKey = privateKey
    }

    String decode (String[] splitStr) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA.CIPHER_ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        StringBuilder sb = new StringBuilder()
        for (int i = 0; i < splitStr.length; i++) {
            sb.append(new String(cipher.doFinal(Base64.getDecoder().decode(splitStr[i]))))
        }
        return sb.toString()
    }

}
