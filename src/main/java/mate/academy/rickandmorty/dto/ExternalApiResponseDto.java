package mate.academy.rickandmorty.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalApiResponseDto(Info info, List<ExternalCharacterDto> results) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Info(String next) {

    }
}
