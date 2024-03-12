package factories;

import constants.StatusConstants;
import models.CategoryDto;
import models.PetDto;
import models.TagDto;

import java.util.List;

public class PetFactory {
    public PetDto getBasicPetWithValidData() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(500)
                .name("test_category")
                .build();

        TagDto tagDto = TagDto.builder()
                .id(500)
                .name("test_tag")
                .build();

        PetDto petDto = PetDto.builder()
                .id(123456789)
                .name("MEGA_PET")
                .tags(List.of(tagDto))
                .category(categoryDto)
                .status(StatusConstants.AVAILABLE)
                .build();

        return petDto;
    }
}
