package com.financesnap.controller;

import com.financesnap.service.OcrService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private static final Path UPLOAD_DIR = Paths.get("uploads").toAbsolutePath().normalize();
    private final OcrService ocrService;

    public UploadController(OcrService ocrService) {
        this.ocrService = ocrService;
    }

    @PostMapping
    public UploadResponse upload(@RequestParam("file") MultipartFile file) throws IOException {
        Files.createDirectories(UPLOAD_DIR);

        // Nome único para evitar conflitos e bloquear path traversal
        String filename = UUID.randomUUID() + "_" + Paths.get(file.getOriginalFilename()).getFileName();
        Path destination = UPLOAD_DIR.resolve(filename);
        file.transferTo(destination);

        // Extrai o texto da nota fiscal via OCR
        String extractedText = ocrService.extractTextFromImage(destination.toString());

        return new UploadResponse(destination.toString(), extractedText);
    }

    public record UploadResponse(String path, String extractedText) {}
}