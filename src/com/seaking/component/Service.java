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

import com.google.gson.Gson;
import com.seaking.mapper.ActionInterface;
import com.seaking.mapper.IntentFilterInterface;
import com.seaking.mapper.ServiceInterface;



/**
 * This class represents a Service from AndroidManifest.xml
 * 
 * @author Tilman Bender <tilman.bender@rub.de>
 *
 */
public class Service extends IntentReceivingComponent implements ServiceInterface {

    private String usePermission;
    private String exported;
    
	public Service(String value) {
		super();
		this.name=value;
		this.usePermission = null;
		this.exported = null;
	}

	public Service(){
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
    	Service ser=new Service(getName());
		for(IntentFilterInterface intent : getIntentFilters()) {
			for(ActionInterface action: intent.getActions()){
				intent.addAction(action);
			}
			ser.addIntentFilter(intent);
		}
		Gson gson=new Gson();
		String serStr=gson.toJson(ser);
		return serStr;
    }
}

