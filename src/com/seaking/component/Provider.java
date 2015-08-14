package com.seaking.component;



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
		String head = "<provider  android:name=\"" + getName() + "\"";
		if (exported != null) {
			head += " android:exported=\"" + exported + "\"";
		}

		StringBuilder sb = new StringBuilder();
		head = head + sb.toString() + "</provider>";
		return head;
    }
}
