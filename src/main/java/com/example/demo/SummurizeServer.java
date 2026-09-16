package com.example.demo;

import com.example.demo.tools.CalculatorTools;
import com.example.demo.tools.CurrencyExchangeTool;
import com.example.demo.tools.WeatherTool;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class SummurizeServer {

    private final CalculatorTools calculatorTool;
    private final WeatherTool weatherTool;
    private final CurrencyExchangeTool currencyExchangeTool;
    private final ChatClient chatClient;

    private final List<Message> history = new ArrayList<>();

    private final String systemPrompt = """
            You are a helpful AI assistant with access to external tools.

            Follow these rules:

            1. For arithmetic calculations, ALWAYS use the calculator tool.
            2. Always use the calculator tool even for trivial calculations.
            3. For current weather, ALWAYS use the currentWeather tool.
            4. For currency conversion or exchange rates, ALWAYS use the convertCurrency tool.
            5. You may call multiple tools when solving a multi-step request.
            6. After receiving tool results, explain the answer naturally.
            7. Never invent current weather or exchange-rate information.
            """;

    public SummurizeServer(
            ChatClient.Builder builder,
            CalculatorTools calculatorTool,
            WeatherTool weatherTool,
            CurrencyExchangeTool currencyExchangeTool) {

        this.chatClient = builder.build();
        this.calculatorTool = calculatorTool;
        this.weatherTool = weatherTool;
        this.currencyExchangeTool = currencyExchangeTool;
    }

    public Flux<String> chat(String msg) {

        history.add(new UserMessage(msg));

        StringBuilder fullResponse = new StringBuilder();

        return chatClient.prompt()
                .system(systemPrompt)
                .messages(history)
                .tools(
                        calculatorTool,
                        weatherTool,
                        currencyExchangeTool
                )
                .stream()
                .content()

                .doOnNext(fullResponse::append)

                .doOnComplete(() -> {
                    history.add(
                            new AssistantMessage(fullResponse.toString())
                    );
                });
    }
}