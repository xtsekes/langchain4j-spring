package com.example.langchain.service;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.transformer.HtmlTextExtractor;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.*;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.util.ResourceUtils.toURL;


@Service
public class EmbeddingService {

    private final EmbeddingStoreIngestor embeddingStoreIngestor;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    public EmbeddingService(
            EmbeddingStoreIngestor embeddingStoreIngestor,
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    public void ingestInfo(String prompt) throws MalformedURLException {
        // Load the HTML document from the URL
        URL url = toURL(prompt);
        Document htmlDocument = UrlDocumentLoader.load(url, new TextDocumentParser());
        // Only get the paragraphs from the HTML document
        HtmlTextExtractor htmlTextExtractor = new HtmlTextExtractor("p", null, true);
        Document document = htmlTextExtractor.transform(htmlDocument);

        embeddingStoreIngestor.ingest(document);
    }

    public EmbeddingSearchResult<TextSegment> retrieveInfo(String prompt) {

        String query = "You are %s. What is your full name, nickname, nationality, birthdate and official peak rating?".formatted(prompt);
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(embeddingModel.embed(query).content())
                .maxResults(3)
                .minScore(0.7)
                .build();

        return embeddingStore.search(embeddingSearchRequest);
    }
}