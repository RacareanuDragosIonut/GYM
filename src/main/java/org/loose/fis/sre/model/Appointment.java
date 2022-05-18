package org.loose.fis.sre.model;

public class Appointment {
    private String class_name;
    private String client_username;

    public Appointment(String class_name, String client_username) {
        this.class_name = class_name;
        this.client_username = client_username;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClient_username() {
        return client_username;
    }

    public void setClient_username(String client_username) {
        this.client_username = client_username;
    }
}
