package com.example.demo;


import com.example.demo.tools.CalculatorTools;
import com.example.demo.tools.CurrencyExchangeTool;
import com.example.demo.tools.WeatherTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SummurizeServer {

    String system_prompt= """
               You are a helpful AI assistant with access the external tools.
               
               Follows these rules :
             1. For arithmetic calculations, ALWAYS use the calculator tool.
                                                                                       2. Always use calculator tool for even trivial calculation
                                                                                       3. For current weather, ALWAYS use the currentWeather tool.
                                                                                       4. For currency conversion or exchange rates, ALWAYS use the convertCurrency tool.
                                                                                       5. You may call multiple tools when solving a multi-step request.
                                                                                       6. After receiving tool results, explain the answer naturally.
                                                                                       7. Never invent current weather or exchange-rate information.
                """;
    private CalculatorTools calculatorTool;
    private WeatherTool weatherTool;
    private CurrencyExchangeTool currencyExchangeTool;

    private ChatClient chatClient;

    private final List<Message> history = new ArrayList<>();

    public SummurizeServer(ChatClient.Builder builder,
                       CalculatorTools calculatorTool,
                       WeatherTool weatherTool,
                       CurrencyExchangeTool currencyExchangeTool) {
        this.chatClient = builder.build();
        this.calculatorTool = calculatorTool;
        this.weatherTool = weatherTool;
        this.currencyExchangeTool = currencyExchangeTool;
    }

    public String chat(String msg){



        history.add(new UserMessage(msg));

        String out=chatClient.prompt()
                .messages(history)
                .tools(calculatorTool, weatherTool, currencyExchangeTool)
                .system(system_prompt)
                .call()
                .content();
        history.add(new AssistantMessage(out));
        return out;
    }
}
