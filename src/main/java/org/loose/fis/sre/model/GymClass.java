package org.loose.fis.sre.model;

public class GymClass {
    private int idappointment;
    private String class_name;

    public GymClass(int idappointment, String class_name) {
        this.idappointment = idappointment;
        this.class_name = class_name;
    }

    public GymClass(String class_name) {
        this.class_name = class_name;
    }

    public int getIdappointment() {
        return idappointment;
    }

    public void setIdappointment() {
        this.idappointment = idappointment;
    }

    public String getClass_name() {
        return class_name;
    }

    public void  setClass_name() {
        this.class_name = class_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GymClass gymclass = (GymClass) o;

        if (class_name != null ? !class_name.equals(gymclass.class_name) : gymclass.class_name != null) return false;
        //if (idappointment != null ? !idappointment.equals(gymclass.idappointment) : gymclass.idappointment != null) return false;
        return false;
    }
}
