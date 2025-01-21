package br.com.lindomarjr.literalura.service.api;

import br.com.lindomarjr.literalura.service.api.dto.BookData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Service
public class GutendexAPIService implements IBookAPI {
    private final String BASE_URL = "https://gutendex.com/books/";

    @Override
    public BookData execute(HashMap<String, String> params) {
        ObjectMapper objectMapper = new ObjectMapper();

        String searchParam = "?search=" + params.get("author").replaceAll(" ", "%20");

        URI uri = URI.create(BASE_URL + searchParam);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), BookData.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BookData findSingleBook(HashMap<String, String> params) {
        ObjectMapper objectMapper = new ObjectMapper();

        String searchParam = "?search=" + params.get("bookName").replaceAll(" ", "%20");

        URI uri = URI.create(BASE_URL + searchParam);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), BookData.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
