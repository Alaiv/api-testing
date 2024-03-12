package models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class PetDto {
    @EqualsAndHashCode.Include
    private int id;
    private CategoryDto category;
    @EqualsAndHashCode.Include
    private String name;
    private List<String> photoUrls;
    private List<TagDto> tags;
    @EqualsAndHashCode.Include
    private String status;
}
