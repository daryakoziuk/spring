package com.dmdev.service.contoller;

import com.dmdev.service.dto.TariffCreateEditDto;
import com.dmdev.service.entity.TariffType;
import com.dmdev.service.service.TariffService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/tariffs")
public class TariffController {

    private final TariffService tariffService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("tariffs", tariffService.findAll());
        return "tariff/tariffs";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return tariffService.findById(id)
                .map(tariff -> {
                    model.addAttribute("tariff", tariff);
                    model.addAttribute("types", TariffType.values());
                    return "tariff/tariff";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(Model model, TariffCreateEditDto tariffCreateEditDto) {
        model.addAttribute("tariff", tariffCreateEditDto);
        model.addAttribute("types", TariffType.values());
        return "tariff/create";
    }

    @PostMapping
    public String create(@Validated TariffCreateEditDto tariffCreateEditDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("tariff", tariffCreateEditDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/tariffs/create";
        }
        return "redirect:/tariffs/" + tariffService.create(tariffCreateEditDto).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute @Validated TariffCreateEditDto createEditDto) {
        return tariffService.update(id, createEditDto)
                .map(it -> "redirect:/tariffs/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (tariffService.delete(id)) {
            return "redirect:/tariffs";
        } else {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }
}
