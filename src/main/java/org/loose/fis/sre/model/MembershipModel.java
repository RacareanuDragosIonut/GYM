package org.loose.fis.sre.model;

public class MembershipModel {
    private String membership_type;
    private String client_name;
    private String client_age;
    public MembershipModel(String membership_type,String client_name,String client_age){
        this.membership_type=membership_type;
        this.client_name=client_name;
        this.client_age=client_age;
    }
    public String getmembership_type(){
        return membership_type;
    }
    public void setmembership_type(String membership_type){
        this.membership_type=membership_type;
    }
    public String getclient_name(){
        return client_name;
    }
    public void setclient_name(String client_name){
        this.client_name=client_name;
    }
    public String getclient_age(){
        return client_age;
    }
    public void setclient_age(String client_age){
        this.client_age=client_age;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        MembershipModel other = (MembershipModel) o;

        if(membership_type!=other.membership_type)
            return false;
        if (client_name != other.client_name)
            return false;
        if (client_age != other.client_age)
            return false;

        return true;
    }
}
