package time.math;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import time.nlp.TimeNormalizer;
import time.nlp.TimeUnit;
import time.util.DateUtil;

/**
 * 处理时间数据的封装外部类。
 *  <p>
 * 初始化输入数据，获取标题、发布时间、正文并进行全角转半角的规范化处理。
 */
public class Doc {
	private String title;
	private String publishdate;
	private String content;

	public Doc(String title, String publishdate, String content) throws ParseException {
		this.setTitle(title);
		this.setPublishdate(publishdate);
		this.setContent(content);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = fullWidth2halfWidth(title);
	}

	public String getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(String publishdate){
		this.publishdate = fullWidth2halfWidth(publishdate);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = fullWidth2halfWidth(content);
	}

	public ArrayList<When> getTitleDate() throws IOException {
		return DigTime(this.title, this.publishdate, 1);
	}

	public ArrayList<When> getContentDate() throws IOException {
		return DigTime(this.content, this.publishdate, 3);
	}

	private ArrayList<When> DigTime(String content, String publishdate, int num) throws IOException {

		ArrayList<When> alist = new ArrayList<When>();
		BufferedWriter bufw = new BufferedWriter(new FileWriter(new File("E:\\实验室\\结果集.txt"),true));
		if(num==3)
			bufw.write("***********时间文本对应***********");bufw.newLine();
		
		String timebase = publishdate.replaceAll(":", "-").replaceAll(" ", "-");
		String userDirPath = System.getProperty("user.dir");
		String wordFile = userDirPath + "\\resource";
		TimeNormalizer normalizer = new TimeNormalizer(wordFile + "/TimeExp.m");
		normalizer.setPreferFuture(true);
		TimeUnit[] unit = normalizer.getTimeUnit();
		normalizer.parse(content, timebase);
		unit = normalizer.getTimeUnit();
		for (int i = 0; i < unit.length; i++) {
			String str = DateUtil.formatDateDefault(unit[i].getTime());
			When time = new When(str, num);
			alist.add(time);
			bufw.write("时间文本:" + unit[i].Time_Expression + ",对应时间:" + str);bufw.newLine();
			bufw.flush();
		}
		bufw.close();
		return alist;
	}
	/**
     * 全角字符串转换半角字符串
     *
     * @param fullWidthStr
     *            非空的全角字符串
     * @return 半角字符串
     */
	 private static String fullWidth2halfWidth(String fullWidthStr) {		 
        if (null == fullWidthStr || fullWidthStr.length() <= 0) {
            return "";
        }
        char[] charArray = fullWidthStr.toCharArray();
        //对全角字符转换的char数组遍历
        for (int i = 0; i < charArray.length; ++i) {
            int charIntValue = (int) charArray[i];
            //如果符合转换关系,将对应下标之间减掉偏移量65248;如果是空格的话,直接做转换
            if (charIntValue >= 65281 && charIntValue <= 65374) {
                charArray[i] = (char) (charIntValue - 65248);
            } else if (charIntValue == 12288) {
                charArray[i] = (char) 32;
            }
        }
        return new String(charArray);
    }

}
