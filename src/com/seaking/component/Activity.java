/* SAAF: A static analyzer for APK files.
 * Copyright (C) 2013  syssec.rub.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.seaking.component;

import com.seaking.mapper.ActionInterface;
import com.seaking.mapper.ActivityInterface;
import com.seaking.mapper.IntentFilterInterface;



/**
 * This class represents an Activity from AnroidManifest.xml
 * 
 * @author Tilman Bender <tilman.bender@rub.de>
 *
 */
public class Activity extends IntentReceivingComponent implements ActivityInterface{

    private String usePermission;
    private String exported;
    
	public Activity(String name) {
		super();
		this.name = name;
		this.usePermission = null;
		this.exported = null;
	}
	
	public void setUsePermission(String permission){
	    this.usePermission = permission;
	}
	
	public void setExported(String exported){
	    this.exported = exported;
	}
	
	public boolean isExported(){
	    /**
	     * ����Ϊtrue�������������������������
	     * �������Ϊfalse����ô��Activityֻ�ܱ�ͬһ��Ӧ�ó����е�����������ͬ�û�ID��Ӧ�ó�����������
	     * ���û���κι�����������ζ�Ÿ�Activityֻ��ͨ����ȷ�����������ã�
	     * �����Ͱ�ʾ�߸�Activityֻ����Ӧ�ó����ڲ�ʹ�ã���Ϊ�����û�����֪���������������������������£�Ĭ��ֵ��false��
	     * ����һ���棬���ٴ���һ������������ʾ�Ÿ�Activity�ɱ��ⲿʹ�ã����Ĭ��ֵ��true��
         * ������Բ�������Activity��¶������Ӧ�ó����Ψһ������������ʹ��Ȩ���������ⲿʵ��Ը�Activity�ĵ��á�
	     */
	    if(exported == null){
	        if(getIntentFilters().isEmpty()) return false;
	        else{
	            if(usePermission != null) return false;
	            else return true;
	        }
	    }else if(exported.toLowerCase().equals("true")){
	        if(usePermission != null) return false;
	        else return true;
	    }else return false;
	}
	
	public String toString(){
		String head = "<activity  android:name=\"" + getName()+"\"";	   
	    if(exported!=null){
	    	head+=" android:exported=\""+exported+"\"";
	    }
	    StringBuilder sb = new StringBuilder();	   
	    for (IntentFilterInterface intent : getIntentFilters()) {
	    	 sb.append("  <intent-filter>");
           for (ActionInterface action : intent.getActions()) {
        	   sb.append("<action android:name=\"");
            sb.append(action.getName()+"\"/>");
          } 
           sb.append("</intent-filter>");
        }
	    head=head+sb.toString()+ "</Activity>";
	    return head ;
		
// ['normal', 'control vibrator', 'Allows the application to control the vibrator.']
//	android.permission.RECEIVE_BOOT_COMPLETED ['normal', 'automatically start at boot', 'Allows an application to start itself as soon as the system has finished booting. This can make it take longer to start the phone and allow the application to slow down the overall phone by always running.']
//	'rmal', 'view Wi-Fi status', 'Allows an application to view the information about the status of Wi-Fi.']
//	['normal', 'prevent phone from sleeping', 'Allows an application to prevent the phone from going to sleep.']normal', 'view network status', 'Allows an application to view the status of all networks.']
// ['dangerous', 'full Internet access', 'Allows an application to create network sockets.']
//	android.permission.WRITE_EXTERNAL_STORAGE ['dangerous', 'modify/delete SD card contents', 'Allows an application to write to the SD card.']
//	android.permission.FLASHLIGHT 
//		'FACTORY_TEST', 'CHANGE_WIFI_STATE', 
//		'RECORD_AUDIO', 'BROADCAST_STICKY', 
//		'USE_CREDENTIALS', 
//	
//		'ACCESS_COARSE_LOCATION', 
//		'SET_WALLPAPER', 'GET_ACCOUNTS', 'READ_CONTACTS', 
//		'READ_PHONE_STATE', , 'ACCESS_FINE_LOCATION']
	}
}
