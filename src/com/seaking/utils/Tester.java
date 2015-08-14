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
	CertificateReader cert=new CertificateReader(archive);
	System.out.println(cert.getCertificate());
	System.out.println(cert.getMD5());
	System.out.println(cert.getSHA1());
	try {
		ExtractAM.extractApk(archive,dest);
		ExtractAM.parseXML(dest);
	} catch (IOException e) {
		e.printStackTrace();
	}
	//Manifest mai=new Manifest(new File("D:\\Downloads\\gift\\AndroidManifest.xml"));
	Manifest mai=(Manifest) new DOMManifestParser().parse(new File("D:\\Downloads\\gift\\AndroidManifest.xml"));
	
	for(PermissionRequestInterface per:mai.getRequestedPermissions()){
		System.out.println(((PermissionRequest)per).getRequestedPermission().getName());
	}
	ManifestChecker mainchecker=new ManifestChecker(mai);
    mainchecker.check();
    for(VulnerabilityResult res:mainchecker.getVulnerList()){
        System.out.println(res.toString());
    }
    System.out.println("运行完了");
    }

}
