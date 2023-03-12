package com.iiaccount.data;


import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RequestData {
    private boolean run;
    private String story;
    private String moduleFlag;

    private String host;
    private String path;

    private String url;
    private String method;

    private LinkedHashMap<String,Object> requestdata;

    private LinkedHashMap<String,Object> respdata;

    private ArrayList<Object> assertdata;

    private String description;

    private LinkedHashMap<String,Object> dependon;

    public String getUrl() {
        return url;
    }

    public RequestData setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RequestData setMethod(String method) {
        this.method = method;
        return this;
    }



    public String getModuleFlag() {
        return moduleFlag;
    }

    public RequestData setModuleFlag(String moduleFlag) {
        this.moduleFlag = moduleFlag;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RequestData setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getHost() {
        return host;
    }

    public RequestData setHost(String host) {
        this.host = host;
        return this;
    }

    public String getPath() {
        return path;
    }

    public RequestData setPath(String path) {
        this.path = path;
        return this;
    }



    public boolean getRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public LinkedHashMap<String, Object> getRequestdata() {
        return requestdata;
    }

    public void setRequestdata(LinkedHashMap<String, Object> requestdata) {
        this.requestdata = requestdata;
    }

    public LinkedHashMap<String, Object> getRespdata() {
        return respdata;
    }

    public void setRespdata(LinkedHashMap<String, Object> respdata) {
        this.respdata = respdata;
    }

    public ArrayList<Object> getAssertdata() {
        return assertdata;
    }

    public void setAssertdata(ArrayList<Object> assertdata) {
        this.assertdata = assertdata;
    }

    public LinkedHashMap<String, Object> getDependon() {
        return dependon;
    }

    public void setDependon(LinkedHashMap<String, Object> dependon) {
        this.dependon = dependon;
    }
}
