package com.Gavin.service;


import com.Gavin.common.PageInfo;
import com.Gavin.entity.Book;
import com.Gavin.entity.BookType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface BookService {
    List<Book> findAllBook();

    PageInfo<Book> findBookByPage(int page, int pagesize,String search,int firstTypeId,int secondTypeId);

    List<BookType> findFirstType();

    List<BookType> findSecondType(int parentId);

    boolean addOrUpdateBook(Book book);
}
