package org.loose.fis.sre.exceptions;

public class ClassFull extends Exception {

    private final String class_name;

    public ClassFull(String class_name) {
        super(String.format("The class is full!"));
        this.class_name = class_name;
    }

    public String getClass_name() {
        return class_name;
    }
}