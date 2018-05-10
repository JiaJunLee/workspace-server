package com.workspace.server.security.utils

import org.springframework.stereotype.Component
import javax.crypto.KeyGenerator
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * Created by 51998 on 2018/2/22.
 */
@Component
class HMAC {

    public static final String HMAC_MD5 = 'HmacMD5'
    public static final String HMAC_SHA1 = 'HmacSHA1'
    public static final String HMAC_SHA256 = 'HmacSHA256'
    public static final String HMAC_SHA384 = 'HmacSHA384'
    public static final String HMAC_SHA512 = 'HmacSHA512'

    String generateKey (String algorithm) {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm)
        return Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded())
    }

    String digest (String str, String key, String algorithm) {
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), algorithm)
        Mac mac = Mac.getInstance(algorithm)
        mac.init(secretKey)
        return Base64.getEncoder().encodeToString(mac.doFinal(str.getBytes()))
    }

    Boolean validate (String str, String key, String algorithm, String expect) {
        boolean result = false
        try {
            result = digest(str, key, algorithm) == expect
        } catch (Exception e) {
            result = false
        } finally {
            return result
        }
    }

}
