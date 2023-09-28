package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repo.CustomerRepository;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repo.EmployeeRepository;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repo.PetRepository;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repo.ScheduleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public ScheduleService(EmployeeRepository employeeRepository, ScheduleRepository scheduleRepository, PetRepository petRepository, CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule save(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        checkNonEmpty(employees,"employees");
        schedule.setEmployees(employees);
        List<Pet> pets = petRepository.findAllById(petIds);
        checkNonEmpty(pets,"pets");
        schedule.setPets(pets);
        checkNonEmpty(schedule.getActivities(), "activities");
        return scheduleRepository.save(schedule);
    }

    private void checkNonEmpty(Collection<?> collection, String name) {
        if (collection == null || collection.isEmpty()) {
            throw new ValidationException(String.format("Attribute/relation %s cannot be empty", name));
        }
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.findByPetsIsContaining(pet);

    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.findByEmployeesIsContaining(employee);

    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        Customer employee = customerRepository.getOne(customerId);
        return scheduleRepository.findByPetsIn(employee.getPets());

    }
}
