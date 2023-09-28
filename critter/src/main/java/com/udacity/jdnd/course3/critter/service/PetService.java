package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repo.CustomerRepository;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repo.PetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }


    public Pet loadById(long id) {
        return petRepository.getOne(id);
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet save(Pet pet, long ownerId) {

        Customer owner = customerRepository.getOne(ownerId);
        pet.setOwner(owner);
        pet = petRepository.save(pet);
        owner.addPet(pet);
        customerRepository.save(owner);
        return pet;

    }


    public List<Pet> findByOwners(long ownerId) {
        return customerRepository.getOne(ownerId).getPets();
    }

    public List<Pet> getBirthdayPets(LocalDate localDate) {
        return petRepository.findBirthdayPets(localDate.getMonthValue(), localDate.getDayOfMonth());
    }
}
