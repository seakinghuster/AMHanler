package com.seaking.utils;

import java.io.File;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.seaking.component.Manifest;
import com.seaking.component.PermissionRequest;
import com.seaking.datamodel.VulnerabilityResult;
import com.seaking.mapper.PermissionRequestInterface;
import com.seaking.metadata.DOMManifestParser;
import com.seaking.metadata.ManifestParserException;

public class AMWrapper{
	public static void AMTest(String path1,String path2) throws IOException, XmlPullParserException, ManifestParserException{
		ExtractAM.extractApk(new File(path1),new File(path2));
		ExtractAM.parseXML(new File(path2));
		Manifest mai=(Manifest) new DOMManifestParser().parse(new File(path2+"\\AndroidManifest.xml"));
		System.out.println("申请权限信息：");
		for(PermissionRequestInterface per:mai.getRequestedPermissions()){
			System.out.println(((PermissionRequest)per).getRequestedPermission().getName());
		}
		System.out.println("AM漏洞检测信息：");
		ManifestChecker mainchecker=new ManifestChecker(mai);
	    mainchecker.check();
	    for(VulnerabilityResult res:mainchecker.getVulnerList()){
	    	if (res!=null){
	    	    String vlu=res.toString().replace("\\", "");
	            System.out.println(vlu.replace(",\"isEntryPoint\":false", ""));
	        }
	    }
	}

}
