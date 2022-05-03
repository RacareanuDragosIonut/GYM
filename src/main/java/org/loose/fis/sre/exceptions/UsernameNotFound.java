package org.loose.fis.sre.exceptions;

public class UsernameNotFound extends Exception{
    public String username;

    public UsernameNotFound(String username) {
        super(String.format("An account with the username %s doesn't exist!", username));
        this.username = username;
    }
}
