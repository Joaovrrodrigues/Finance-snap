package com.financesnap.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class OcrService {

    public String extractTextFromImage(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        ByteString imgBytes = ByteString.copyFrom(imageBytes);

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feature)
                .setImage(img)
                .build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(List.of(request));
            AnnotateImageResponse res = response.getResponsesList().get(0);

            if (res.hasError()) {
                throw new RuntimeException("Erro OCR: " + res.getError().getMessage());
            }

            return res.getTextAnnotationsList().isEmpty()
                    ? ""
                    : res.getTextAnnotationsList().get(0).getDescription();
        }
    }
}