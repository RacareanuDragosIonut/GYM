package org.loose.fis.sre.exceptions;

public class AppointmentAlreadyMade extends Exception {
    private final String class_name;

    public AppointmentAlreadyMade(String class_name) {
        super(String.format("You already made an appointment to this class!"));
        this.class_name = class_name;
    }

    public String getClass_name() {
        return class_name;
    }
}
