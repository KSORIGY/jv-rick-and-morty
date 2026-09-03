package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.CharacterResponseDto;

public interface CharacterService {
    void convertCharacters();

    CharacterResponseDto getRandomCharacter();

    List<CharacterResponseDto> searchByPartName(String partName);
}
