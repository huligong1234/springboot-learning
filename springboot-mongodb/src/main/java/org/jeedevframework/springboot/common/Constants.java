package org.jeedevframework.springboot.common;

public interface Constants {

	String MISS_MUST_PARAMETER = "缺少必填参数";
	String MSG_SAVE_SUCCESS = "保存成功";
	String MSG_SAVE_FAIL = "保存失败";
	String MSG_UPDATE_SUCCESS = "更新成功";
	String MSG_DELETE_SUCCESS = "删除成功";
	
    /**
     * 数据变更审核状态
     */
    String DATA_CHANGE__UNCHANGED = "0";   //未变更
    String DATA_CHANGE__CHANGING = "1";  //变更中
    String DATA_CHANGE__CLEANING = "4"; //清洗中
    
    /**
     * 数据状态-正常
     */
    String DATA_STATUS__NORMAL = "0"; // 正常
    /**
     * 数据状态-已删除
     */
    String DATA_STATUS__DELETED = "1"; // 已删除
    /**
     * 数据状态-已冻结
     */
    String DATA_STATUS__FREEZE = "2"; // 已冻结
    
    /**
     * 数据审核
     **/
    String DATA_VALID__UNREVIEWED = "0"; // 未审核
    String DATA_VALID__REVIEWING = "1"; // 审核中
    String DATA_VALID__APPROVED = "2"; // 审核通过
    String DATA_VALID__REJECTED = "3"; // 审核拒绝
}
