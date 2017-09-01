package com.example.szymon.ewtk;
import android.content.Context;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import com.example.szymon.ewtk.Model.AccessToken;
import com.example.szymon.ewtk.Model.Answer;
import com.example.szymon.ewtk.Model.Chat;
import com.example.szymon.ewtk.Model.FileServer;
import com.example.szymon.ewtk.Model.Inquiry;
import com.example.szymon.ewtk.Model.Message;
import com.example.szymon.ewtk.Model.Model;
import com.example.szymon.ewtk.Model.User;

/**
 * Created by Szymon on 15.10.2016.
 */

public final class Methods implements RestClient {

    private RestTemplate restTemplate;
    private HttpAuthentication authentication;
    private String rootUrl;

    public Methods(Context context) {
        restTemplate = new RestTemplate();
        rootUrl = Constants.ADDRESS_URL;
        restTemplate.getMessageConverters();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Override
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void setBearerAuth(final String token) {
        this.authentication = new HttpAuthentication() {

            @Override
            public String getHeaderValue() {
                return ("Bearer " + token);
            }
        }
        ;
    }

    @Override
    public void setAuthentication(HttpAuthentication auth) {
        this.authentication = auth;
    }


    @Override
    public User login(String object) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(object, httpHeaders);
        return restTemplate.exchange(rootUrl.concat(Constants.LOGIN_URL), HttpMethod.POST, requestEntity, User.class).getBody();
    }

    @Override
    public void logout() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAuthorization(authentication);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        restTemplate.exchange(rootUrl.concat(Constants.LOGOUT_URL), HttpMethod.POST, requestEntity, String.class);
    }

    @Override
    public AccessToken getAccessToken(String object) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<String>(object, httpHeaders);
        return restTemplate.exchange(rootUrl.concat(Constants.ACCESS_TOKEN_URL), HttpMethod.POST, requestEntity, AccessToken.class).getBody();
    }

    @Override
    public User userData() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAuthorization(authentication);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat(Constants.USER_DATA_URL), HttpMethod.GET, requestEntity, User.class).getBody();
    }

    @Override
    public List<Model> getModelsList(int inquiryID) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAuthorization(authentication);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("id", inquiryID);
        return restTemplate.exchange(rootUrl.concat(Constants.MODELS_URL), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Model>>() {
                }
                , urlVariables).getBody();
    }

    @Override
    public Inquiry getInquiryDetails(int inquiryID) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAuthorization(authentication);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("id", inquiryID);
        return restTemplate.exchange(rootUrl.concat(Constants.INQUIRY_DETAILS_URL), HttpMethod.GET, requestEntity, Inquiry.class, urlVariables).getBody();
    }

    @Override
    public Model getQuestionsModel(int modelID) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAuthorization(authentication);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("id", modelID);
        return restTemplate.exchange(rootUrl.concat(Constants.QUESTIONS_URL), HttpMethod.GET, requestEntity, Model.class, urlVariables).getBody();
    }

    @Override
    public ResponseEntity<Void> sendAnswers(String object, int inquiryID, int moduleID, int meidID) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.setAuthorization(authentication);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(object, httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("id", inquiryID);
        urlVariables.put("mid", moduleID);
        urlVariables.put("meid", meidID);
        return restTemplate.exchange(rootUrl.concat(Constants.ANSWERS_SEND_URL), HttpMethod.PUT, requestEntity, ((Class<Void>) null), urlVariables);
    }

    @Override
    public FileServer sendFile(MultiValueMap<String, Object> file) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.setAuthorization(authentication);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(file, httpHeaders);
        return restTemplate.exchange(rootUrl.concat(Constants.FILE_SEND_URL), HttpMethod.POST, requestEntity, FileServer.class).getBody();
    }

    @Override
    public Chat getConversations(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAuthorization(authentication);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat(Constants.CONVERSATIONS_CHAT_URL), HttpMethod.GET, requestEntity, Chat.class).getBody();
    }

    @Override
    public Chat getAllMessages(int cid) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        httpHeaders.setAuthorization(authentication);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("cid", cid);
        return restTemplate.exchange(rootUrl.concat(Constants.MESSAGES_ALL_CHAT_URL), HttpMethod.GET, requestEntity, Chat.class, urlVariables).getBody();
    }

    @Override
    public Message getMessage(int cid, int mid) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        httpHeaders.setAuthorization(authentication);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("cid", cid);
        urlVariables.put("mid", mid);
        return restTemplate.exchange(rootUrl.concat(Constants.MESSAGE_CHAT_URL), HttpMethod.GET, requestEntity, Message.class, urlVariables).getBody();
    }

    @Override
    public ResponseEntity<Void> sendMessage(String object, int cid) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        httpHeaders.setAuthorization(authentication);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(object, httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("cid", cid);
        return restTemplate.exchange(rootUrl.concat(Constants.MESSAGE_SEND_CHAT_URL), HttpMethod.PUT, requestEntity, ((Class<Void>) null), urlVariables);
    }
}

