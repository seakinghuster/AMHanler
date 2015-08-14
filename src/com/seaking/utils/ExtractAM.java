package com.seaking.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.xmlpull.v1.XmlPullParserException;

public class ExtractAM {
	/**
	 * 解压apk文件
	 * @param archive apk文件所在路径
	 * @param dest    解压后文件存储路径
	 * @throws IOException
	 */
	public static void extractApk(File archive, File dest) throws IOException {
		
		ZipFile zipFile = new ZipFile(archive);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();

			String entryFileName = entry.getName();

			byte[] buffer = new byte[16384];
			int len;

			File dir = buildDirectoryHierarchyFor(entryFileName, dest);// destDir
			if (!dir.exists()) {
				dir.mkdirs();
			}

			if (!entry.isDirectory()) {
				if (entry.getSize() == 0) {
					continue;
				}
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(dest, entryFileName)));

					bis = new BufferedInputStream(zipFile.getInputStream(entry));

					while ((len = bis.read(buffer)) > 0) {
						bos.write(buffer, 0, len);
					}
					bos.flush();
				} catch (IOException ioe) {
					System.out.println("不能够解压该文件，可能该文件不是完整的");
				} finally {
					if (bos != null) bos.close();
					if (bis != null) bis.close();
				}
			}
		}
	}
	/**
	 * 
	 * @param entryFileName 文件夹名
	 * @param dest 文件路径
	 * @return
	 */
	private static File buildDirectoryHierarchyFor(String entryName,
			File dest) {
		int lastIndex = entryName.lastIndexOf('/');

		String internalPathToEntry = entryName.substring(0, lastIndex + 1);
		return new File(dest, internalPathToEntry);
	}
	/**
	 * 
	 * @param f 解压后的apk文件所在的文件夹
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static void parseXML(File dir) throws IOException,XmlPullParserException{
		if(!dir.isDirectory()) return;
		File[] files=dir.listFiles();
		for(File f:files){
			if(f.getName().equals("AndroidManifest.xml")){
				String path=f.getPath();
				File destAm=new File(path);
				String content=MyAXMLPrinter.parse(f);
				FileWriter fw = new FileWriter(destAm);
				fw.write(content);
				fw.flush();
				fw.close();
			}
		}
	}
	
	/**
	 * 测试apk解压后的结果
	 * @param args
	 * @throws XmlPullParserException 
	 */
	public static void main(String[] args) throws XmlPullParserException{
		File archive=new File("D:\\Downloads\\com.gift.android_040615.apk");
		File dest=new File("D:\\Downloads\\gift");
		try {
			extractApk(archive,dest);
			parseXML(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
