package com.example.demo.tools;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CurrencyExchangeTool {

    private final RestClient restClient;

    public CurrencyExchangeTool(RestClient.Builder builder){
        this.restClient=builder
                .baseUrl("https://api.frankfurter.dev")
                .build();
    }

    @Tool(description = " Get the latest exchange rate between two currency.")
    public String getExchange(
            @ToolParam(description = "Source currency code example for USD")
            String from,
            @ToolParam(description="Target Currency code , example for INR")
            String to
    ){
        System.out.println("currency tool called");
        return restClient.get()
                .uri("/v2/rate/{from}/{to}", from, to)
                .retrieve()
                .body(String.class);
    }
}
