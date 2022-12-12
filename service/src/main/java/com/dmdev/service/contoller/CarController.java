package com.dmdev.service.contoller;

import com.dmdev.service.dto.CarCreateEditDto;
import com.dmdev.service.dto.CarReadDto;
import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.dto.PageResponse;
import com.dmdev.service.entity.Status;
import com.dmdev.service.entity.TypeFuel;
import com.dmdev.service.entity.TypeTransmission;
import com.dmdev.service.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public String findAll(Model model, FilterCar filter, Pageable pageable) {
        Page<CarReadDto> all = carService.findAll(filter, pageable);
        model.addAttribute("cars", PageResponse.of(all));
        model.addAttribute("transmissions", TypeTransmission.values());
        model.addAttribute("types", TypeFuel.values());
        model.addAttribute("filter", filter);
        return "car/cars";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return carService.findById(id)
                .map(car -> {
                    model.addAttribute("car", car);
                    model.addAttribute("transmissions", TypeTransmission.values());
                    model.addAttribute("types", TypeFuel.values());
                    model.addAttribute("statuses", Status.values());
                    return "car/car";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated CarCreateEditDto carDto) {
        return carService.update(id, carDto)
                .map(it -> "redirect:/cars/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(Model model, CarCreateEditDto car) {
        model.addAttribute("car", car);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("transmissions", TypeTransmission.values());
        model.addAttribute("types", TypeFuel.values());
        return "car/create";
    }

    @PostMapping
    public String create(@Validated CarCreateEditDto carDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("car", carDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/cars/create";
        }
        return "redirect:/cars/" + carService.create(carDto).getId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!carService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/cars";
    }
}
