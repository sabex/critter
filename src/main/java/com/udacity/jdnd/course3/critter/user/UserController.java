package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer newCustomer = toEntity(customerDTO);
        if (null == newCustomer.getPets()) {
            newCustomer.setPets(new ArrayList<Pet>());
        }
        return toDto(customerService.save(newCustomer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee empToSave = toEntity(employeeDTO);
        // check for null relationships and add if needed
        if (null == empToSave.getSchedules()) {
            empToSave.setSchedules(new HashSet<Schedule>());
        }
        if (null == empToSave.getSkills()) {
            empToSave.setSkills(new HashSet<EmployeeSkill>());
        }
        if (null == empToSave.getDaysAvailable()) {
            empToSave.setDaysAvailable(new HashSet<DayOfWeek>());
        }
        Employee employee = employeeService.save(empToSave);
        return toDto(employee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return toDto(employeeService.get(employeeId));
    }

    // sets schedule
    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.get(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.save(employee);
        return;
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private EmployeeDTO toDto (Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    private Employee toEntity (EmployeeDTO employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }
    private CustomerDTO toDto (Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    private Customer toEntity (CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}
