package com.ziruk.oa.communitymodule.util;

import android.content.Context;


import com.ziruk.oa.communitymodule.util.bean.AccountInfo;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrentUserInfoUtils {

	private static final String GUID = "com.ziruk.cicc.autocheck.common.CurrentUserInfo.ID";
	private static final String UserID = "com.ziruk.cicc.autocheck.common.CurrentUserInfo.UserID";
	private static final String Unit = "com.ziruk.cicc.autocheck.common.CurrentUserInfo.Unit";
	private static final String PASSWORD = "com.ziruk.cicc.autocheck.common.CurrentUserInfo.Password";
	private static final String Power = "com.ziruk.cicc.autocheck.common.CurrentUserInfo.Power";
	
	public static void SaveUserInfo(Context context, AccountInfo info) {
		Config.SaveConfig(context, UserID, info.getUserName());
		Config.SaveConfig(context, Unit, info.getUnit());
		Config.SaveConfig(context, GUID, info.getGUID());
		Config.SaveConfig(context, PASSWORD, info.getPassword());
	}

	public static void SaveUserInfo(Context context,
			AccountInfo info,
			List<String> powerLst) {
		Config.SaveConfig(context, UserID, info.getUserName());
		Config.SaveConfig(context, Unit, info.getUnit());
		Config.SaveConfig(context, GUID, info.getGUID());
		Config.SaveConfig(context, PASSWORD, info.getPassword());
		
		if (powerLst==null) powerLst=new ArrayList<String>();
		String powerStr = StringUtils.join(powerLst.toArray(), ",") + ",";
		Config.SaveConfig(context, Power, powerStr);
	}
	
	public static String GetUserName(Context context) {
		return Config.LoadConfig(context, UserID);
	}
	
	public static String GetUnit(Context context) {
		return Config.LoadConfig(context, Unit);
	}
	
	public static String getGUID(Context context) {
		return Config.LoadConfig(context, GUID);
	}

	public static String getPassword(Context context) {
		return Config.LoadConfig(context, PASSWORD);
	}

	public static Boolean hasPower(Context context, String id) {
		String tmp = Config.LoadConfig(context, Power);
		return StringUtils.contains(tmp, id + ",");
	}
}
