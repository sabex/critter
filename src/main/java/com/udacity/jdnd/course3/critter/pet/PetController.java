/* (C)2021 */
package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** Handles web requests related to Pets. */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired private PetService petService;

    @Autowired private ModelMapper modelMapper;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        // convert dto to entity
        Pet newPet = this.petService.save(convertDtoToEntity(petDTO));
        return convertEntityToDto(newPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }

    private Pet convertDtoToEntity(PetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        return pet;
    }

    private PetDTO convertEntityToDto(Pet pet) {
        PetDTO petDto = modelMapper.map(pet, PetDTO.class);
        return petDto;
    }
}
