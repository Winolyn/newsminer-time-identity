package time.math;

import java.text.ParseException;
import java.text.SimpleDateFormat;
/*
 * 处理所有时间数据
 */
import java.util.Date;

public class When {
	public String time;
	public boolean[] featrue;// 1、是否在标题；2、是否为报道时间；3、是否为确定时间
	public int flag; // 1.标题,2.发布时间,3.正文

	public When(Doc dm) {
		this.featrue = new boolean[3];
		// this.position_in_sens = new ArrayList<Integer>();
	}

	public When(String date, int i) {
		this.flag = i;
		this.time = date;
	}

	public String Date2Str(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(new Date());
		return dateStr;
	}

	public Date Str2Date(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(str);
		return date;
	}
}
