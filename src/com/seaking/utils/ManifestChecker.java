package com.seaking.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.seaking.component.Provider;
import com.seaking.datamodel.VulnerabilityResult;
import com.seaking.mapper.ActionInterface;
import com.seaking.mapper.ActivityInterface;
import com.seaking.mapper.IntentFilterInterface;
import com.seaking.mapper.ManifestInterface;
import com.seaking.mapper.ReceiverInterface;
import com.seaking.mapper.ServiceInterface;

//主要检测Activity、Provider、Service、Receiver组件的暴露,AM错误配置、以及组件暴露引起的权限漏洞几大类
public class ManifestChecker {
	
	private ManifestInterface manifest;
	
	private String ACTIVITY_VUL="activity";
	private String SERVICE_VUL="service";
	private String PROVIDER_VUL="provider";
	private String RECIEVER_VUL="broadcast";
	private String PERMISSION_VUL="expose";
	private String AMBACKUP_VUL="backup";
	private String AMDEBUG_VUL="debug";
	
	
	private boolean isActivityExported=false;
	private boolean isServiceExported=false;
	private boolean isProviderExported=false;
	private boolean isReceiverExported=false;
	private List<VulnerabilityResult> vulnerlist;
	
	public ManifestChecker(ManifestInterface manifest){
		this.manifest=manifest;
		vulnerlist=new ArrayList<VulnerabilityResult>();
	}
	
	public void check(){
		vulnerlist.add(this.DebugChecker());
		vulnerlist.add(this.BackUpChecker());
		vulnerlist.add(this.ActivityChecker());
		vulnerlist.add(this.ServiceChecker());
		vulnerlist.add(this.ReceiverChecker());
		vulnerlist.add(this.ProviderChecker());
		
		VulnerabilityResult vp=new VulnerabilityResult();
		vp.setName(PERMISSION_VUL);
		List<String> info=new ArrayList<String>();
		
		if(isActivityExported||isServiceExported||
				isReceiverExported||isProviderExported){
			vp.setCount(1);
			info.add("存在权限暴露漏洞");
			vp.setInfolist(info);
		}
		vulnerlist.add(vp);
	}
	//
	private VulnerabilityResult DebugChecker(){
		VulnerabilityResult v = new VulnerabilityResult();
		v.setName(AMDEBUG_VUL);
		List<String> info = new ArrayList<String>();
		int count = 0;
		if(manifest.isAppDebuggable()){
			v.setCount(1);
			info.add("AM文件中deuggable属性设为true,存在一定风险");
			v.setInfolist(info);
		}
		return v;
	}
	private VulnerabilityResult BackUpChecker(){
		VulnerabilityResult v = new VulnerabilityResult();
		v.setName(AMBACKUP_VUL);
		List<String> info = new ArrayList<String>();
		int count = 0;
		if(manifest.isAppAllowBackup()){
			v.setCount(1);
			info.add("AM文件中allowBackup属性设为true,存在一定风险");
			v.setInfolist(info);
		}
		return v;
	}
	private VulnerabilityResult ActivityChecker(){
		VulnerabilityResult v=new VulnerabilityResult();
		v.setName(ACTIVITY_VUL);
		List<String> info=new ArrayList<String>();
		int count=0;
		
		Collection<ActivityInterface> activities=manifest.getActivities();
		if(activities != null){
            for (ActivityInterface act : activities) {
            	boolean flag1=false;
                if(act.isExported()){
                	for(IntentFilterInterface intent: act.getIntentFilters())
                		for (ActionInterface action : intent.getActions()) {
                            if(!action.getName().equals("android.intent.action.MAIN")){
                            	flag1=true;
                            }
                        }
                }
                if(flag1){
                	count++;
                	info.add(act.toString());
                	isActivityExported=true;
                }
            }
        }
		v.setCount(count);
		v.setInfolist(info);
		return v;
	}
	
	private VulnerabilityResult ServiceChecker(){
		VulnerabilityResult v=new VulnerabilityResult();
		v.setName(SERVICE_VUL);
		List<String> info=new ArrayList<String>();
		int count=0;
		
		Collection<ServiceInterface> services = manifest.getServices();
        if(services != null){
            for (ServiceInterface service : services) {
            	if(service.isExported()){
            		count++;
            		info.add(service.toString());
            		isServiceExported=true;
            	}
            	
            }
        }
		v.setCount(count);
		v.setInfolist(info);
		return v;
	}
	
	private VulnerabilityResult ReceiverChecker(){
		VulnerabilityResult v = new VulnerabilityResult();
		v.setName(RECIEVER_VUL);
		List<String> info = new ArrayList<String>();
		int count = 0;

		Collection<ReceiverInterface> receivers = manifest.getReceivers();
		if (receivers != null) {
			for (ReceiverInterface receiver : receivers) {
				if (receiver.isExported()) {
					count++;
					info.add(receiver.toString());
					isReceiverExported = true;
				}
			}
		}
		v.setCount(count);
		v.setInfolist(info);
		return v;
		
	}
	
	private VulnerabilityResult ProviderChecker(){
		VulnerabilityResult v = new VulnerabilityResult();
		v.setName(PROVIDER_VUL);
		List<String> info = new ArrayList<String>();
		int count = 0;

		Collection<Provider> providers = manifest.getProviders();
        if(providers != null){
            for (Provider p : providers) {
            	if(p.isExported()){
					count++;
					info.add(p.toString());
					isProviderExported = true;
				}
			}
		}
		v.setCount(count);
		v.setInfolist(info);
		return v;
	}
	
	
	public List<VulnerabilityResult> getVulnerList(){
		return vulnerlist;
	}
}
