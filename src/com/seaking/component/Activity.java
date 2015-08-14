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

import java.util.Set;

import com.google.gson.Gson;
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
	     * 设置为true，则可以启动，否则不能启动。
	     * 如果设置为false，那么该Activity只能被同一个应用程序中的组件或带有相同用户ID的应用程序来启动。
	     * 如果没有任何过滤器，则意味着该Activity只能通过明确的类名来调用，
	     * 这样就暗示者该Activity只能在应用程序内部使用（因为其他用户不会知道它的类名），因此在这种情况下，默认值是false。
	     * 在另一方面，至少存在一个过滤器，则暗示着该Activity可被外部使用，因此默认值是true。
         * 这个属性不是限制Activity暴露给其他应用程序的唯一方法。还可以使用权限来限制外部实体对该Activity的调用。
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
	}
}
