/* (C)2021 */
package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** Handles web requests related to Schedules. */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

  @Autowired private ScheduleService scheduleService;

  @Autowired private ModelMapper modelMapper;

  @Autowired PetService petService;

  @Autowired EmployeeService employeeService;

  @PostMapping
  public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
    return toDto(scheduleService.createSchedule(toEntity(scheduleDTO)));
  }

  @GetMapping
  public List<ScheduleDTO> getAllSchedules() {
    List<Schedule> scheduleList = scheduleService.getAllSchedules();
    return scheduleList.stream().map(this::toDto).collect(Collectors.toList());
  }

  @GetMapping("/employee/{employeeId}")
  public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
    Employee employee = employeeService.get(employeeId);
    List<Schedule> scheduleList = scheduleService.getAllSchedulesForEmployee(employee);
    return scheduleList.stream().map(this::toDto).collect(Collectors.toList());
  }

  @GetMapping("/pet/{petId}")
  public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
    Pet pet = petService.get(petId);
    List<Schedule> scheduleList = scheduleService.getAllSchedulesForPet(pet);
    return scheduleList.stream().map(this::toDto).collect(Collectors.toList());
  }

  @GetMapping("/customer/{customerId}")
  public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
    List<Pet> pets = petService.findAllByOwnerId(customerId);
    // for each pet, get all schedules
    List<Schedule> scheduleList = new ArrayList<>();
    for (Pet pet : pets) {
      scheduleList.addAll(scheduleService.getAllSchedulesForPet(pet));
    }
    return scheduleList.stream().distinct().map(this::toDto).collect(Collectors.toList());
  }

  /**
   * converts Entity to Dto
   *
   * @param schedule
   * @return
   */
  private ScheduleDTO toDto(Schedule schedule) {
    ScheduleDTO dto = modelMapper.map(schedule, ScheduleDTO.class);
    dto.setEmployeeIds(
        schedule.getScheduledEmployees().stream()
            .map(employee -> employee.getId())
            .collect(Collectors.toList()));
    dto.setPetIds(
        schedule.getScheduledPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
    return dto;
  }

  /**
   * converts dto into Entity
   *
   * @param dto
   * @return
   */
  private Schedule toEntity(ScheduleDTO dto) {
    Schedule schedule = modelMapper.map(dto, Schedule.class);
    // add other entities
    List<Employee> employeeList =
        dto.getEmployeeIds().stream()
            .map(
                empId -> {
                  return employeeService.get(empId);
                })
            .collect(Collectors.toList());
    schedule.setScheduledEmployees(employeeList);
    List<Pet> petList =
        dto.getPetIds().stream()
            .map(
                petId -> {
                  return petService.get(petId);
                })
            .collect(Collectors.toList());
    schedule.setScheduledPets(petList);
    return schedule;
  }
}
