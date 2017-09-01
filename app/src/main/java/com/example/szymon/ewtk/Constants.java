package com.example.szymon.ewtk;

/**
 * The Constants has a const. build url to connection apk
 * Created by Szymon on 02.10.2016.
 */

public class Constants {

    public static final String GRAND_TYPE = "password";
    public static final String ADDRESS_URL = "http://85.255.6.225/rest/";

    //static build string to login, access_token and logout
    public static final String LOGIN_URL = "api/auth/login";
    public static final String LOGOUT_URL = "api/auth/logout";
    public static final String ACCESS_TOKEN_URL = "api/auth/access-token";

    //static build string to models
    public static final String MODELS_URL = "api/inquiries/{id}/modules";
    public static final String INQUIRY_DETAILS_URL = "api/inquiries/{id}";
    public static final String QUESTIONS_URL = "api/module/{id}/questions";
    public static final String USER_DATA_URL = "api/auth/user-data";
    public static final String FILE_SEND_URL = "api/upload";

    //static build string to chat
    public static final String CONVERSATIONS_CHAT_URL = "api/get-conversations";
    public static final String ENDPOINT_CHAT_URL = "api/pusher-auth";
    public static final String MESSAGE_SEND_CHAT_URL = "api/conversation/{cid}/messages";
    public static final String MESSAGES_ALL_CHAT_URL = "api/conversation/{cid}/messages";
    public static final String MESSAGE_CHAT_URL = "api/conversation/{cid}/message/{mid}";
    public static final String PUSHER_KEY = "19951d1ee0e22a11d878";

    //static build string to answers
    public static final String ANSWERS_SEND_URL = "/api/inquiry/{id}/module/{mid}/module-execution/{meid}/fill-module";

    public static final String QUESTIONS_CURRENT = "questionsCurrent";
    public static final String MODEL_ID = "modelID";
    public static final String GENDER = "gender";
    public static final String MODEL_EXECUTION_ID = "modelExecutionID";
    public static final String USER_ID = "userID";
    public static final String CONVERSATION_ID = "conversationID";
    public static final String CONVERSATION_USER = "conversationUser";

    //static instance key
    public static final String QUESTIONS_INSTANCE = "questionsInstance";
    public static final String ANSWERS_INSTANCE = "answersInstance";
    public static final String USER_INSTANCE = "userInstance";
    public static final String MODEL_INSTANCE = "modelInstance";
    public static final String INQUIRY_INSTANCE = "inquiryInstance";
    public static final String DISEASE_INSTANCE = "diseaseInstance";
    public static final String DISEASES_INSTANCE = "diseasesInstance";
    public static final String ADDICTIONS_INSTANCE = "addictionsInstance";
    public static final String QUESTION_CURRENT = "questionCurrent";

    //static state key
    public static final String QUESTIONS_STATE = "questionsState";
    public static final String QUESTONS_CURRENT_STATE = "questionsCurrentState";
    public static final String DISEASE_STATE = "diseaseState";
    public static final String DISEASES_STATE = "diseasesState";
    public static final String ADDICTIONS_STATE = "addictionsState";
    public static final String INQUIRY_STATE = "inquiryState";
    public static final String USER_STATE = "userState";
    public static final String MODEL_STATE = "modelState";
    public static final String ANSWERS_STATE = "answerState";
    public static final String MODELS_LIST_STATE = "modelsListState";
    public static final String CHAT_STATE = "chatState";
}
