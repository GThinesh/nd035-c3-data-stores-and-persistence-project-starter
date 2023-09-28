package com.udacity.jdnd.course3.critter.pet;

import com.google.common.base.MoreObjects;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.MappingUtil;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return toDto(petService.save(toEntity(petDTO), petDTO.getOwnerId()));
    }

    private PetDTO toDto(Pet pet) {
        PetDTO dto = new PetDTO();
        BeanUtils.copyProperties(pet, dto);
        dto.setOwnerId(pet.getOwner().getId());
        return dto;
    }

    private Pet toEntity(PetDTO pet) {
        Pet dto = new Pet();
        BeanUtils.copyProperties(pet, dto);
        return dto;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return toDto(petService.loadById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return MappingUtil.toList(petService.findAll(), this::toDto);
    }

    @GetMapping("birthdays")
    public List<PetDTO> getBirthdayPets(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return MappingUtil.toList(petService.getBirthdayPets(MoreObjects.firstNonNull(day, LocalDate.now())), this::toDto);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return MappingUtil.toList(petService.findByOwners(ownerId), this::toDto);
    }
}
