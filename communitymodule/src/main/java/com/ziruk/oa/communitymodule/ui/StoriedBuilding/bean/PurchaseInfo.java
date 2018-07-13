package com.ziruk.oa.communitymodule.ui.StoriedBuilding.bean;

/**
 * Created by 宋棋安
 * on 2018/6/15.
 */

import java.io.Serializable;


public class PurchaseInfo implements Serializable {

    /// <summary>
    /// ID
    /// </summary>
    public String ID;
    /// <summary>
    /// String
    /// </summary>
    public String BiddingCode;
    /// <summary>
    /// 招标名称
    /// </summary>
    public String BiddingName;
    /// <summary>
    /// 所属部门
    /// </summary>
    public String Department;
    /// <summary>
    /// 招标方式
    /// </summary>
    public String BiddingType;
    /// <summary>
    /// 经办人
    /// </summary>
    public String Transactor;
    /// <summary>
    /// 经办人
    /// </summary>
    public String TransactorUserID;
    /// <summary>
    /// 采购状态
    /// </summary>
    public String BiddingStatus;
    /// <summary>
    /// 概算金额
    /// </summary>
    public Double EstimatedAmount;


}
