package com.hoxy.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String isbn;

    private int price;

    @ManyToOne //한명의 저자는 여러 권을 책을 쓸 수 있고, 책은 하나의 저자와 연관
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne //하나의 책은 하나의 카테고리에 속하며, 카테고리에는 여러 권의 책이 있을 수 있다.
    @JoinColumn(name = "category_id")
    private Category category;

    //하나의 책은 하나의 재고 항목만 가질 수 있음
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Stock stock;



}
