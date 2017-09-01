package com.example.szymon.ewtk.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Szymon on 28.11.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileServer implements Serializable {

    @JsonProperty("name")
    public String name;
    @JsonProperty("real_name")
    public String realName;
    @JsonProperty("size")
    public int size;

    @Override
    public String toString() {
        return "FileServer{" +
                "name='" + name + '\'' +
                ", realName='" + realName + '\'' +
                ", size=" + size +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
