package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private EmployeeService employeeService;
    private PetService petService;

    private ScheduleRepository scheduleRepository;
    private ModelMapper modelMapper;

    public ScheduleService(EmployeeService employeeService, PetService petService, ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.petService = petService;
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        return scheduleList
                .stream()
                .map(this::toDTo)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAllSchedulesForEmployee(Long employeeId) {
        Employee employee = employeeService.get(employeeId);
        List<Schedule> scheduleList = scheduleRepository.findByScheduledEmployees(employee);
        return scheduleList
                .stream()
                .map(this::toDTo)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAllSchedulesForPet(Long petId) {
        Pet pet = petService.get(petId);
        List<Schedule> scheduleList = scheduleRepository.findByScheduledPets(pet);
        return scheduleList
                .stream()
                .map(this::toDTo)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAllSchedulesForCustomer(Long customerId) {
        List<Pet> pets = petService.findAllByOwnerId(customerId);
        // for each pet, get all schedules
        List<Schedule> scheduleList = new ArrayList<>();
        for (Pet pet: pets) {
            scheduleList.addAll(scheduleRepository.findByScheduledPets(pet));
        }
        // remove duplicates and convert to DTO
        return  scheduleList.stream().distinct().map(this::toDTo).collect(Collectors.toList());
    }

    @Transactional
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule newSchedule = toEntity(scheduleDTO);
        return toDTo(this.save(newSchedule));
    }

    private ScheduleDTO toDTo(Schedule schedule) {
        ScheduleDTO dto = modelMapper.map(schedule, ScheduleDTO.class);
        dto.setEmployeeIds(schedule.getScheduledEmployees()
                .stream()
                .map(employee -> employee.getId())
                .collect(Collectors.toList()));
        dto.setPetIds(schedule.getScheduledPets()
                .stream()
                .map(pet -> pet.getId())
                .collect(Collectors.toList()));
        return dto;
    }

    private Schedule toEntity(ScheduleDTO dto) {
        Schedule schedule = modelMapper.map(dto, Schedule.class);
        // add other entities
        List<Employee> employeeList = dto.getEmployeeIds()
                .stream()
                .map(empId -> {
                    return employeeService.get(empId);
                })
                .collect(Collectors.toList());
        schedule.setScheduledEmployees(employeeList);
        List<Pet> petList = dto.getPetIds()
                .stream()
                .map(petId -> {
                    return petService.get(petId);
                })
                .collect(Collectors.toList());
        schedule.setScheduledPets(petList);
        // pets
        return schedule;
    }

    @Transactional
    private Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    private Schedule get(Long id) {
        return scheduleRepository.findById(id).get();
    }
}
