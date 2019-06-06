package utils;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * AES加解密
 * <p>
 * Created by yyh on 2015/10/9.
 */
public class AESUtils {
    public static final String KEY = "akyn896ntgrbdz9w";
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_STR = "AES/CFB/NoPadding";

    /**
     * SecretKeySpec类是KeySpec接口的实现类,用于构建秘密密钥规范
     */
    private SecretKeySpec key;
    private final IvParameterSpec iv;

    public AESUtils(String hexKey) {
        key = new SecretKeySpec(hexKey.getBytes(), ALGORITHM);
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        iv = new IvParameterSpec(hexKey.getBytes());

    }

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String encryptData(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR); // 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);// 初始化
        return new BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public String decryptData(String base64Data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(base64Data)));
    }

    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public String decryptData(byte[] base64Data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return new String(cipher.doFinal(base64Data));
    }

    /**
     * hex字符串 转 byte数组
     *
     * @param s
     * @return
     */
    private static byte[] hex2byte(String s) {
        if (s.length() % 2 == 0) {
            return hex2byte(s.getBytes(), 0, s.length() >> 1);
        } else {
            return hex2byte("0" + s);
        }
    }

    private static byte[] hex2byte(byte[] b, int offset, int len) {
        byte[] d = new byte[len];
        for (int i = 0; i < len * 2; i++) {
            int shift = i % 2 == 1 ? 0 : 4;
            d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
        }
        return d;
    }

    public static void main(String[] args) throws Exception {
        AESUtils util = new AESUtils("akyn896ntgrbdz9w"); // 密钥
        String s = util.decryptData("VdVXI/cfnn7aNUb1");
        System.out.print(s);
        String s1 = util.encryptData(s);
        System.out.print(s1);
    }

    public static List<String> appAESDecode(String src) throws Exception {
        String headerStr = "www.msgpush.com";
        String contentStr = src.substring(headerStr.length());
        List<String> strings = parseContent(contentStr);
        return strings;
    }

    private static List<String> parseContent(String content) throws Exception {
        AESUtils util = new AESUtils("akyn896ntgrbdz9w"); // 密钥
        List<String> list = new ArrayList<>();
        int startIndex = 0;
        while (hasParse(content, startIndex)) {
            int dataLength = getDataLength(content, startIndex);
            if (dataLength == 0) {
                break;
            }
            startIndex += 12;
            String itemContent = content.substring(startIndex, startIndex + dataLength);
            String json = util.decryptData(itemContent);
            list.add(json);
            startIndex += dataLength;
        }
        return list;
    }

    private static boolean hasParse(String contentBytes, int startIndex) {
        if (startIndex + 12 < contentBytes.length()) {
            return true;
        }
        return false;
    }

    private static int getDataLength(String content, int startIndex) {
        if (content.length() > 12) {
            String lengthStr = content.substring(startIndex, startIndex + 12);
            int length = Utils.parseInt(lengthStr);
            return length;
        }
        return 0;
    }


    private static byte[] subBytes(byte[] src, int begin) {
        int count = src.length - begin;
        byte[] bs = new byte[count];
        for (int i = begin; i < src.length; i++)
            bs[i - begin] = src[i];
        return bs;
    }

    /**
     * 截取byte数组
     *
     * @param src
     * @param begin 开始位置
     * @param count 要截取的长度
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++)
            bs[i - begin] = src[i];
        return bs;
    }

    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
}