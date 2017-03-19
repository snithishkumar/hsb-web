package com.archide.hsb.enumeration;

public enum AppType {
	
	 Kitchen(1),User(2),Captain(3);
    private int appType;
    AppType(int appType){
      this.appType = appType;
    }

    public int getAppType(){
        return appType;
    }
    

}
