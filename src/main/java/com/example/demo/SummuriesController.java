package com.example.demo;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SummuriesController {

    private SummurizeServer summurizeServer;

    public SummuriesController(SummurizeServer s){
        this.summurizeServer=s;
    }


    @PostMapping("/chat")
    public Flux<String> chat(@RequestBody String message){
        return summurizeServer.chat(message);
    }
}
