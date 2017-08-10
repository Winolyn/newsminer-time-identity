package time.util;

/**
 * 字符串工具类
 * 
 */
public class StringUtil {

	/**
	 * 字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.trim().length() == 0));
	}

}
