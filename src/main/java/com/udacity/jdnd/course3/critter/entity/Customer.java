/* (C)2021 */
package com.udacity.jdnd.course3.critter.entity;

import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

@Entity(name = "Customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id @GeneratedValue private long id;

    @Nationalized private String name;

    private String phoneNumber;
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerId")
    private List<Pet> pets;
}
