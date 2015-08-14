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

//主要检测Activity、Provider、Service、Receiver组件的暴露问题
//和由之引起的权限暴露问题
public class ManifestChecker {
	
	private ManifestInterface manifest;
	
	private String ACTIVITY_VUL="activityexpose";
	private String SERVICE_VUL="serviceexpose";
	private String PROVIDER_VUL="providerexpose";
	private String RECIEVER_VUL="recieverexpose";
	private String PERMISSION_VUL="permissionexpose";
	
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
		vulnerlist.add(this.ActivityChecker());
		vulnerlist.add(this.ServiceChecker());
		vulnerlist.add(this.ReceiverChecker());
		vulnerlist.add(this.ProviderChecker());
		
		VulnerabilityResult vp=new VulnerabilityResult();
		//写入漏洞类型
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
	
	//检查Activity组件
	private VulnerabilityResult ActivityChecker(){
		VulnerabilityResult v=new VulnerabilityResult();
		//写入漏洞类型
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
		//写入count
		v.setCount(count);
		//写入info
		v.setInfolist(info);
		return v;
	}
	
	
	//检查Service组件
	private VulnerabilityResult ServiceChecker(){
		VulnerabilityResult v=new VulnerabilityResult();
		//写入漏洞类型
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
        
      //写入count
		v.setCount(count);
		//写入info
		v.setInfolist(info);
		return v;
	}
	
	//检查Recevier组件
	private VulnerabilityResult ReceiverChecker(){
		VulnerabilityResult v = new VulnerabilityResult();
		// 写入漏洞类型
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

		// 写入count
		v.setCount(count);
		// 写入info
		v.setInfolist(info);
		return v;
		
	}
	
	//检查Provider组件
	private VulnerabilityResult ProviderChecker(){
		VulnerabilityResult v = new VulnerabilityResult();
		// 写入漏洞类型
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

		// 写入count
		v.setCount(count);
		// 写入info
		v.setInfolist(info);
		return v;
	}
	
	public List<VulnerabilityResult> getVulnerList(){
		return vulnerlist;
	}
}
