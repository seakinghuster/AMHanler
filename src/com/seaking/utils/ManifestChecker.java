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

//��Ҫ���Activity��Provider��Service��Receiver����ı�¶����
//����֮�����Ȩ�ޱ�¶����
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
		//д��©������
		vp.setName(PERMISSION_VUL);
		List<String> info=new ArrayList<String>();
		
		if(isActivityExported||isServiceExported||
				isReceiverExported||isProviderExported){
			vp.setCount(1);
			info.add("����Ȩ�ޱ�¶©��");
			vp.setInfolist(info);
		}
		vulnerlist.add(vp);
	}
	
	//���Activity���
	private VulnerabilityResult ActivityChecker(){
		VulnerabilityResult v=new VulnerabilityResult();
		//д��©������
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
		//д��count
		v.setCount(count);
		//д��info
		v.setInfolist(info);
		return v;
	}
	
	
	//���Service���
	private VulnerabilityResult ServiceChecker(){
		VulnerabilityResult v=new VulnerabilityResult();
		//д��©������
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
        
      //д��count
		v.setCount(count);
		//д��info
		v.setInfolist(info);
		return v;
	}
	
	//���Recevier���
	private VulnerabilityResult ReceiverChecker(){
		VulnerabilityResult v = new VulnerabilityResult();
		// д��©������
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

		// д��count
		v.setCount(count);
		// д��info
		v.setInfolist(info);
		return v;
		
	}
	
	//���Provider���
	private VulnerabilityResult ProviderChecker(){
		VulnerabilityResult v = new VulnerabilityResult();
		// д��©������
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

		// д��count
		v.setCount(count);
		// д��info
		v.setInfolist(info);
		return v;
	}
	
	public List<VulnerabilityResult> getVulnerList(){
		return vulnerlist;
	}
}
