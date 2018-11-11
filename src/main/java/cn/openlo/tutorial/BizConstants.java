/**
*
*/
package cn.openlo.tutorial;

/**
 * <p>
 * Title: BizConstants
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author gaozhi
 *
 * @version
 * @since 2016年5月24日
 */
public class BizConstants {

    public static final String ORDER_STATUS_ACCEPT = "00";// 受理
    public static final String ORDER_STATUS_CONFIRM = "01";// 确认
    public static final String ORDER_STATUS_INVALID = "09";// 失败

    public static final String HANDLE_STATUS_INIT = "00";// 初始
    public static final String HANDLE_STATUS_SUCC = "01";// 成功
    public static final String HANDLE_STATUS_FAIL = "02";// 失败
    public static final String HANDLE_STATUS_DOING = "03";// 处理中
    public static final String HANDLE_STATUS_EXCEPTION = "09";// 异常
}
