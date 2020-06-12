package com.example.demo.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author ：jyk
 * @date ：Created in 2020/5/7 17:27
 * @description：
 */
@Slf4j
public class AesUtil {
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";
    // 设置缓存大小
    private static final int BUFFERSIZE = 1024;
    private static final String DATE_FORMAT = "utf-8";
    private static KeyGenerator keyGen;
    private static Cipher cipher;
    private static boolean isInited = false;

    private static void init() {
        try {
            /**
             * 为指定算法生成一个 KeyGenerator 对象。 此类提供（对称）密钥生成器的功能。 密钥生成器是使用此类的某个
             * getInstance 类方法构造的。 KeyGenerator 对象可重复使用，也就是说，在生成密钥后， 可以重复使用同一
             * KeyGenerator 对象来生成进一步的密钥。 生成密钥的方式有两种：与算法无关的方式，以及特定于算法的方式。
             * 两者之间的惟一不同是对象的初始化： 与算法无关的初始化 所有密钥生成器都具有密钥长度 和随机源 的概念。 此
             * KeyGenerator 类中有一个 init 方法，它可采用这两个通用概念的参数。 还有一个只带 keysize 参数的
             * init 方法， 它使用具有最高优先级的提供程序的 SecureRandom 实现作为随机源 （如果安装的提供程序都不提供
             * SecureRandom 实现，则使用系统提供的随机源）。 此 KeyGenerator 类还提供一个只带随机源参数的 inti
             * 方法。 因为调用上述与算法无关的 init 方法时未指定其他参数，
             * 所以由提供程序决定如何处理将与每个密钥相关的特定于算法的参数（如果有）。 特定于算法的初始化
             * 在已经存在特定于算法的参数集的情况下， 有两个具有 AlgorithmParameterSpec 参数的 init 方法。
             * 其中一个方法还有一个 SecureRandom 参数， 而另一个方法将已安装的高优先级提供程序的 SecureRandom
             * 实现用作随机源 （或者作为系统提供的随机源，如果安装的提供程序都不提供 SecureRandom 实现）。
             * 如果客户端没有显式地初始化 KeyGenerator（通过调用 init 方法）， 每个提供程序必须提供（和记录）默认初始化。
             */
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            log.error("error",e);
        }
        // 初始化此密钥生成器，使其具有确定的密钥长度。
        keyGen.init(128); // 128位的AES加密
        try {
            // 生成一个实现指定转换的 Cipher 对象。
            cipher = Cipher.getInstance(ALGORITHM_STR);
        } catch (NoSuchAlgorithmException e) {
            log.error("error",e);
        } catch (NoSuchPaddingException e) {
            log.error("error",e);
        }
        // 标识已经初始化过了的字段
        isInited = true;
    }

    public static String encodeZip(String content, String key)
            throws UnsupportedEncodingException {
        // 加密之后的字节数组,转成16进制的字符串形式输出
        //System.out.println("content " + content + "key   " + key);
        String con = parseByte2HexStr(encrytptZip(content, key));
        Base64 base64 = new Base64();
        return new String(base64.encode(con.getBytes()), DATE_FORMAT);

    }

    public static String encode(String content, String key) throws UnsupportedEncodingException {
        // 加密之后的字节数组,转成16进制的字符串形式输出
        // System.out.println("content " + content + "key   " + key);
        String con = parseByte2HexStr(encrytpt(content, key));
        Base64 base64 = new Base64();
        return new String(base64.encode(con.getBytes()), DATE_FORMAT);

    }

    public static String decodeZip(String content, String key)
            throws UnsupportedEncodingException {
        // 解密之前,先将输入的字符串按照16进制转成二进制的字节数组,作为待解密的内容输入
        Base64 base = new Base64();
        String con = new String(base.decode(content), "UTF-8");
        //System.out.println("base64解密" + con);
        byte[] b = decrypt(parseHexStr2Byte(con), key);
        try {
            b = unZip(b);
        } catch (IOException e) {
            log.error("error",e);
        }
        return new String(b, DATE_FORMAT);
    }

