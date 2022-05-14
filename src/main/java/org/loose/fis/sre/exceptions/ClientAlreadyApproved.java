package org.loose.fis.sre.exceptions;

public class ClientAlreadyApproved extends Exception{
    public ClientAlreadyApproved(){
        super(String.format("Client Already Approved"));
    }
}
