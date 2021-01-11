/* (C)2021 */
package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PetService {

  private PetRepository petRepository;

  public PetService(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  public Pet save(Pet pet) {
    return petRepository.save(pet);
  }

  public Pet get(Long id) {
    return petRepository.findById(id).get();
  }

  public List<Pet> findAll() {
    return petRepository.findAll();
  }

  public List<Pet> findAllByOwnerId(Long id) {
      return petRepository.findAllByOwnerId(id);
  }
}
