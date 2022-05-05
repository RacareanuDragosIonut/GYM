package org.loose.fis.sre.exceptions;

public class Classwiththatscheduledoesnotexist extends Exception{
    private String day;
    private String hour;
    public Classwiththatscheduledoesnotexist(String day,String hour){
        super(String.format("A class with the day %s and the hour %s does not exist", day,hour));
        this.day=day;
        this.hour=hour;
    }
    public String getday() {
        return day;
    }
    public String gethour(){return hour;}
}
