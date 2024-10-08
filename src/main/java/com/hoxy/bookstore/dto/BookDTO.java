package com.hoxy.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private int price;
    private AuthorDTO author;  // 변경
    private CategoryDTO category;  // 변경
    private StockDTO stock;  // 변경
}
