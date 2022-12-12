package com.dmdev.service.contoller;

import com.dmdev.service.dto.RequestCreateEditDto;
import com.dmdev.service.entity.RequestStatus;
import com.dmdev.service.entity.Status;
import com.dmdev.service.exception.ServiceException;
import com.dmdev.service.service.CarService;
import com.dmdev.service.service.RequestService;
import com.dmdev.service.service.TariffService;
import com.dmdev.service.service.UserService;
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

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;
    private final CarService carService;
    private final TariffService tariffService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("requests", requestService.findAll());
        return "request/requests";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return requestService.findById(id)
                .map(request -> {
                    model.addAttribute("request", request);
                    model.addAttribute("statuses", RequestStatus.values());
                    model.addAttribute("users", userService.findAll());
                    model.addAttribute("cars", carService.findAll());
                    model.addAttribute("tariffs", tariffService.findAll());
                    model.addAttribute("user", userService.findById(request.getUser().getId()).get());
                    return "request/request";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(Model model, RequestCreateEditDto createEditDto) {
        model.addAttribute("request", createEditDto);
        model.addAttribute("statuses", RequestStatus.values());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("cars", carService.findAll().stream()
                .filter(car -> Status.FREE.equals(car.getStatus())).collect(toList()));
        model.addAttribute("tariffs", tariffService.findAll());
        return "request/create";
    }

    @PostMapping
    public String create(@Validated RequestCreateEditDto createEditDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("request", createEditDto);
                redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
                return "redirect:/requests/create";
            }
            return "redirect:/requests/" + requestService.create(createEditDto).getId();
        } catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("exception", ex);
            return "redirect:/requests/create";
        }
    }

    @PostMapping(value = "/{id}/update", params = "action=update")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated RequestCreateEditDto editDto) {
        return requestService.update(id, editDto)
                .map(it -> "redirect:/requests/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping(value = "/{id}/update", params = "action=close")
    public String close(@PathVariable("id") Long id, @ModelAttribute @Validated RequestCreateEditDto editDto) {
        requestService.close(id, editDto);
        return "redirect:/requests/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!requestService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/requests";
    }

    @GetMapping("/{id}/calculate")
    public String calculate(@PathVariable("id") Long id, Model model) {
        return requestService.calculate(id)
                .map(request -> {
                    model.addAttribute("request", requestService.findById(id).get());
                    model.addAttribute("amount", request);
                    return "request/calculate";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
