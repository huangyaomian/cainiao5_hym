package com.hym.netdemo.support;

import android.view.inspector.StaticInspectionCompanionProvider;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.EncryptUtils;
import com.hym.netdemo.config.HymConfigKt;

import kotlin.js.ExperimentalJsExport;

/**
 * author: huangyaomian
 * created on: 2021/6/12 10:17 上午
 * description:解码和转码工具类
 */
public class HymUtils {
    /**
     * 将构造方法私有化，避免并实例化对象
     */
    private HymUtils() {

    }

    /**
     * 中文转unicode
     */
    public static String unicodeEncode(String str){
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 判断是否为中文字符
     *
     * @param c 字符
     * @return 是否为中文字符
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * unicode 转中文
     */
    public static String unicodeDecode(String str){
        StringBuilder string = new StringBuilder();

        String[] hex = str.split("\\\\u");

        for (String s : hex) {

            try {
                // 汉字范围 \u4e00-\u9fa5 (中文)
                if (s.length() >= 4) {//取前四个，判断是否是汉字
                    String chinese = s.substring(0, 4);
                    try {
                        int chr = Integer.parseInt(chinese, 16);
                        boolean isChinese = isChinese((char) chr);
                        //转化成功，判断是否在  汉字范围内
                        if (isChinese) {//在汉字范围内
                            // 追加成string
                            string.append((char) chr);
                            //并且追加  后面的字符
                            String behindString = s.substring(4);
                            string.append(behindString);
                        } else {
                            string.append(s);
                        }
                    } catch (NumberFormatException e1) {
                        string.append(s);
                    }

                } else {
                    string.append(s);
                }
            } catch (NumberFormatException e) {
                string.append(s);
            }
        }

        return string.toString();
    }


    /**
     * 解析返回的data数据
     * @param dataStr 需要解析的数据
     * @return 返回解析完的数据
     */
    @Nullable
    public static String decodeData(@Nullable String dataStr){
        //java 代码，无自动null判断，需要自行处理
        if (dataStr != null) {
            return new String(EncryptUtils.decryptBase64AES(
                    dataStr.getBytes(), HymConfigKt.NET_CONFIG_APPID.getBytes(),
                    "AES/CBC/PKCS7Padding",
                    "J#y9sJesy*5HmqLq".getBytes()
            ));
        }else {
            return null;
        }
    }

}
