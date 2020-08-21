package com.dajukeji.hslz.util;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final int DEFAULT_MAX_SIZE = 10 * 1024 * 2014;

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isMobileNum(String mobiles) {
//        Pattern p = Pattern
//                .compile("(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}");
//        Matcher m = p.matcher(mobiles);
//        System.out.println(m.matches() + "---");
//        return m.matches();
        return true;
    }

    public static String md5(InputStream in, int max_size) {
        try {
            MessageDigest md5er = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int read;
            int totalRead = 0;
            do {
                read = in.read(buffer);
                totalRead += read;
                if (totalRead > max_size) {
                    break;
                }
                if (read > 0) {
                    md5er.update(buffer, 0, read);
                }
            } while (read != -1);
//			in.close();
            byte[] digest = md5er.digest();
            if (digest == null)
                return null;
            String strDigest = "0x";
            for (int i = 0; i < digest.length; i++) {
                strDigest += Integer.toString((digest[i] & 0xff) + 0x100, 16)
                        .substring(1).toUpperCase();
            }
            return strDigest;
        } catch (Exception e) {
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String md5(InputStream in) {
        return md5(in, DEFAULT_MAX_SIZE);
    }

    public static String md5(File file) {
        String content = String.valueOf(System.currentTimeMillis());
        try {
            content = file.getAbsolutePath() + file.lastModified() + file.length();
        } catch (Exception ex) {
        }

        return md5(content);
    }

    public static String inputStream2String(InputStream inputStream, String encoding)
            throws IOException {
        return new String(inputStream2bytes(inputStream), encoding);
    }

    public static String inputStream2String(InputStream inputStream)
            throws IOException {
        return new String(inputStream2bytes(inputStream), Charset.defaultCharset());
    }

    private static byte[] inputStream2bytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toByteArray();
    }

    public static String null2String(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }


    public static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255) {
                length++;
            } else {
                length += 2;
            }
        }
        return length / 2;
    }

    public static String encodeEmojis(String s) {
        if (TextUtils.isEmpty(s)) {
            s = "";
        }
        String reg = "(\\ud83c[\\udf00-\\udfff])|(\\ud83d[\\udc00-\\ude4f])|(\\ud83d[\\ude80-\\udeff])";
        if (!s.matches(reg)) {
            return s;
        }
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, URLEncoder.encode(m.group(1)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String decodeEmojis(String s) {
        if (TextUtils.isEmpty(s)) {
            s = "";
        }
        String reg = "(\\ud83c[\\udf00-\\udfff])|(\\ud83d[\\udc00-\\ude4f])|(\\ud83d[\\ude80-\\udeff])";
        if (!s.matches(reg)) {
            return s;
        }
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, URLDecoder.decode(m.group(1)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

//    public static String getTime(int time) {
//        if (time >= 18) {
//            return MNJBaseApplication.getInstance().getString(R.string.night);
//        }
//        if (time >= 12 && time < 18) {
//            return MNJBaseApplication.getInstance().getString(R.string.pm);
//        }
//        return MNJBaseApplication.getInstance().getString(R.string.am);
//    }

//    public static String parseTime(long time) {
//        StringBuffer sb = new StringBuffer();
//        Date date = new Date(time);
//        String dateString = DateUtil.format(date);
//        try {
//            String today = DateUtil.getNow(DateUtil.FORMAT_SHORT);
//            String yesterday = DateUtil.format(DateUtil.addDay(new Date(), -1), DateUtil.FORMAT_SHORT);
//            if (dateString.startsWith(today)) {
//                sb.append("今天 ");
//            } else if (dateString.startsWith(yesterday)) {
//                sb.append("昨天 ");
//            } else {
//                sb.append(dateString.replaceAll(" .*", "") + " ");
//            }
//            String time_short = dateString.replaceAll(".* |:\\d{2}$", "");
//            sb.append(getTime(Integer.parseInt(time_short.split(":")[0])));
//            sb.append(time_short);
//            return sb.toString();
//        } catch (Exception e) {
//            return dateString.replaceAll(":\\d{2}$", "");
//        }
//    }

    public static String parseStarName(String s) {
        if (s.length() == 1) {
            s = "*";
        } else if (s.length() == 2) {
            s = s.replaceFirst("\\w", "*");
        } else {
            s = s.replaceAll("(?<=\\w)(.{1,10})(?=\\w)", "*");
        }
        return s;
    }

    /**
     * 电话号码加横杠:127-1111-1111
     *
     * @param s
     * @return
     */
    public static String parsePhone(String s) {
        return s.replaceFirst("(?<=\\d{3})", "-").replaceFirst("(?<=\\d{4})", "-");
    }

    public static String parsePhoneNum(String s) {
        return s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String parseStarBankCard(String s) {
        return s.replaceAll("(?<=\\d{6}).(?=\\d{4})", "*");
    }

    /**
     * 距离转换
     *
     * @param distance
     * @return
     */
    public static String getDistance(int distance) {
        if (distance < 100) {
            return "附近";
        }
        if (distance < 1000) {
            return (int) distance + "米";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        if (distance < 10000) {
            return decimalFormat.format(distance * 1.0 / 1000) + "千米";
        }
        return (int) (distance / 1000) + "千米";
    }

    public static boolean isEmpty(String s) {
        if (TextUtils.isEmpty(s)) {
            return true;
        }
        if ("null".equalsIgnoreCase(s)) {
            return true;
        }
        return false;
    }

    public static String getStringNoEmpty(String s) {
        if (isEmpty(s)) {
            return "";
        } else {
            return s;
        }
    }
    /**
     * 保留两位小数
     * */
    public static String decimalString(Object s){
       return String.format("%.2f",s);
    }
}
