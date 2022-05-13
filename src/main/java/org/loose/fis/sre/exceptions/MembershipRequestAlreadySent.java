package org.loose.fis.sre.exceptions;

public class MembershipRequestAlreadySent extends Exception {
    private final String username;

    public MembershipRequestAlreadySent(String username) {
        super(String.format("You already sent a request for buying this membership!"));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
