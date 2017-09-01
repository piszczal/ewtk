package com.example.szymon.ewtk;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.example.szymon.ewtk.Model.AccessToken;
import com.example.szymon.ewtk.Model.Chat;
import com.example.szymon.ewtk.Model.FileServer;
import com.example.szymon.ewtk.Model.Inquiry;
import com.example.szymon.ewtk.Model.Message;
import com.example.szymon.ewtk.Model.Model;
import com.example.szymon.ewtk.Model.User;

/**
 * Created by Szymon on 15.10.2016.
 */

public interface RestClient {
    User login(String object);
    void logout();
    AccessToken getAccessToken(String object);

    User userData();

    List<Model> getModelsList(int inquiryID);
    Model getQuestionsModel(int modelID);
    Inquiry getInquiryDetails(int inquiryID);

    ResponseEntity<Void> sendAnswers(String object, int inquiryID, int moduleID, int meidID);

    FileServer sendFile(MultiValueMap<String, Object> file);

    Chat getConversations();
    Chat getAllMessages(int cid);
    Message getMessage(int cid, int mid);
    ResponseEntity<Void> sendMessage(String object, int cid);

    RestTemplate getRestTemplate();
    void setRestTemplate(RestTemplate restTemplate);

    void setAuthentication(HttpAuthentication auth);
    void setBearerAuth(String token);
}
