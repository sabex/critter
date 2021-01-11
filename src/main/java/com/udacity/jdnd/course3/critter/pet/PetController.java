/* (C)2021 */
package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** Handles web requests related to Pets. */
@RestController
@RequestMapping("/pet")
public class PetController {

  @Autowired private CustomerService customerService;
  @Autowired private PetService petService;

  @Autowired private ModelMapper modelMapper;

  @PostMapping
  public PetDTO savePet(@RequestBody PetDTO petDTO) {
      Customer owner = customerService.get(petDTO.getOwnerId());
      Pet newPet = toEntity(petDTO);
      newPet.setOwner(owner);
      PetDTO dto = toDto(petService.save(newPet));
      customerService.addPet(owner, newPet);
    // add pet to owner
      return dto;
  }

  @GetMapping("/{petId}")
  public PetDTO getPet(@PathVariable long petId) {
    return toDto(petService.get(petId));
  }

  @GetMapping
  public List<PetDTO> getPets() {
    return petService.findAll().stream().map(this::toDto).collect(Collectors.toList());
  }

  @GetMapping("/owner/{ownerId}")
  public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    return petService.findAllByOwnerId(ownerId).stream().map(this::toDto).collect(Collectors.toList());
  }

  private Pet toEntity(PetDTO petDTO) {
    Pet pet = modelMapper.map(petDTO, Pet.class);
    return pet;
  }

  private PetDTO toDto(Pet pet) {
    PetDTO petDto = modelMapper.map(pet, PetDTO.class);
    return petDto;
  }
}
