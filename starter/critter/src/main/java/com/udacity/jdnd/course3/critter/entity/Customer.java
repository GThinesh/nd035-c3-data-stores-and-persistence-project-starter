package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Customer extends Named {
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Pet> pets = new ArrayList<>();
    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 500)
    private String notes;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void addPet(Pet... pets) {
        getPets().addAll(new ArrayList<>(Arrays.asList(pets)));
    }

    void removePet(Pet pet) {
        getPets().remove(pet);
        pet.setOwner(null);
    }


}
