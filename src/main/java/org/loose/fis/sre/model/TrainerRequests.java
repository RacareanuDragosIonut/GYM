package org.loose.fis.sre.model;

public class TrainerRequests {
    private String client_username;
    private String trainer;

    public TrainerRequests(String client_username, String trainer) {
        this.client_username = client_username;
        this.trainer = trainer;
    }

    public String getClient_username() {
        return client_username;
    }

    public void setClient_username(String client_username) {
        this.client_username = client_username;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainerRequests trainerrequests = (TrainerRequests) o;

        if (client_username != null ? !client_username.equals(trainerrequests.client_username) : trainerrequests.client_username != null) return false;
        if (trainer != null ? !trainer.equals(trainerrequests.trainer) : trainerrequests.trainer != null) return false;
        return false;
    }
}
