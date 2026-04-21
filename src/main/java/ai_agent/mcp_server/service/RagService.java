package ai_agent.mcp_server.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

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

        String historyText = mongoChatHistoryService.getRecentHistoryAsText(conversationId, 6);

        QuestionAnswerAdvisor advisor = QuestionAnswerAdvisor.builder(vectorStore)
            .searchRequest(SearchRequest.builder()
                .topK(6)
                .similarityThreshold(0.65)
                .build())
            .build();

        String answer = chatClient.prompt()
            .system("""
                You are a document question-answering assistant.

                Rules:
                - Answer ONLY from retrieved document context.
                - If the answer is not in the context, say:
                  "I could not find this information in the document."
                - Do NOT use outside knowledge.
                - Do NOT guess.
                """)
            .user("""
                Recent conversation:
                %s

                Current question:
                %s
                """.formatted(historyText, question))
            .advisors(advisor)
            .call()
            .content();

        mongoChatHistoryService.saveAssistantMessage(conversationId, answer);

        return answer;
    }
}
