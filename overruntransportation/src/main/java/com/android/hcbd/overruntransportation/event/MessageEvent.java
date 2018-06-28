package com.android.hcbd.overruntransportation.event;

/**
 * Created by guocheng on 2017/3/17.
 */
public class MessageEvent {

    /*发出的广播类型*/
    public static final int EVENT_CAR_TYPE = 1;
    public static final int EVENT_EVIDENCE_MATERIAL = 2;
    public static final int EVENT_TPS_ADD_SUCCESS = 3;
    public static final int EVENT_TPS_EDIT_SUCCESS = 4;
    public static final int EVENT_OTHER_QUESTION_ADD_SUCCESS = 5;
    public static final int EVENT_OTHER_QUESTION_EDIT_SUCCESS = 6;
    public static final int EVENT_OTHER_UPDATE_SUCCESS = 7;
    public static final int EVENT_OTHER_PHONE_ADD_SUCCESS = 8;
    public static final int EVENT_OTHER_PHONE_EDIT_SUCCESS = 100;
    public static final int EVENT_OTHER_PHONE_DELETE_SUCCESS = 9;
    public static final int EVENT_OTHER_DISCUSSED_SAVE_SUCCESS = 10;
    public static final int EVENT_TPS_CHECK_SAVE = 11;
    public static final int EVENT_TPS_CHECK_SAVE_SUCCESS = 12;

    public static final int EVENT_WORK_DAY_LIST_EDIT = 13;
    public static final int EVENT_WORK_DAY_LIST_ADD = 14;
    public static final int EVENT_WORK_DAY_LIST_DEL = 15;

    public static final int EVENT_DEFAULT_PROBLEM_LIST_EDIT = 16;
    public static final int EVENT_DEFAULT_PROBLEM_LIST_ADD = 17;
    public static final int EVENT_DEFAULT_PROBLEM_LIST_DEL = 18;

    public static final int EVENT_LEGAL_NAME_LIST_EDIT = 19;
    public static final int EVENT_LEGAL_NAME_LIST_ADD = 20;
    public static final int EVENT_LEGAL_NAME_LIST_DEL = 21;

    public static final int EVENT_LAW_INFO_LIST_EDIT = 22;
    public static final int EVENT_LAW_INFO_LIST_ADD = 23;
    public static final int EVENT_LAW_INFO_LIST_DEL = 24;

    public static final int EVENT_WORD_LIST_EDIT = 25;
    public static final int EVENT_WORD_LIST_ADD = 26;
    public static final int EVENT_WORD_LIST_DEL = 27;

    public static final int EVENT_TEMPLATE_LIST_EDIT = 28;
    public static final int EVENT_TEMPLATE_LIST_ADD = 29;
    public static final int EVENT_TEMPLATE_LIST_DEL = 30;

    public static final int EVENT_CAR_TYPE_LIST_EDIT = 31;
    public static final int EVENT_CAR_TYPE_LIST_ADD = 32;
    public static final int EVENT_CAR_TYPE_LIST_DEL = 33;

    public static final int EVENT_OVERLOAD_DATA_LIST_EDIT = 34;
    public static final int EVENT_OVERLOAD_DATA_LIST_ADD = 35;
    public static final int EVENT_OVERLOAD_DATA_LIST_DEL = 36;

    public static final int EVENT_EM_LIST_EDIT = 37;
    public static final int EVENT_EM_LIST_ADD = 38;
    public static final int EVENT_EM_LIST_DEL = 39;

    public static final int EVENT_OVERLOAD_ADD1 = 40;
    public static final int EVENT_OVERLOAD_ADD2 = 41;

    public static final int EVENT_MAIN_FINISH = 42;

    public static final int EVENT_TPS_SEARCH = 43;

    public static final int EVENT_LOCATION_CUCCESS = 100;

    public static final int EVENT_LOGINOUT = 119;
    public static final int EVENT_IPADDRESS_DEL = 120;


    private int eventId;
    private Object obj;
    private Object obj2;
    public MessageEvent() {
    }

    public MessageEvent(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj2() {
        return obj2;
    }

    public void setObj2(Object obj2) {
        this.obj2 = obj2;
    }
}
