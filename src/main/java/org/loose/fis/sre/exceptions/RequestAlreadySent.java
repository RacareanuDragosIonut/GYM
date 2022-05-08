package org.loose.fis.sre.exceptions;

public class RequestAlreadySent extends Exception {
    private final String username;

    public RequestAlreadySent(String username) {
        super(String.format("You already sent a request to this trainer"));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
