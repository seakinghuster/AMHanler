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

import com.seaking.mapper.ReceiverInterface;



/**
 * This class represents a Receiver from AndroidManifest.xml
 * 
 * @author Tilman Bender <tilman.bender@rub.de>
 *
 */
public class Receiver extends IntentReceivingComponent implements ReceiverInterface {

    private String usePermission;
    private String exported;
    
	public Receiver(String value) {
		super();
		this.name=value;
        this.usePermission = null;
        this.exported = null;
	}

	public Receiver() {
	    super();
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
    	String head = "<receiver  android:name=\" " + getName() ;
    	if(exported!=null){
	    	head+="\" android:exported=\""+exported+"\"";
	    }
    	StringBuilder sb = new StringBuilder();  	   
  	    head=head+sb.toString()+ "</receiver>";
  	    return head ;
    }
}
