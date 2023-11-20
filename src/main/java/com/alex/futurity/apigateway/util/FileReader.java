package com.alex.futurity.apigateway.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Slf4j
@UtilityClass
public class FileReader {
    public static String readFileToString(String path) {
        try {
            byte[] bytes = new ClassPathResource(path).getInputStream().readAllBytes();
            return new String(bytes);
        } catch (IOException e) {
            throw new IllegalStateException(
                    String.format("Error loading \"%s\" file: " + e.getMessage(), path)
            );
        }
    }
}
