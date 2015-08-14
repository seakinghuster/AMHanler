package com.seaking.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;

import org.xmlpull.v1.XmlPullParserException;

import com.seaking.component.Manifest;
import com.seaking.component.PermissionRequest;
import com.seaking.datamodel.VulnerabilityResult;
import com.seaking.mapper.PermissionRequestInterface;
import com.seaking.metadata.CertificateReader;
import com.seaking.metadata.DOMManifestParser;
import com.seaking.metadata.ManifestParserException;



public class Tester {
public static void main(String[] args) throws XmlPullParserException, ManifestParserException, CertificateEncodingException, NoSuchAlgorithmException, FileNotFoundException, IOException {
	File archive=new File("D:\\Downloads\\com.gift.android_040615.apk");
	File dest=new File("D:\\Downloads\\gift");
	//获取签名信息
	System.out.println("证书信息：");
	CertificateReader cert=new CertificateReader(archive);
	System.out.println("cert:"+cert.getCertificate());
	System.out.println("MD5:"+cert.getMD5());
	System.out.println("SHA1:"+cert.getSHA1());
	try {
		ExtractAM.extractApk(archive,dest);
		ExtractAM.parseXML(dest);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	Manifest mai=(Manifest) new DOMManifestParser().parse(new File("D:\\Downloads\\gift\\AndroidManifest.xml"));
	System.out.println("申请权限信息：");
	for(PermissionRequestInterface per:mai.getRequestedPermissions()){
		System.out.println(((PermissionRequest)per).getRequestedPermission().getName());
	}
	System.out.println("AM漏洞检测信息：");
	ManifestChecker mainchecker=new ManifestChecker(mai);
    mainchecker.check();
    for(VulnerabilityResult res:mainchecker.getVulnerList()){
        System.out.println(res.toString());
    }
    }

}
