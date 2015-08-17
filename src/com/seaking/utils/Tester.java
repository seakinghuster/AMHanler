package com.seaking.utils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.seaking.metadata.ManifestParserException;




public class Tester {
public static void main(String[] args) {
	String apkFilepath="D:\\Downloads\\4886c75d13f948200a2c43ad829d9b4d.apk";
	String destFilePath="D:\\Downloads\\weibo";
	try {
		AMWrapper.AMTest(apkFilepath, destFilePath);
	} catch (IOException e) {
		e.printStackTrace();
	} catch (XmlPullParserException e) {
		e.printStackTrace();
	} catch (ManifestParserException e) {
		e.printStackTrace();
	}
}
}