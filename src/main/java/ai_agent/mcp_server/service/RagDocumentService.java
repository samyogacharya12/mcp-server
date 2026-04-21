package ai_agent.mcp_server.service;

import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class RagDocumentService {

    private final VectorStore vectorStore;

    public RagDocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public String ingestDocument(String filePath) {
        try {
            Path path = Path.of(filePath).normalize();

            if (!Files.exists(path)) {
                return "File not found: " + filePath;
            }

            if (!Files.isRegularFile(path)) {
                return "Path is not a regular file: " + filePath;
            }

            TikaDocumentReader reader = new TikaDocumentReader(new FileSystemResource(path));
            List<Document> documents = reader.read();

            TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(800)
                .withMinChunkSizeChars(350)
                .withMinChunkLengthToEmbed(10)
                .withMaxNumChunks(1000)
                .withKeepSeparator(true)
                .build();

            List<Document> chunks = splitter.apply(documents);

            for (Document chunk : chunks) {
                chunk.getMetadata().put("source", path.getFileName().toString());
                chunk.getMetadata().put("path", path.toString());
            }

            vectorStore.add(chunks);

            return "Document ingested successfully. Chunks added: " + chunks.size();
        } catch (Exception e) {
            return "Failed to ingest document: " + e.getMessage();
        }
    }
}
