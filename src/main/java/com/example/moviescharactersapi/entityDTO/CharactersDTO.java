package com.example.moviescharactersapi.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharactersDTO {
    private Long id;
    private String image;
    private String name;
    private Integer age;
    private Integer weight;
    private String history;
}
