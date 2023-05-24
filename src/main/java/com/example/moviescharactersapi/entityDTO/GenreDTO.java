package com.example.moviescharactersapi.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreDTO {
    private Long id;
    private String image;
    private String name;
}
