package com.example.szymon.ewtk.Model;

import android.util.Log;

import com.example.szymon.ewtk.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Szymon on 03.10.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken implements Serializable {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;

    public AccessToken() {

    }

    public String serializer(User user, String password){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("grant_type", Constants.GRAND_TYPE);
            jsonObject.accumulate("username", user.getEmail());
            jsonObject.accumulate("password", password);
            jsonObject.accumulate("client_id", "test");
            jsonObject.accumulate("client_secret", "test");
            return jsonObject.toString();
        }catch(JSONException ex){
            Log.e("Access serializer: ", ex.getLocalizedMessage(), ex);
            return null;
        }
    }

    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public int getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
