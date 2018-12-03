package com.endava.quote.invoker.service;

import com.endava.quote.invoker.client.QuoteClient;
import com.endava.quote.invoker.model.QuoteServiceResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceInvokerTest {

    @Mock
    private QuoteClient quoteCaller;

    @InjectMocks
    private QuoteServiceInvoker quoteServiceInvoker;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp(){
    }

    @Test
    public void shouldThrowExceptionWhenAuthorIsFraudulent() throws IOException {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Fraud detected!");
        Mockito.when(quoteCaller.getQuote()).thenReturn(new QuoteServiceResponse().author("Dejan"));
        ReflectionTestUtils.setField(quoteServiceInvoker, "fraudAuthor", "Dejan");
        quoteServiceInvoker.getQuote();
    }

    @Test
    public void shouldLog() throws IOException {
        Mockito.when(quoteCaller.getQuote()).thenReturn(new QuoteServiceResponse().author("Dejan"));
        ReflectionTestUtils.setField(quoteServiceInvoker, "fraudAuthor", "Not Dejan");
        quoteServiceInvoker.getQuote();
    }

}