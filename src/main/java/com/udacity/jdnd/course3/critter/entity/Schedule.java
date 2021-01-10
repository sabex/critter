/* (C)2021 */
package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id @GeneratedValue private long id;

    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name = "employee_schedule",
            joinColumns = { @JoinColumn(name = "employee_id")},
            inverseJoinColumns = { @JoinColumn(name = "schedule_id")}
    )
    private List<Employee> scheduledEmployees;

    @ManyToMany
    @JoinTable(
            name = "employee_schedule",
            joinColumns = { @JoinColumn(name = "pet_id")},
            inverseJoinColumns = { @JoinColumn(name = "schedule_id")})
    private List<Pet> scheduledPets;

    @ElementCollection private Set<EmployeeSkill> activities;
}
