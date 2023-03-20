package com.xzw.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBookstoreDto {

    private Long userId;

    private String bookName;

    private String img;

    private Double price;

    private Integer books;

    private Integer count;

    private Long category;
}
