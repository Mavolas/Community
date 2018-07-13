package com.ziruk.oa.communitymodule.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ziruk.oa.communitymodule.util.bean.IAccountInfo;


public class Config {
	
	private final static String SharedPreferencesName = "Config";
	private final static String SharedPreferencesKeyAccount = "userInfoStr";


	public static void SaveAccount(Context context, IAccountInfo accountInfo) {
		SharedPreferences sharedPreferences =
				context.getSharedPreferences(SharedPreferencesName,
						Context.MODE_PRIVATE);


		Editor editor = sharedPreferences.edit();
		editor.putString(SharedPreferencesKeyAccount, accountInfo.Account2Str());
		editor.commit();
	}

	public static void LoadAccount(Context context, IAccountInfo accountInfo) {
		SharedPreferences sharedPreferences =
				context.getSharedPreferences(SharedPreferencesName,
						Context.MODE_PRIVATE);
		String tmp = sharedPreferences.getString(SharedPreferencesKeyAccount, "");

		accountInfo.Str2Account(tmp);
	}

	
	public static void SaveConfig(Context context, String key, String value) {
		SharedPreferences sharedPreferences =
				context.getSharedPreferences(SharedPreferencesName, 
						Context.MODE_PRIVATE);
		
		
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String LoadConfig(Context context, String key) {
		SharedPreferences sharedPreferences =
				context.getSharedPreferences(SharedPreferencesName, 
						Context.MODE_PRIVATE);
		String tmp = sharedPreferences.getString(key, "");
		
		return tmp;
	}

}
