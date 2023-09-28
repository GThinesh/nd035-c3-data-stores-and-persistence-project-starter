package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repo.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getById(long id) {
        return employeeRepository.getOne(id);
    }

    public void setAvailability(long employee, Set<DayOfWeek> dayOfWeeks) {
        Employee one = employeeRepository.getOne(employee);
        one.setDaysAvailable(dayOfWeeks);
        employeeRepository.save(one);
    } public void addSkills(long employee, Set<EmployeeSkill> skills){
        Employee one = employeeRepository.getOne(employee);
        one.getSkills().addAll(skills);
        employeeRepository.save(one);
    }

    public List<Employee> findByCriteria(Set<EmployeeSkill> skills, LocalDate date) {
        return employeeRepository.findByDaysAvailableContaining(date.getDayOfWeek())
                .stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
