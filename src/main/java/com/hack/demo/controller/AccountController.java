package com.hack.demo.controller;

import com.hack.demo.entity.Hacker;
import com.hack.demo.repository.HackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class AccountController {

    @Autowired
    private HackerRepository hackerRepository;

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String registrationPage(Model model){
        model.addAttribute("hacker", new Hacker());
        return "hacker-register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String hackerRegister(Model model, @Valid Hacker hacker, BindingResult bindingResult){
        //model.addAttribute("hacker", new Hacker());
        if(bindingResult.hasErrors()){
            return "hacker-register";
        }else{

            if( hackerRepository.existsById(hacker.getEmail())){
                model.addAttribute("error", "Email address already exist.");
                return "hacker-register";
            }else {

                hacker.setId(UUID.randomUUID().toString());
                hacker.setRole(Hacker.ROLE_HACKER);

                // email verification then enable the hacker //
                hacker.setEnable(true);
                hackerRepository.save(hacker);

                System.out.println("# Hacker: " + hacker);
                model.addAttribute("name", hacker.getName());
                return "registration_success";
            }
        }
    }


}
