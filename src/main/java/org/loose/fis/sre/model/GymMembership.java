package org.loose.fis.sre.model;

public class GymMembership {
    private String membership_type;
    private String hours;
    private String price;

    public GymMembership(String membership_type, String hours, String price) {
        this.membership_type = membership_type;
        this.hours = hours;
        this.price = price;
    }

    public String getMembership_type() {
        return membership_type;
    }

    public void setMembership_type(String membership_type) {
        this.membership_type = membership_type;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GymMembership membership = (GymMembership) o;

        if (membership_type != null ? !membership_type.equals(membership.membership_type) : membership.membership_type != null) return false;
        if (hours != null ? !hours.equals(membership.hours) : membership.hours != null) return false;
        if (price != null ? !price.equals(membership.price) : membership.price != null) return false;
        return false;
    }
}
