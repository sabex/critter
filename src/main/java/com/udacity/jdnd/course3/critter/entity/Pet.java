/* (C)2021 */
package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

@Entity(name = "pet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id @GeneratedValue private long id;

    private PetType type;

    @Nationalized private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Customer ownerId;

    @ManyToMany(mappedBy = "scheduledPets")
    private Set<Schedule> schedules;
}
