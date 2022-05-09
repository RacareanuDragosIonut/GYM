package org.loose.fis.sre.model;

public class Trainer {
    private String fullname;
    private String type_of_training;
    private String price;

    public Trainer(String fullname, String type_of_training, String price) {
        this.fullname = fullname;
        this.type_of_training = type_of_training;
        this.price = price;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getType_of_training() {
        return type_of_training;
    }

    public void setType_of_training(String type_of_training) {
        this.type_of_training = type_of_training;
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

        Trainer trainer = (Trainer) o;

        if (fullname != null ? !fullname.equals(trainer.fullname) : trainer.fullname != null) return false;
        if (type_of_training != null ? !type_of_training.equals(trainer.type_of_training) : trainer.type_of_training != null) return false;
        if (price != null ? !price.equals(trainer.price) : trainer.price != null) return false;
        return false;
    }
}
