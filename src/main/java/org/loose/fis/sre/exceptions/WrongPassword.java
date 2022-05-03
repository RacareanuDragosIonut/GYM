package org.loose.fis.sre.exceptions;

public class WrongPassword extends Exception{
    private String password;

    public WrongPassword(String password) {
        super(String.format("Wrong password! Try again."));
        this.password = password;
    }
}
