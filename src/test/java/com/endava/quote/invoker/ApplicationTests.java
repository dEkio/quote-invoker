package com.endava.quote.invoker;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.endava.quote.invoker.client.QuoteClient;
import com.endava.quote.invoker.model.QuoteServiceResponse;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

    @Rule
    public PactProviderRuleMk2 quoteProvider = new PactProviderRuleMk2(
            "quote-generator", "localhost", 9999, this);

    @Autowired
    private QuoteClient quoteClient;

    @Test
    public void contextLoads() {
    }

    @Pact(consumer = "quote-invoker")
    public RequestResponsePact getQuote(PactDslWithProvider builder) {
        PactDslJsonBody quote = new PactDslJsonBody()
                .stringType("author")
                .stringType("quote")
                .asBody();
        return builder
                .given("quote generator")
                .uponReceiving("get quote")
                .path("/quote")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(quote)
                .toPact();
    }

    @Test
    @PactVerification(fragment = "getQuote")
    public void getPaymentPlansTroughConsumer() {
        QuoteServiceResponse quoteClientQuote = quoteClient.getQuote();
        Assert.assertNotNull(quoteClientQuote.getAuthor());
        Assert.assertNotNull(quoteClientQuote.getQuote());
    }
}