package com.myLoan.br.tools.math;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
/**
 * DES安全编码组件
 * <pre>
 * 支持 DES、DESede(TripleDES,就是3DES)、AES、Blowfish、RC2、RC4(ARCFOUR)
 * DES                  key size must be equal to 56
 * DESede(TripleDES)    key size must be equal to 112 or 168
 * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
 * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
 * RC2                  key size must be between 40 and 1024 bits
 * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
 * 具体内容 需要关注 JDK Document http://.../docs/technotes/guides/security/SunProviders.html
 * </pre>
 *
 * @author
 * @version 1.0
 * @since 1.0
 */
public abstract class DESCoder {

    private static final String Mykey = "D9)3!|WU";
    private static byte[] data = new byte[]{31, 12, 33, 44, 21, 121, 22, 55}; /*new byte[] { 68, 5, 41, 51, 33, 124, 87, 85 }*/

    /**
     * DES加密
     *
     * @param message 明文字符串
     * @return
     * @throws Exception
     */
    public static byte[] encryptProcess(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(Mykey.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(new byte[]{31, 12, 33, 44, 21, 121, 22, 55});
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    /**
     * DES解密
     *
     * @param bytes 密文字节数组
     * @return
     * @throws Exception
     */
    public static String decryptProcess(byte[] bytes) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(Mykey.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(new byte[]{31, 12, 33, 44, 21, 121, 22, 55});
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        return new String(cipher.doFinal(bytes));
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * 加密数据
     *
     * @param data
     * @return
     */
    public static String encryData(String data) {
        try {
            byte[] entryData = encryptProcess(data);
            String entryBase = encryptBASE64(entryData);
            String urlEntryData = URLEncoder.encode(entryBase, "utf-8");
            return urlEntryData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解密数据
     * @param enryData
     * @return
     */
    public static String decodeData(String enryData) {
        try {
            String urlDecodeData = URLDecoder.decode(enryData, "utf-8");
            byte[] baseData = decryptBASE64(urlDecodeData);
            String data = decryptProcess(baseData);
            return data;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //emoji编码
    public static String emojiEncode(String str) {

        StringBuilder b = new StringBuilder();
        int n = str.length();
        int i = 0;
        while (i < n) {
            int code = str.codePointAt(i);
            if (code > 0xFFFF) {
                i++;
                int code2 = str.codePointAt(i);

                b.append(String.format("[E]U+%X[/E]", code));
                i++;
            } else {
                b.appendCodePoint(code);
                i++;
            }
        }

        String ret = b.toString();
        return ret;
    }

    //emoji解码
    public static String emojiDecode(String str) {

        final String REGEX_STR = "\\[e\\]U\\+(.*?)\\[\\/e\\]";//u(.*?)
        String ret = str;
        Pattern emojiPatten = Pattern.compile(REGEX_STR, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emojiPatten.matcher(ret);
        while (matcher.find()) {
            String key = matcher.group();
            String s1 = matcher.group(1);
            //String s2=matcher.group(2);

            StringBuilder sb = new StringBuilder();
            //sb.append(Integer.parseInt(s1, 16));
            //sb.append(Integer.parseInt(s2, 16));
            sb.appendCodePoint(Integer.parseInt(s1, 16));
            //sb.appendCodePoint(Integer.parseInt(s2, 16));

            String s = sb.toString();

            ret = ret.replace(key, s);
            //Log.w("match",key);
        }

        return ret;
    }

}
