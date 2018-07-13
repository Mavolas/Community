package com.ziruk.oa.communitymodule.util.bean;

import org.apache.commons.lang.StringUtils;

public class AccountInfo implements IAccountInfo {

	private String GUID;
	private String UserName;
	private String Password;
	/***
	 * 用户类型，1：养殖场人员；2：畜牧站人员；3：畜牧局人员
	 */
	private String Unit;
	private Boolean AutoSaveType = false;
	
	final private String splitor = "###%%__&$^@#()";
	final private String nullSeparator = "[";
	
	public AccountInfo() {
	}
	
	public AccountInfo(String guid,
                       String userName,
                       String password,
                       String Unit,
                       Boolean AutoSaveType) {
		super();
		GUID = guid;
		UserName = userName;
		Password = password;
		this.Unit = Unit;
		this.AutoSaveType = AutoSaveType;
	}
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public Boolean getAutoSaveType() {
		return AutoSaveType;
	}

	public void setAutoSaveType(Boolean autoSaveType) {
		AutoSaveType = autoSaveType;
	}

	/**
	 * 用户类型，1：养殖场人员；2：畜牧站人员；3：畜牧局人员
	 * @return
	 */
	public String getUnit() {
		return Unit;
	}
	public String getGUID() {
		return GUID;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}



	@Override
	public String Account2Str() {
		String tmp = "";
		tmp += nullSeparator + GUID + splitor;
		tmp += nullSeparator + UserName + splitor;
		tmp += nullSeparator + Password + splitor;
		tmp += nullSeparator + Unit + splitor;
		tmp += nullSeparator + (AutoSaveType==true ? "1" : "0") + splitor;
		
		return tmp;
	}

	@Override
	public IAccountInfo Str2Account(String str) {
		
		if ( StringUtils.isBlank(str))
			return new AccountInfo("", "", "", "", false);
		
		String[] array = StringUtils.split(str, splitor);
		if (array != null && array.length>=5) {
		
			this.GUID = StringUtils.substringAfter(array[0], nullSeparator);
			this.UserName = StringUtils.substringAfter(array[1], nullSeparator);;
			this.Password = StringUtils.substringAfter(array[2], nullSeparator);;
			this.Unit = StringUtils.substringAfter(array[3], nullSeparator);;
			this.AutoSaveType = StringUtils.substringAfter("1", nullSeparator).equals(array[4]) ? true : false;
		}
		
		return this;
	}
}
