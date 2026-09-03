package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterResponseDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Character management", description = "Ednpoints for managing characters")
@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @Operation(summary = "Get random character", description = "Get random character from DB")
    @GetMapping("/random")
    public CharacterResponseDto getRandomCharacter() {
        return characterService.getRandomCharacter();
    }

    @Operation(summary = "Search characters by parameters", description = "Returns list of "
            + "characters by part of their names")
    @GetMapping("/search")
    public List<CharacterResponseDto> getCharactersByPartName(@RequestParam("name")
                                                                  String partName) {
        return characterService.searchByPartName(partName);
    }
}
