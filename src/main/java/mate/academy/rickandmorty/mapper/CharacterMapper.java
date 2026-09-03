package mate.academy.rickandmorty.mapper;

import mate.academy.rickandmorty.dto.CharacterResponseDto;
import mate.academy.rickandmorty.dto.ExternalCharacterDto;
import mate.academy.rickandmorty.model.Character;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "externalId")
    Character toEntity(ExternalCharacterDto externalCharacterDto);

    CharacterResponseDto toDto(Character character);
}
