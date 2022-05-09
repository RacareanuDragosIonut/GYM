package org.loose.fis.sre.exceptions;

public class DayandHourTaken extends Exception{
    private  String day;
            private  String hour;

    public DayandHourTaken(String day,String hour) {
        super(String.format("A class with the day %s and the hour %s already exists", day,hour));
        this.day = day;
        this.hour=hour;
    }

    public String getday() {
        return day;
    }
    public String gethour(){return hour;}
}
