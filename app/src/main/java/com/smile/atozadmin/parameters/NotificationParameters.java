package com.smile.atozadmin.parameters;

public class NotificationParameters {

    String id,title,message,url;

    public NotificationParameters() {
    }

    public NotificationParameters(String id, String title, String message, String url) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
