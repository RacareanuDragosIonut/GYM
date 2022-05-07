package org.loose.fis.sre.exceptions;

public class Nospacesavailable extends Exception{
    private String client_information;
    public Nospacesavailable(){
        super(String.format("No spaces available"));
    }
    public String getClient_information(){
        return client_information;
    }
}
