package util;

import java.util.Random;

public class UtilsAll {
	/**
	 *  Java生成随机字符串的代码
	 * @param length 表示要生成字符串的长度  
	 * @return
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; i++) {
	        int number = random.nextInt(base.length());
	        sb.append(base.charAt(number));
	    }
	    return sb.toString();
	 }
}
