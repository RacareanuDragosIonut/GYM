package org.loose.fis.sre.exceptions;

public class Classdoesnotexist extends Exception{
    private String type;
    private String day;
    private String hour;
    private int numberofclients;
    public Classdoesnotexist(String type,String day,String hour,int numberofclients){
        super(String.format("The class does not exist"));
        this.type=type;
        this.hour=hour;
        this.day=day;
        this.numberofclients=numberofclients;
    }
    public String gettype(){
        return type;
    }
    public String gethour(){
        return hour;
    }
    public String getday(){
        return day;
    }
    public int getnumberofclients(){
        return numberofclients;
    }
}