    public static String decode(String content, String key) throws UnsupportedEncodingException {
        // 解密之前,先将输入的字符串按照16进制转成二进制的字节数组,作为待解密的内容输入
        Base64 base = new Base64();
        String con = new String(base.decode(content), "UTF-8");
        // System.out.println("base64解密" + con);
        byte[] b = decrypt(parseHexStr2Byte(con), key);
        return new String(b, DATE_FORMAT);
    }

    public static byte[] decrypt(byte[] content, String password) {
        try {
            byte[] keyStr = getKey(password);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher1 = Cipher.getInstance(ALGORITHM_STR);
            cipher1.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher1.doFinal(content);
            return result; //
        } catch (Exception e) {
            log.error("error",e);
        }
        return null;
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(
                    hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] encrytptZip(String bw, String password) {
        try {
            byte[] keyStr = getKey(password);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher1 = Cipher.getInstance(ALGORITHM_STR);
            byte[] byteContent = bw.getBytes(DATE_FORMAT);
            cipher1.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher1.doFinal(byteContent);
            return result; //
        } catch (Exception e) {
            log.error("error",e);
        }
        return null;
    }

    public static byte[] encrytpt(String bw, String password) {
        try {
            byte[] keyStr = getKey(password);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher1 = Cipher.getInstance(ALGORITHM_STR);
            byte[] byteContent = bw.getBytes(DATE_FORMAT);
            cipher1.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher1.doFinal(byteContent);
            return result; //
        } catch (Exception e) {
            log.error("error",e);
        }
        return null;
    }

    private static byte[] getKey(String password) {
        byte[] rByte = null;
        if (password != null) {
            rByte = password.getBytes();
        } else {
            rByte = new byte[24];
        }
        return rByte;
    }

    public static byte[] zip(byte[] bContent) throws IOException {
        if (bContent == null || bContent.length == 0) {
            return bContent;
        }
        byte[] b = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(bos);
        ZipEntry entry = new ZipEntry("zip");
        entry.setSize(bContent.length);
        zip.putNextEntry(entry);
        zip.write(bContent);
        zip.closeEntry();
        zip.close();
        b = bos.toByteArray();
        bos.close();
        return b;
    }

    /***************************************************************************
     * 解压Zip
     *
     * @return
     * @throws IOException
     */
    public static byte[] unZip(byte[] bContent) throws IOException {
        if (bContent == null || bContent.length == 0) {
            return bContent;
        }
        byte[] b = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bContent);
        ZipInputStream zip = new ZipInputStream(bis);
        if (zip.getNextEntry() != null) {
            byte[] buf = new byte[BUFFERSIZE];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = zip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.close();
        }
        zip.close();
        bis.close();
        return b;
    }

    /**
     * 将文件读成字符串,如果文件不存在或出现异常则返回空
     *
     * @param fileName
     *            文件(含有绝对路径)
     * @return 文件内容字符串
     */
    public static String readFile(String fileName) {
        StringBuffer content = new StringBuffer();
        String fileline;
        BufferedReader filedata = null;
        try {
            filedata = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName)));
            fileline = filedata.readLine();
            content.append(fileline); // 注意最后一行不要回车换行 edit by lhy 200309251219
            while ((fileline = filedata.readLine()) != null) {
                content.append("\r\n"); // 实现上一行回车换行 edit by lhy 200309251219
                content.append(fileline); // 注意最后一行不要回车换行 edit by lhy
                // 200309251219
            }
        } catch (FileNotFoundException ex1) {
            System.out.println("文件:" + fileName + "不存在!");
        } catch (IOException ex2) {
            System.out.println("读取文件:" + fileName + "时,出现输入输出异常!");
        } finally {
            if (filedata != null) {
                try {
                    filedata.close();
                } catch (IOException ex3) {
                    System.out.println("关闭文件:" + fileName + "时,出现输入输出异常!");
                }
            }
        }
        return content.toString();
    }
}
