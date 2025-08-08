
package com.ruoyi.web.test.core.util;

public class AESUtil {
    // 完全跳过加密解密逻辑
    public static String encrypt(String plaintext) {
        return plaintext; // 直接返回原文
    }

    public static String decrypt(String ciphertext) {
        return ciphertext; // 直接返回原文
    }

    public static class MedicalSecurityException extends RuntimeException {
        public MedicalSecurityException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
