package com.workspace.server.security.utils.rsa

import groovy.util.logging.Slf4j
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

/**
 * Created by 51998 on 2018/2/26.
 */
@Slf4j
class RSA {

    private static final String KEY_FILE_NAME = 'rsa_key.properties'

    public static final String PUBLIC_KEY_ALISA = 'public_key'
    public static final String PRIVATE_KEY_ALISA = 'private_key'

    public static final String KEY_ALGORITHM = 'RSA'
    public static final String CIPHER_ALGORITHM = 'RSA/ECB/PKCS1Padding'

    public static final int RSA_KEY_SIZE = 1024
    public static final int RSA_MAX_SIZE = 100

    static final RSAEncoder rsaEncoder
    static final RSADecoder rsaDecoder

    private static final PublicKey publicKey
    private static final PrivateKey privateKey

    static {
        log.info('[workspace-server] Initialize RSA encrypt util')
        File keyFile = new File(new URL(getClass().getResource('/'), KEY_FILE_NAME).toURI())
        if(keyFile.exists()){
            Properties properties = new Properties()
            properties.load(keyFile.newInputStream())
            publicKey = restorePublicKey(Base64.getDecoder().decode(String.valueOf(properties.get(PUBLIC_KEY_ALISA))))
            privateKey = restorePrivateKey(Base64.getDecoder().decode(String.valueOf(properties.get(PRIVATE_KEY_ALISA))))
        }else{
            keyFile.createNewFile()
            Map keyPairMap = generateKeyPairMap()
            publicKey = (RSAPublicKey) keyPairMap[PUBLIC_KEY_ALISA]
            privateKey = (RSAPrivateKey) keyPairMap[PRIVATE_KEY_ALISA]
            Properties properties = new Properties()
            properties.put(PUBLIC_KEY_ALISA, Base64.getEncoder().encodeToString(publicKey.getEncoded()))
            properties.put(PRIVATE_KEY_ALISA, Base64.getEncoder().encodeToString(privateKey.getEncoded()))
            properties.store(keyFile.newOutputStream(), 'RSA_KEY_PAIR')
        }
        rsaEncoder = new RSAEncoder(publicKey)
        rsaDecoder = new RSADecoder(privateKey)
    }

    static Map generateKeyPairMap() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM)
        keyPairGen.initialize(RSA_KEY_SIZE)
        KeyPair keyPair = keyPairGen.generateKeyPair()
        Map keyPairMap = [:]
        keyPairMap[PUBLIC_KEY_ALISA] = (RSAPublicKey) keyPair.getPublic()
        keyPairMap[PRIVATE_KEY_ALISA] = (RSAPrivateKey) keyPair.getPrivate()
        return keyPairMap
    }

    static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes)
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM)
        return factory.generatePublic(x509EncodedKeySpec)
    }

    static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes)
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM)
        return factory.generatePrivate(pkcs8EncodedKeySpec)
    }

    static String getPublicKey () {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded())
    }

}
