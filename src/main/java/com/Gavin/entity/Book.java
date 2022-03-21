package com.Gavin.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Book {
    private Integer bookId;

    private String bookName;

    private String authorName;

    private Integer firstTypeId;

    private Integer secondTypeId;

    private String description;

    private String imgUrl;
    private String firstTypeName;
    private String secondTypeName;


}