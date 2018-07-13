package com.ziruk.oa.communitymodule.capabilities.zirukhttp.mdel;

import java.util.ArrayList;
import java.util.List;

public class ResponseCls<T> {
	
	//0:正常；1：Session过期；2：系统异常
	private String RequestStatus;
	private String ReturnType;
	private List<Message> ErrorMessage = new ArrayList<Message>();
	private List<Message> InfoMessage = new ArrayList<Message>();
	private T Data;

	public PageInfo pageInfo;

	
	public ResponseCls()
	{
		super();
	}

	
	public ResponseCls(String requestStatus, String returnType,
                       List<Message> errorMessage, List<Message> infoMessage, T data) {
		super();
		RequestStatus = requestStatus;
		ReturnType = returnType;
		ErrorMessage = errorMessage;
		InfoMessage = infoMessage;
		Data = data;
	}

	public String getRequestStatus() {
		return RequestStatus;
	}

	public String getReturnType() {
		return ReturnType;
	}

	public List<Message> getErrorMessage() {
		return ErrorMessage;
	}

	public List<Message> getInfoMessage() {
		return InfoMessage;
	}

	public T getData() {
		return Data;
	}


	public class Message {
		public String MessageID;
		public String MessageText;
		public String[] Paras;

	}

	public class PageInfo {
		public int PageIndex;
		public int PageSize;
		public int TotalCount;
		public int TotalPages;
	}


}
