package com.workspace.server.controller


import javax.crypto.KeyGenerator
import javax.crypto.Mac
import javax.crypto.SecretKey

/**
 * Created by 51998 on 2018/2/24.
 */
KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512") // 可填入 HmacSHA1，HmacSHA256 等
SecretKey key = keyGenerator.generateKey()

Mac mac = Mac.getInstance("HmacSHA512")
mac.init(key)

byte[] resultBytes = mac.doFinal('U8y2gnv.'.getBytes())
String resultString = Base64.getEncoder().encodeToString(resultBytes)

System.err.println(Base64.getEncoder().encodeToString(key.getEncoded()))

System.err.println(resultString)