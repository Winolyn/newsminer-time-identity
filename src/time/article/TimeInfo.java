package time.article;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.util.ArrayList;

import time.math.Doc;
import time.math.When;
/**
 * 本工具类主要是测试使用的入口类
 * 将读取新闻文本标题、发布时间、正文封装到Doc类中进行初始化
 * 
 * @author winolyn
 */

public class TimeInfo {

	static String title = "";
	static String content = "";
	static String publishtime = "";

	public static String finaltime = null;
	public static long starttime;
	public static long endtime;
	public static int count=0;//记录测试文本数

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ArrayList<File> fileList = getFiles("E:\\实验室\\news");
		ArrayList<String> list = convert(fileList);
		BufferedWriter bufw = new BufferedWriter(new FileWriter(new File("E:\\实验室\\结果集.txt"),true));
		starttime = System.currentTimeMillis();
		for (String fn : list) {
			bufw.write(fn.toString());bufw.newLine();
			BufferedReader br = new BufferedReader(new FileReader(fn));
			int i = 0;
			String temp = "";content="";
			while ((temp = br.readLine()) != null) {
				if (i == 0) {
					title = temp;
					i++;
				} else if (i++ == 1)
					publishtime = temp;
				else
					content = content + temp;
			}
			br.close();
			bufw.write("-----------------------------title--------------------------------");bufw.newLine();
			bufw.write(title);bufw.newLine();
			bufw.write("--------------------------publish time------------------------------");bufw.newLine();
			bufw.write(publishtime);bufw.newLine();
			bufw.write("-----------------------------content-------------------------------");bufw.newLine();
			bufw.write(content);bufw.newLine();
			Doc doc = new Doc(title, publishtime, content);
			//bufw.write("------------------------ time collection----------------------------");bufw.newLine();

			ArrayList<When> dateCandidate = new ArrayList<When>();

			ArrayList<When> a1 = doc.getTitleDate();// 标题时间候选集
			dateCandidate.addAll(a1);

			When pd = new When(doc.getPublishdate(), 2);
			dateCandidate.add(pd);// 将发布时间加入候选集

			ArrayList<When> a2 = doc.getContentDate();// 正文时间候选集
			dateCandidate.addAll(a2);
		/*	bufw.write("**********************************************");bufw.newLine();
			for (When t : dateCandidate) {
				if (t.flag == 2)
					bufw.write("发布时间：" + t.time + "\t\n");
				else if (t.flag == 1)
					bufw.write("标题时间：" + t.time + "\t\n");
				else if (t.flag == 3)
					bufw.write("正文时间：" + t.time + "\t\n");
			}*/
			bufw.write("---------------------------- final  time------------------------------");
			finaltime = getfinaltime(dateCandidate, pd);bufw.newLine();
			bufw.write(finaltime);bufw.newLine();bufw.newLine();
			bufw.flush();
			count++;
		}
		
		endtime=System.currentTimeMillis();
		bufw.write("共测试"+count+"篇文章");
		bufw.write("花费时间"+(endtime-starttime)+"ms");
		bufw.close();
	}

	private static ArrayList<String> convert(ArrayList<File> fileList) {
		ArrayList<String> list = new ArrayList<String>();
		for (File file : fileList)
			list.add(file.toString().replaceAll("\\\\", "\\\\\\\\"));
		return list;
	}
/**
 * 获取文件，作为测试时遍历文件使用
 */
	private static ArrayList<File> getFiles(String path) throws Exception {// 目标集合fileList
		ArrayList<File> fileList = new ArrayList<File>();
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileIndex : files) {
				if (fileIndex.isDirectory())
					fileList.addAll(getFiles(fileIndex.getPath().replaceAll("\\\\", "\\\\\\\\")));
				else
					fileList.add(fileIndex);
			}
		}
		return fileList;
	}
/***
 * 最终输出时间确定。
 * <p>规则：把时间候选集中的时间和发布时间进行对比，选取距离新闻发布时间最近的时间作为finaltime
 * 如果候选集为空或最近时间小于15天（半个月），取发布时间。
 * @param dateCandidate
 * @param pd
 * @return finaltime
 */
	private static String getfinaltime(ArrayList<When> dateCandidate, When pd) {
		long min = Long.MAX_VALUE;
		String temp = pd.time;
		for (When t : dateCandidate) {
			if (t.flag == 1 || t.flag == 3) {
				try {
					long interval = pd.Str2Date(pd.time).getTime() - t.Str2Date(t.time).getTime();
					if (interval > 0) {
						// System.out.println(t.time + "#" + interval);
						if (interval < min) {
							// System.out.println("!" + interval);
							min = interval;
							temp = t.time;

						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}

		}
		if (min > 1296000000L)
			return pd.time;
		return temp;
	}

}
