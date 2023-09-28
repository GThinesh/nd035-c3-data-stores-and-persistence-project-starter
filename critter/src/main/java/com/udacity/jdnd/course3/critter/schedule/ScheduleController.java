package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Named;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.service.MappingUtil.toIds;
import static com.udacity.jdnd.course3.critter.service.MappingUtil.toList;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return toDto(scheduleService.save(toEntity(scheduleDTO), scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
    }

    private ScheduleDTO toDto(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, dto);
        dto.setEmployeeIds(toIds(schedule.getEmployees()));
        dto.setPetIds(toIds(schedule.getPets()));
        return dto;
    }

    private Schedule toEntity(ScheduleDTO dto) {
        Schedule entity = new Schedule();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return toDtos(scheduleService.getAll());
    }

    private List<ScheduleDTO> toDtos(List<Schedule> schedules) {
        return toList(schedules, this::toDto);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return toDtos(scheduleService.getScheduleForPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return toDtos(scheduleService.getScheduleForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return toDtos(scheduleService.getScheduleForCustomer(customerId));
    }
}
