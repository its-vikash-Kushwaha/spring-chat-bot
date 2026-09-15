package com.example.demo;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SummuriesController {

    private SummurizeServer summurizeServer;

    public SummuriesController(SummurizeServer s){
        this.summurizeServer=s;
    }


    @PostMapping("/chat")
    public String chat(@RequestBody String message){
        return summurizeServer.chat(message);
    }
}
