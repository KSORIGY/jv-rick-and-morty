package mate.academy.rickandmorty.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.client.RickAndMortyClient;
import mate.academy.rickandmorty.dto.CharacterResponseDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final RickAndMortyClient rickAndMortyClient;
    private final CharacterMapper characterMapper;
    private final CharacterRepository characterRepository;

    @PostConstruct
    @Override
    public void convertCharacters() {
        try {
            List<Character> characters = rickAndMortyClient.fetchCharacters().stream()
                    .map(characterMapper::toEntity)
                    .toList();

            characterRepository.saveAll(characters);
        } catch (Exception e) {
            throw new RuntimeException("Can`t get charaters! ", e);
        }
    }

    @Override
    public CharacterResponseDto getRandomCharacter() {
        long count = characterRepository.count();

        if (count == 0) {
            throw new RuntimeException("BD is empty!");
        }

        int randomRowIndex = (int) (Math.random() * count);

        PageRequest pageRequest = PageRequest.of(randomRowIndex, 1);

        Page<Character> characterFromPage = characterRepository.findAll(pageRequest);

        Character character = characterFromPage.getContent().get(0);

        return characterMapper.toDto(character);
    }

    @Override
    public List<CharacterResponseDto> searchByPartName(String partName) {
        return characterRepository.findAllByNameContains(partName).stream()
                .map(characterMapper::toDto)
                .toList();
    }
}
