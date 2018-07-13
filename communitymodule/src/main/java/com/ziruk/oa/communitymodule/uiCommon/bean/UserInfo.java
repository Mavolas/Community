package com.ziruk.oa.communitymodule.uiCommon.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class UserInfo implements Serializable{
    /**
     * 用户ID
     */
    public String UserID;

    /**
     * 用户名
     */
    public String UserName;
    
    /**
     * 用户类型
     */
    public String userType = "";
    
    /**
     * 模块权限
     */
    public List<String> power = new ArrayList<String>();

}
