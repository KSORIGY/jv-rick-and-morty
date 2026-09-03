package mate.academy.rickandmorty.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.ExternalApiResponseDto;
import mate.academy.rickandmorty.dto.ExternalCharacterDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RickAndMortyClient {
    private static final String FirstPageUrlInApi = "https://rickandmortyapi.com/api/character";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ExternalCharacterDto> fetchCharacters() throws Exception {
        List<ExternalCharacterDto> allCharactersFromApi = new ArrayList<>();

        String url = FirstPageUrlInApi;

        while (url != null) {
            ExternalApiResponseDto response = fetchPage(url);

            allCharactersFromApi.addAll(response.results());

            url = response.info() != null ? response.info().next() : null;
        }
        return allCharactersFromApi;
    }

    private ExternalApiResponseDto fetchPage(String url) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest, HttpResponse.BodyHandlers.ofString()
            );

            String body = httpResponse.body();
            if (body != null && body.trim().startsWith("error")) {
                return new ExternalApiResponseDto(null, List.of());
            }

            return objectMapper.readValue(httpResponse.body(), ExternalApiResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Can`t fetch characters from URL: " + url, e);
        }
    }

}
