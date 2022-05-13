package org.loose.fis.sre.model;

public class MembershipRequests {
    private String client_username;
    private String membership_type;

    public MembershipRequests(String client_username, String membership_type) {
        this.client_username = client_username;
        this.membership_type = membership_type;
    }

    public String getClient_username() {
        return client_username;
    }

    public void setClient_username(String client_username) {
        this.client_username = client_username;
    }

    public String getMembership_type() {
        return membership_type;
    }

    public void setMembership_type(String membership_type) {
        this.membership_type = membership_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MembershipRequests membershiprequests = (MembershipRequests) o;

        if (client_username != null ? !client_username.equals(membershiprequests.client_username) : membershiprequests.client_username != null) return false;
        if (membership_type != null ? !membership_type.equals(membershiprequests.membership_type) : membershiprequests.membership_type != null) return false;
        return false;
    }
}
