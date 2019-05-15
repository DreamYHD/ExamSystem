package com.example.administrator.examsystem.utils;

/**
 * Created by Administrator on 2019/5/8.
 */

public class TableUtil {
    //plan表
    public static final String PLAN_TABLE_NAME = "Plan";
    public static final String PLAN_CONTENT = "planContent";
    public static final String PLAN_DATE = "planDate";
    public static final String PLAN_START_TIME = "planStartTime";
    public static final String PLAN_END_TIME = "planEndTime";
    public static final String PLAN_IS_FINISH = "planIsFinish";
    public static final String PLAN_USER = "user";

    //user
    public static final String USER_TABLE_NAME = "_User";
    public static final String USER_NAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PHONE_NUMBER = "mobilePhoneNumber";
    public static final String USER_SCHOOL = "schoolName";
    public static final String USER_QUESTION = "questions";

    //note
    public static final String NOTE_TABLE_NAME = "Note";
    public static final String NOTE_TITLE = "noteTitle";
    public static final String NOTE_CONTENT = "noteContent";
    public static final String NOTE_IMG = "noteImg";
    public static final String NOTE_NAME = "noteName";
    public static final String NOTE_USER = "user";

    //Discuss
    public static final String DISCUSS_TABLE_NAME = "Discuss";
    public static final String DISCUSS_USER = "user";
    public static final String DISCUSS_TIME = "discussTime";
    public static final String DISCUSS_IMG = "discussImg";
    public static final String DISCUSS_TAG = "discussTag";
    public static final String DISCUSS_CONTENT = "discussContent";
    public static final String DISCUSS_ITEM = "discussItem";

    //Question
    public static final String QUESTION_TABLE_NAME = "Question";
    //科目 0：政治 1：英语
    // 00 政治单选 01政治多选 10 英语一阅读  11英语二阅读
    public static final String QUESTION_TYPE = "type";
    public static final String QUESTION_TITLE = "title";
    public static final String QUESTION_SELECT = "select";
    public static final String QUESTION_ANSWER = "answer";
    public static final String QUESTION_TEACH = "teach";

}
