package com.graze.pastoral.notes.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.graze.pastoral.notes.domain.dto.user.MiniProgramPhoneOutput;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/8/17 11:07
 */
public class wxHelper {

    // region  解析微信小程序手机号码

    // 算法名
    private static final String KEY_NAME = "AES";
    // 加解密算法/模式/填充方式
    // ECB模式只用密钥即可对数据进行加密解密，CBC模式需要添加一个iv
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    /**
     * 解密小程序手机号码
     *
     * @param session_key
     * @param encryptedData
     * @param iv
     * @return
     */
    public static MiniProgramPhoneOutput getMiniProgramMobilePhone(String session_key, String encryptedData, String iv) {
        String json = null;
        byte[] encrypted64 = org.apache.commons.codec.binary.Base64.decodeBase64(encryptedData);
        byte[] key64 = org.apache.commons.codec.binary.Base64.decodeBase64(session_key);
        byte[] iv64 = org.apache.commons.codec.binary.Base64.decodeBase64(iv);
        byte[] data;
        try {
            init();
            json = new String(decrypt(encrypted64, key64, generateIV(iv64)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) {
            throw new RuntimeException("解析手机号失败");
        }
//        System.out.println(json);
        MiniProgramPhoneOutput output = JSONObject.parseObject(json, MiniProgramPhoneOutput.class);
        return output;
    }

    /**
     * 初始化密钥
     */
    private static void init() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator.getInstance(KEY_NAME).init(128);
    }

    /**
     * 生成iv
     */
    private static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        // iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
        // Arrays.fill(iv, (byte) 0x00);
        AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_NAME);
        params.init(new IvParameterSpec(iv));
        return params;
    }

    /**
     * 生成解密
     */
    private static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv)
            throws Exception {
        Key key = new SecretKeySpec(keyBytes, KEY_NAME);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedData);
    }

    // endregion

}
