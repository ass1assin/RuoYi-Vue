////package com.ruoyi.web.test.core.util;
////
////
////import javax.crypto.Cipher;
////import javax.crypto.SecretKey;
////import javax.crypto.spec.GCMParameterSpec;
////import javax.crypto.spec.SecretKeySpec;
////import java.nio.charset.StandardCharsets;
////import java.security.SecureRandom;
////import java.util.Base64;
////import java.util.Objects;
////
////public class AESUtil {
////    private static final String ALGORITHM = "AES/GCM/NoPadding";
////    private static final int TAG_LENGTH_BIT = 128;
////    private static final int IV_LENGTH_BYTE = 12;
//////    private static final String SECRET_KEY = System.getenv("MEDICAL_ENC_KEY");
////
//////    //////////////////
////    // 修改密钥获取方式，提供默认值
////    private static final String SECRET_KEY = getEncryptionKey();
////
////    private static String getEncryptionKey() {
////        String key = System.getenv("MEDICAL_ENC_KEY");
////        if (key == null || key.length() != 32) {
////            // 生产环境应严格禁止使用默认密钥
////            if (Objects.equals(System.getProperty("spring.profiles.active", ""), "dev")) {
////                return "default-dev-key-32-characters-long"; // 32字符
////            }
////            throw new SecurityException("加密密钥未配置或长度错误（需要32字节）");
////        }
////        return key;
////    }
//////    //////////////////
////
////    private AESUtil() {}
////
////    public static String encrypt(String plaintext) {
////        if (SECRET_KEY == null || SECRET_KEY.length() != 32) {
////            throw new SecurityException("加密密钥未配置或长度错误（需要32字节）");
////        }
////
////        try {
////            // 生成随机初始向量
////            byte[] iv = new byte[IV_LENGTH_BYTE];
////            new SecureRandom().nextBytes(iv);
////
////            // 创建密钥和加密器
////            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
////            Cipher cipher = Cipher.getInstance(ALGORITHM);
////            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
////
////            // 执行加密
////            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
////
////            // 组合IV + 密文
////            byte[] combined = new byte[iv.length + encryptedBytes.length];
////            System.arraycopy(iv, 0, combined, 0, iv.length);
////            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
////
////            return Base64.getEncoder().encodeToString(combined);
////        } catch (Exception e) {
////            throw new MedicalSecurityException("AES加密失败: " + e.getMessage(), e);
////        }
////    }
////
////    public static String decrypt(String ciphertext) {
////        if (SECRET_KEY == null) {
////            throw new SecurityException("加密密钥未配置");
////        }
////
////        try {
////            // 解码Base64
////            byte[] combined = Base64.getDecoder().decode(ciphertext);
////
////            // 提取IV（前12字节）
////            if (combined.length < IV_LENGTH_BYTE) {
////                throw new IllegalArgumentException("无效的密文长度");
////            }
////            byte[] iv = new byte[IV_LENGTH_BYTE];
////            System.arraycopy(combined, 0, iv, 0, iv.length);
////
////            // 提取加密内容
////            byte[] encryptedBytes = new byte[combined.length - IV_LENGTH_BYTE];
////            System.arraycopy(combined, IV_LENGTH_BYTE, encryptedBytes, 0, encryptedBytes.length);
////
////            // 创建密钥和解密器
////            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
////            Cipher cipher = Cipher.getInstance(ALGORITHM);
////            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
////
////            // 执行解密
////            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
////            return new String(decryptedBytes, StandardCharsets.UTF_8);
////        } catch (Exception e) {
////            throw new MedicalSecurityException("AES解密失败: " + e.getMessage(), e);
////        }
////    }
////
////    public static class MedicalSecurityException extends RuntimeException {
////        public MedicalSecurityException(String message, Throwable cause) {
////            super(message, cause);
////        }
////    }
////}
//package com.ruoyi.web.test.core.util;
//
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Autowired;
//import javax.annotation.PostConstruct;
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.GCMParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.SecureRandom;
//import java.util.Base64;
//
//@Component
//public class AESUtil {
//    private static final String ALGORITHM = "AES/GCM/NoPadding";
//    private static final int TAG_LENGTH_BIT = 128;
//    private static final int IV_LENGTH_BYTE = 12;
//
//    private static String SECRET_KEY = null;
//    private static Environment env;
//
//    @Autowired
//    public AESUtil(Environment environment) {
//        env = environment;
//    }
//
//    @PostConstruct
//    public void init() {
//        // 延迟加载密钥
//        getSecretKey();
//    }
//
//    private static String getSecretKey() {
//        if (SECRET_KEY != null) {
//            return SECRET_KEY;
//        }
//
//        String key = System.getenv("MEDICAL_ENC_KEY");
//        boolean allowDefaultKey = false;
//
//        try {
//            // 检查是否允许使用默认密钥
//            allowDefaultKey = "true".equalsIgnoreCase(
//                    env.getProperty("medical.security.allow-default-key")
//            );
//        } catch (Exception e) {
//            // 忽略配置读取异常
//        }
//
//        if (key == null || key.length() != 32) {
//            if (allowDefaultKey) {
//                SECRET_KEY = "default-dev-key-32-characters-long";
//            } else {
//                throw new SecurityException("加密密钥未配置或长度错误（需要32字节）");
//            }
//        } else {
//            SECRET_KEY = key;
//        }
//
//        return SECRET_KEY;
//    }
//
//    public static String encrypt(String plaintext) {
//        String secretKey = getSecretKey();
//        if (secretKey == null || secretKey.length() != 32) {
//            throw new SecurityException("加密密钥未配置或长度错误（需要32字节）");
//        }
//
//        try {
//            // 生成随机初始向量
//            byte[] iv = new byte[IV_LENGTH_BYTE];
//            new SecureRandom().nextBytes(iv);
//
//            // 创建密钥和加密器
//            SecretKey key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
//
//            // 执行加密
//            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
//
//            // 组合IV + 密文
//            byte[] combined = new byte[iv.length + encryptedBytes.length];
//            System.arraycopy(iv, 0, combined, 0, iv.length);
//            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
//
//            return Base64.getEncoder().encodeToString(combined);
//        } catch (Exception e) {
//            throw new MedicalSecurityException("AES加密失败: " + e.getMessage(), e);
//        }
//    }
//
//    public static String decrypt(String ciphertext) {
//        String secretKey = getSecretKey();
//        if (secretKey == null) {
//            throw new SecurityException("加密密钥未配置");
//        }
//
//        try {
//            // 解码Base64
//            byte[] combined = Base64.getDecoder().decode(ciphertext);
//
//            // 提取IV（前12字节）
//            if (combined.length < IV_LENGTH_BYTE) {
//                throw new IllegalArgumentException("无效的密文长度");
//            }
//            byte[] iv = new byte[IV_LENGTH_BYTE];
//            System.arraycopy(combined, 0, iv, 0, iv.length);
//
//            // 提取加密内容
//            byte[] encryptedBytes = new byte[combined.length - IV_LENGTH_BYTE];
//            System.arraycopy(combined, IV_LENGTH_BYTE, encryptedBytes, 0, encryptedBytes.length);
//
//            // 创建密钥和解密器
//            SecretKey key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
//
//            // 执行解密
//            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//            return new String(decryptedBytes, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            throw new MedicalSecurityException("AES解密失败: " + e.getMessage(), e);
//        }
//    }
//
//    public static class MedicalSecurityException extends RuntimeException {
//        public MedicalSecurityException(String message, Throwable cause) {
//            super(message, cause);
//        }
//    }
//}
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
