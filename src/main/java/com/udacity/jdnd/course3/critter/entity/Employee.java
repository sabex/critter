/* (C)2021 */
package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

@Entity(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id @GeneratedValue private long id;

    @Nationalized private String name;

    @ElementCollection private Set<EmployeeSkill> skills;

    @ElementCollection private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "scheduledEmployees")
    private Set<Schedule> schedules;
}
