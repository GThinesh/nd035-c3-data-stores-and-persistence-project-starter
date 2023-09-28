package com.udacity.jdnd.course3.critter.repo;

import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * The result has to be filtered to match for sills

     */
    List<Employee> findByDaysAvailableContaining(DayOfWeek dayOfWeek);
}
