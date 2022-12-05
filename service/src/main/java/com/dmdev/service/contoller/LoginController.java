package com.dmdev.service.contoller;

import com.dmdev.service.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(Model model, LoginDto loginDto) {
        model.addAttribute("user", loginDto);
        return "redirect:/start";
    }

    @GetMapping("/start")
    public String start() {
        return "start";
    }
}
