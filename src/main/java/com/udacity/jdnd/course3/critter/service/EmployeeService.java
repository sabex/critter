/* (C)2021 */
package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

  private EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Transactional
  public Employee save(Employee employee) {
    return employeeRepository.save(employee);
  }

  public Employee get(Long employeeId) {
    return employeeRepository.findById(employeeId).get();
  }

  public List<Employee> getEmployeesWithSkillsAndAvailability(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
    List<Employee> employees = employeeRepository.findDistinctBySkillsInAndDaysAvailable(skills, dayOfWeek);
    // need to ensure that each employee has ALL the skills because the JPA will return if employee has at least one skill
    return employees.stream().filter( e -> e.getSkills().containsAll(skills)).collect(Collectors.toList());
  }
}
