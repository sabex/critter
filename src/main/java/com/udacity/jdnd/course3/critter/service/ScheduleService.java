/* (C)2021 */
package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

  private EmployeeService employeeService;
  private PetService petService;

  private ScheduleRepository scheduleRepository;
  private ModelMapper modelMapper;

  public ScheduleService(
      EmployeeService employeeService,
      PetService petService,
      ScheduleRepository scheduleRepository,
      ModelMapper modelMapper) {
    this.employeeService = employeeService;
    this.petService = petService;
    this.scheduleRepository = scheduleRepository;
    this.modelMapper = modelMapper;
  }

  public List<Schedule> getAllSchedules() {
    return scheduleRepository.findAll();
  }

  public List<Schedule> getAllSchedulesForEmployee(Employee employee) {
    return scheduleRepository.findByScheduledEmployees(employee);
  }

  public List<Schedule> getAllSchedulesForPet(Pet pet) {
    return scheduleRepository.findByScheduledPets(pet);
  }

  public Schedule createSchedule(Schedule schedule) {
    return this.save(schedule);
  }

  @Transactional
  private Schedule save(Schedule schedule) {
    return scheduleRepository.save(schedule);
  }
}
