package org.loose.fis.sre.exceptions;

public class TooYoung extends Exception{
    public TooYoung(){
        super(String.format("Too young for this membership"));
    }
}
