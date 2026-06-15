package ai_agent.mcp_server.service;

import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;

import java.util.List;

@Service
public class DocumentService {



    private final VectorStore vectorStore;


    public DocumentService(
            VectorStore vectorStore
    ){
        this.vectorStore = vectorStore;
    }



    public String searchDocument(
            String question
    ){


        List<Document> documents =
                vectorStore.similaritySearch(

                        SearchRequest
                                .builder()
                                .query(question)
                                .topK(5)
                                .build()

                );


        return documents
                .stream()
                .map(Document::getText)
                .reduce(
                        "",
                        (a,b) -> a + "\n" + b
                );

    }
}
