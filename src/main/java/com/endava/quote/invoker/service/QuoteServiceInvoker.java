package com.endava.quote.invoker.service;

import com.endava.quote.invoker.client.QuoteClient;
import com.endava.quote.invoker.model.QuoteServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QuoteServiceInvoker {

    public static Logger LOGGER = LoggerFactory.getLogger(QuoteServiceInvoker.class);

    @Value("${quote.author.fraud}")
    private String fraudAuthor;
    private QuoteClient quoteClient;

    @Autowired
    public QuoteServiceInvoker(QuoteClient quoteClient) {
        this.quoteClient = quoteClient;
    }

    @Scheduled(fixedRateString = "${quote.invocation.time}", initialDelayString = "30000")
    protected void getQuote() throws IOException {
        QuoteServiceResponse serviceResponse = quoteClient.getQuote();
        if (serviceResponse.getAuthor().equals(fraudAuthor))
            throw new RuntimeException("Fraud detected!");
        LOGGER.info(serviceResponse.toString());
    }
}