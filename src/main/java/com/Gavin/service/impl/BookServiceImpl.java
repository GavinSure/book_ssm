package com.Gavin.service.impl;

import com.Gavin.common.PageInfo;
import com.Gavin.entity.Book;
import com.Gavin.entity.BookExample;
import com.Gavin.entity.BookType;
import com.Gavin.entity.BookTypeExample;
import com.Gavin.mapper.BookMapper;
import com.Gavin.mapper.BookTypeMapper;
import com.Gavin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookTypeMapper bookTypeMapper;

    @Override
    public List<Book> findAllBook() {
        return bookMapper.selectByExample(null);
    }

    @Override
    public PageInfo<Book> findBookByPage(int page, int pagesize, String search, int firstTypeId, int secondTypeId) {
        BookExample bookExample=new BookExample();
        if (!search.isEmpty()){
            bookExample.createCriteria().andBookNameLike("%"+search+"%");
        }
        if (secondTypeId!=-1)
            bookExample.createCriteria().andSecondTypeIdEqualTo(secondTypeId);
        if (firstTypeId!=-1){           //分类不为空
            bookExample.createCriteria().andFirstTypeIdEqualTo(firstTypeId);
        }
        int total=bookMapper.countByExample(bookExample);
        bookExample.setOffset((page-1)*pagesize);
        bookExample.setLimit(pagesize);
        List<Book> books = bookMapper.selectByExample(bookExample);
        //多表查询替换一级二级目录
        for(Book book:books){
            book.setFirstTypeName(bookTypeMapper.selectByPrimaryKey(book.getFirstTypeId()).getTypeName());
            book.setSecondTypeName(bookTypeMapper.selectByPrimaryKey(book.getSecondTypeId()).getTypeName());
        }
        return PageInfo.getInstance(total,books);
    }

    @Override
    public List<BookType> findFirstType() {
        BookTypeExample bookTypeExample=new BookTypeExample();
        bookTypeExample.createCriteria().andParentIdEqualTo(0);
        List<BookType> bookTypes=bookTypeMapper.selectByExample(bookTypeExample);
        return bookTypes;
    }

    @Override
    public List<BookType> findSecondType(int parentId) {
        BookTypeExample bookTypeExample=new BookTypeExample();
        bookTypeExample.createCriteria().andParentIdNotEqualTo(0).andParentIdEqualTo(parentId);
        List<BookType> bookTypes=bookTypeMapper.selectByExample(bookTypeExample);
        return bookTypes;
    }

    @Override
    public boolean addOrUpdateBook(Book book) {
         bookMapper.insert(book);
         return true;
    }


}
