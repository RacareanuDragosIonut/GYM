package org.loose.fis.sre.model;

public class Client {
    private String client_information;
    public Client(String client_information) {
        this.client_information = client_information;
    }

    public String getclient_information() {
        return client_information;
    }

    public void setclient_information() {
        this.client_information = client_information;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if(o.getClass()!=getClass())
            return false;
        Client other = (Client) o;

        if (!client_information.equals(other.client_information))
            return false;
        return true;
    }

}

