package com.ziruk.oa.communitymodule.capabilities.boot;


import android.app.Activity;
import android.content.Intent;


import com.ziruk.oa.communitymodule.uiCommon.activity.LoginActivity;
import com.ziruk.oa.communitymodule.uiCommon.activity.MainActivity;
import com.ziruk.oa.communitymodule.util.Config;
import com.ziruk.oa.communitymodule.util.CurrentUserInfoUtils;
import com.ziruk.oa.communitymodule.util.bean.AccountInfo;

import org.apache.commons.lang.StringUtils;


public class BootLoader {
	
	public void Load(final Activity context, Boolean showLogin)
	{

		if (showLogin==false) { //直接进入主界面，不显示登录页
            Intent intent=new Intent(); 
            intent.setClass(context,MainActivity.class);
            context.startActivity(intent);
		}
		else {                  //需要有登录逻辑
			AccountInfo accountInfo = new AccountInfo();
	        Config.LoadAccount(context, accountInfo);
	        
	        if ( StringUtils.isNotBlank(accountInfo.getUserName())
	        		&& StringUtils.isNotBlank(accountInfo.getPassword())) {
	        	
	        	CurrentUserInfoUtils.SaveUserInfo(context, accountInfo);
	        	
				Intent intent=new Intent();
		        intent.setClass(context,MainActivity.class);
		        context.startActivity(intent);
	        }
	        else {
				Intent intent=new Intent();
		        intent.setClass(context,LoginActivity.class);
		        context.startActivity(intent);
	        }
		}
		
	}
}
