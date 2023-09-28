package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repo.CustomerRepository;
import com.udacity.jdnd.course3.critter.repo.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(long petId) {
        return petRepository.getOne(petId).getOwner();
    }

    public Customer save(Customer customer, List<Long> pets) {
        Customer saved = customerRepository.save(customer);
        if (pets != null && !pets.isEmpty()) {
            saved.addPet(petRepository.findAllById(pets).toArray(new Pet[0]));
        }
        return saved;
    }

}
