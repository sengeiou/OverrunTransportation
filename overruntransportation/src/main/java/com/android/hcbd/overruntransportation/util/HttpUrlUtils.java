package com.android.hcbd.overruntransportation.util;

/**
 * Created by guocheng on 2017/3/16.
 */

public class HttpUrlUtils {

    //public static final String BASEURL = "http://112.124.108.24:6080/tps";
    //public static final String BASEURL = "http://112.124.108.24:6780/tps";
    public static final String login_url = "/tpsApp/appLoginAction!login.action";//登录接口
    public static final String check_list_url = "/tpsApp/appPunishAction!checkList.action";//待复核列表接口
    public static final String list_url = "/tpsApp/appPunishAction!list.action";//列表接口
    public static final String content_url = "/tpsApp/appPunishAction!toEdit.action";//新建内容接口
    public static final String save_content_url = "/tpsApp/appPunishAction!edit.action";//保存数据接口
    public static final String edit_content_url = "/tpsApp/appPunishAction!toEdit.action";//修改数据接口
    public static final String delete_content_url = "/tpsApp/appPunishAction!delete.action";//删除数据接口
    public static final String edit_other_content_url = "/tpsApp/appPunishAction!toEditOther.action";//准备编辑图片和问题
    public static final String edit_other_discussed_url = "/tpsApp/appPunishAction!editOther.action";//编辑集体讨论列表接口
    public static final String save_other_question_url = "/tpsApp/jsonAction!editQuestion.action";//问题录入接口
    public static final String save_tps_check_url = "/tpsApp/appPunishAction!checkEdit.action";//复核接口
    public static final String submit_to_check_url = "/tpsApp/appPunishAction!submit.action";//提交复核接口
    public static final String work_day_list_url = "/tpsApp/appWorkDayAction!list.action";//查询工作日安排列表接口
    public static final String add_edit_work_day_url = "/tpsApp/appWorkDayAction!edit.action";//工作日安排保存或修改接口
    public static final String delete_work_day_url = "/tpsApp/appWorkDayAction!delete.action";//工作日安排删除接口
    public static final String default_problem_list_url = "/tpsApp/appTypeAction!list.action";//默认问题列表接口
    public static final String add_edit_default_problem_url = "/tpsApp/appTypeAction!edit.action";//默认问题保存或修改接口
    public static final String delete_default_problem_url = "/tpsApp/appTypeAction!delete.action";//默认问题删除接口
    public static final String law_info_list_url = "/tpsApp/appLawAction!list.action";//法条信息列表接口
    public static final String toedit_law_info_url = "/tpsApp/appLawAction!toEdit.action";//法条信息准备编辑接口
    public static final String edit_law_info_url = "/tpsApp/appLawAction!edit.action";//法条信息编辑接口
    public static final String delete_law_info_url = "/tpsApp/appLawAction!delete.action";//法条信息删除接口

    public static final String template_manage_list_url = "/tpsApp/appWordAction!list.action";//模版管理列表接口
    public static final String save_word_list_url = "/tpsApp/appWordAction!edit.action";//模版管理列表接口
    public static final String delete_word_list_url = "/tpsApp/appWordAction!delete.action";//模版管理列表接口
    public static final String download_word_url = "/tpsApp/appWordAction!exportLocal.action";//模版管理列表接口
    public static final String generate_word_url = "/tpsApp/appWordAction!generateWord.action";//模版管理列表接口

    public static final String car_type_list_url = "/tpsApp/appCarTypeAction!list.action";//车型信息列表接口
    public static final String edit_car_type_url = "/tpsApp/appCarTypeAction!edit.action";//车型信息编辑接口
    public static final String delete_car_type_url = "/tpsApp/appCarTypeAction!delete.action";//车型信息删除接口
    public static final String overload_data_list_url = "/tpsApp/appDataAction!list.action";//治超数据列表接口
    public static final String edit_overload_data_url = "/tpsApp/appDataAction!edit.action";//治超数据编辑接口
    public static final String delete_overload_data_url = "/tpsApp/appDataAction!delete.action";//治超数据删除接口

    public static final String driver_info_url = "/tpsApp/jsonAction!getDriverInfo.action";//查询司机信息
    public static final String edit_pwd_url = "/tpsApp/appLoginAction!editPwd.action";//修改密码

    public static final String export_overload_data_url = "/tpsApp/appDataAction!export.action";//超治信息表导出


}
