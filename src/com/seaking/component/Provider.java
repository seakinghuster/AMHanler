package com.seaking.component;

import com.google.gson.Gson;



public class Provider extends Component{
    
    private String writePermission;
    private String readPermission;
    private String permission;
    private String exported;
    
    public Provider(){
        super();
        this.permission = null;
        this.readPermission = null;
        this.writePermission = null;
        this.exported = null;
    }
    
    public void setPermission(String permission){
        this.permission = permission;
    }
    
    public void setReadPermission(String readPermission){
        this.readPermission = readPermission;
    }
    
    public void setWriterPermission(String writePermission){
        this.writePermission = writePermission;
    }
    
    public void setExported(String exported){
        this.exported = exported;
    }
    
    public boolean isExported(){
        if(exported == null){
            if( permission != null || (readPermission != null && writePermission != null)) return false;
            else return true;
        }else if(exported.toLowerCase().equals("true")){
            if( permission != null || (readPermission != null && writePermission != null)) return false;
            else return true;
        }else return false;
    }
    
    public String toString(){
    	Provider pro=new Provider();
    	pro.setName(name);
    	Gson gson=new Gson();
		String proStr=gson.toJson(pro);
		return proStr;
    }
}
