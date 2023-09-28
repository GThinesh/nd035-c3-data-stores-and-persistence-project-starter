package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.MappingUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final EmployeeService employeeService;
    private final CustomerService customerService;

    public UserController(EmployeeService employeeService, CustomerService customerService) {
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return mapToCustomerDto(customerService.save(mapToCustomerEntity(customerDTO), customerDTO.getPetIds()));
    }

    private CustomerDTO mapToCustomerDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        dto.setPetIds(MappingUtil.toIds(customer.getPets()));
        return dto;
    }

    private Customer mapToCustomerEntity(CustomerDTO customer) {
        Customer entity = new Customer();
        BeanUtils.copyProperties(customer, entity);
        return entity;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return MappingUtil.toList(customerService.findAll(), this::mapToCustomerDto);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        return mapToCustomerDto(customerService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return mapToEmployeeDTO(employeeService.save(mapToEmployee(employeeDTO)));
    }

    private EmployeeDTO mapToEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    private Employee mapToEmployee(EmployeeDTO employeeDTO) {
        Employee entity = new Employee();
        BeanUtils.copyProperties(employeeDTO, entity);
        return entity;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return mapToEmployeeDTO(employeeService.getById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(employeeId, daysAvailable);
    }

    @PutMapping("/employee/{employeeId}/skills")
    public void addSkill(@RequestBody Set<EmployeeSkill> skills, @PathVariable long employeeId) {
        employeeService.addSkills(employeeId, skills);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return MappingUtil.toList(employeeService.findByCriteria(employeeDTO.getSkills(), employeeDTO.getDate()), this::mapToEmployeeDTO);
    }

}
