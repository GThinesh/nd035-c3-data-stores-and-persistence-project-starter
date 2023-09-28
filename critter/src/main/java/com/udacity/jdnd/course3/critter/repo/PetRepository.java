package com.udacity.jdnd.course3.critter.repo;

import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("select p from Pet p where month(p.birthDate)=:month and day(p.birthDate)=:day")
    List<Pet> findBirthdayPets(int month, int day);
}
