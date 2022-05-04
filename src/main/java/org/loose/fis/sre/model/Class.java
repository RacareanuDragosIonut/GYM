package org.loose.fis.sre.model;

public class Class {
    private String type;
    private String day;
    private String hour;
    private int numberofclients;
    public Class(String type,String day,String hour,int numberofclients){
        this.type=type;
        this.day=day;
        this.hour=hour;
        this.numberofclients=numberofclients;
    }
    public String gettype(){
        return type;
    }
    public void settype(String type){
        this.type=type;
    }
    public String getday(){
        return day;
    }
    public void setday(String day){
        this.day=day;
    }
    public String gethour(){
        return hour;
    }
    public void sethour(String hour){
        this.hour=hour;
    }
    public int getnumberofclients() {
        return numberofclients;
    }
    public void setNumberofclients(){
        this.numberofclients=numberofclients;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Class other = (Class) o;

        if(type!=other.type)
            return false;
        if (day != other.day)
            return false;
        if (hour != other.hour)
            return false;

        return true;
    }
    @Override
    public int hashCode(){
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (hour != null ? hour.hashCode() : 0);
        return result;
    }
}

