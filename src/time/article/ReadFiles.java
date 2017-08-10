package time.article;

import java.io.File;
import java.util.ArrayList;

public class ReadFiles {

	public static void main(String[] args) throws Exception {
		ReadFiles rf = new ReadFiles();
		ArrayList<File> fileList = rf.getFiles("E:\\实验室\\news");
		for(File f:fileList)
			System.out.println(f.toString().replaceAll("\\\\", "\\\\\\\\"));
	}

	private ArrayList<File> getFiles(String path) throws Exception {// 目标集合fileList
		ArrayList<File> fileList = new ArrayList<File>();
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileIndex : files) {
				if (fileIndex.isDirectory())//{
					fileList.addAll(new ReadFiles().getFiles(fileIndex.getPath().replaceAll("\\\\", "\\\\\\\\")));
					//System.out.println("xx"+fileIndex.getPath().replaceAll("\\\\", "\\\\\\\\"));}
				else
					fileList.add(fileIndex);
			}
		}
		return fileList;
	}
}
