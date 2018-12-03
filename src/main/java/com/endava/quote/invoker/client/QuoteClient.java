package com.endava.quote.invoker.client;

import com.endava.quote.invoker.model.QuoteServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "quoteGenerator", url = "localhost:9999", path = "")
public interface QuoteClient {

    @RequestMapping(method = RequestMethod.GET, value = "/quote")
    QuoteServiceResponse getQuote();
}