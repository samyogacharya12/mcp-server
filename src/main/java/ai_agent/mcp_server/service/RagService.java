package ai_agent.mcp_server.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    private MongoChatHistoryService mongoChatHistoryService;

    public RagService(ChatClient chatClient,
                      VectorStore vectorStore,
                      MongoChatHistoryService mongoChatHistoryService) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
        this.mongoChatHistoryService=mongoChatHistoryService;
    }

    public String ask(String conversationId, String question) {

        mongoChatHistoryService.saveUserMessage(conversationId, question);

        String documentContext = vectorStore.similaritySearch(
                        SearchRequest.builder()
                                .query(question)
                                .topK(5)
                                .similarityThreshold(0.30)
                                .build()
                )
                .stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        String answer = chatClient.prompt()
                .system("""
                You are a document assistant.

                Rules:
                - Answer only from the document context.
                - If not found, say:
                  "I could not find this information in the document."
                """)
                .user("""
                Document context:
                %s

                Question:
                %s
                """.formatted(documentContext, question))
                .call()
                .content();

        mongoChatHistoryService.saveAssistantMessage(conversationId, answer);

        return answer;
    }
}
