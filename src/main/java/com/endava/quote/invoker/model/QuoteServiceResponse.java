package com.endava.quote.invoker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoteServiceResponse {

    @JsonProperty("author")
    private String author;
    @JsonProperty("quote")
    private String quote;

    public QuoteServiceResponse() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public QuoteServiceResponse author(final String author) {
        this.author = author;
        return this;
    }

    public QuoteServiceResponse quote(final String quote) {
        this.quote = quote;
        return this;
    }


    @Override
    public String toString() {
        return "QuoteServiceResponse{" +
                "author='" + author + '\'' +
                ", quote='" + quote + '\'' +
                '}';
    }
}