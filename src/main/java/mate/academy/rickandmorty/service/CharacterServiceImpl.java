package mate.academy.rickandmorty.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.client.RickAndMortyClient;
import mate.academy.rickandmorty.dto.CharacterResponseDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final Random random = new Random();
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
            throw new RuntimeException("Can`t get characters! ", e);
        }
    }

    @Override
    public CharacterResponseDto getRandomCharacter() {
        long count = characterRepository.count();

        long randomId = random.nextLong(count) + 1;

        Character character = characterRepository.findById(randomId)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + randomId));

        return characterMapper.toDto(character);
    }

    @Override
    public List<CharacterResponseDto> searchByPartName(String partName) {
        return characterRepository.findAllByNameContains(partName).stream()
                .map(characterMapper::toDto)
                .toList();
    }

}
