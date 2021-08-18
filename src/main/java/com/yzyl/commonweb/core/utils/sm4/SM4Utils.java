package com.yzyl.commonweb.core.utils.sm4;

import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SM4Utils {
    private String secretKey = "";

    private String iv = "";

    private boolean hexString = false;

    public SM4Utils() {
    }

    public static SM4Utils getInstance(String iv) {
        SM4Utils sm = new SM4Utils();
        sm.setIv(iv);
        return sm;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public boolean isHexString() {
        return hexString;
    }

    public void setHexString(boolean hexString) {
        this.hexString = hexString;
    }

    private byte[] getkey(String key) {
        try {
            byte[] keyBytes = new byte[16];
            byte[] t = new byte[0];

            t = key.getBytes("UTF-8");

            if (t.length < 16) {
                for (int i = 0; i < t.length; i++) {
                    keyBytes[i] = t[i];
                }
            } else {
                keyBytes = t;
            }
            return keyBytes;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptData_ECB(String plainText) {
        try {
            SM4Context ctx = new SM4Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = getkey(secretKey);
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("UTF-8"));
            String cipherText = Base64Utils.encodeToString(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_ECB(String cipherText) {
        try {
            SM4Context ctx = new SM4Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
            } else {
                keyBytes = getkey(secretKey);
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, Base64Utils.decodeFromString(cipherText));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptData_CBC(String plainText) {
        try {
            SM4Context ctx = new SM4Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            } else {
                keyBytes = getkey(secretKey);
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("UTF-8"));
            String cipherText = Base64Utils.encodeToString(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_CBC(String cipherText) {
        try {
            SM4Context ctx = new SM4Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(secretKey);
                ivBytes = Util.hexStringToBytes(iv);
            } else {
                keyBytes = getkey(secretKey);
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, Base64Utils.decodeFromString(cipherText));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String strEncode(String value, String key) throws IOException {
        SM4Utils sm4Utils = new SM4Utils();
        sm4Utils.secretKey = key;
        sm4Utils.hexString = false;
        String s = sm4Utils.encryptData_ECB(value);
        s = s.trim().replaceAll("\r|\n", "");
        s = s.replace("+", "%2B");
        s = s.replace("/", "%2F");
        return s;
    }

    public static String strDecode(String value, String key) throws IOException {
        value = value.replace("%2B", "+");
        value = value.replace("%2F", "/");
        SM4Utils sm4Utils = new SM4Utils();
        sm4Utils.secretKey = key;
        sm4Utils.hexString = false;
        String s = sm4Utils.decryptData_ECB(value);
        return s;
    }


    public static void main(String[] args) {
        String plainText = "fsws01";

        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey("admin");
        sm4.setIv("com.wondersgroup");

        System.out.println("====CBC模式====");
        System.out.println("明文：" + plainText);
        System.out.println("密文：" + (sm4.encryptData_CBC(plainText)));
        System.out.println("解密：" + sm4.decryptData_CBC(sm4.encryptData_CBC(plainText)));


        sm4.setSecretKey("com.wondersgroup");
        sm4.setIv("");
        System.out.println("====ECB模式====");
        System.out.println("明文：" + plainText);
        System.out.println("密文：" + (sm4.encryptData_ECB(plainText)));
        System.out.println("解密：" + sm4.decryptData_ECB(sm4.encryptData_ECB(plainText)));
    }
}
