
package com.tencent.qcloud.ui.utils;

import android.content.Context;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * 表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {

	/**
	 * 表情类型标志符
	 */
	public static final int EMOTION_CLASSIC_TYPE=0x0001;//经典表情

	/**
	 * 从assets目录下获取所有表情
	 *
	 * @return
	 */
	public static String[] getAssetsEmoji(Context context) {
		BufferedReader br = null;
		String emojis[] = null;
		try {
			InputStream is = context.getAssets().open("emoji.json");
			StringBuffer sb = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while (null != (line = br.readLine())) {
				sb.append(line).append("\r\n");
			}
			JSONArray emojiArray = new JSONArray(sb.toString());
			if (null != emojiArray && emojiArray.length() > 0) {
				emojis = new String[emojiArray.length()];
				for (int i = 0; i < emojiArray.length(); i++) {
					emojis[i] = emojiArray.optString(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return emojis;
	}

	/**
	 * 将字符串转成unicode
	 * @param str 待转字符串
	 * @return unicode字符串
	 */
	public static String convert(String str)
	{
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++)
		{
			c = str.charAt(i);
			sb.append("\\u");
			j = (c >>>8); //取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); //取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}
}
