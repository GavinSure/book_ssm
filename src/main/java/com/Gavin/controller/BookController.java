package com.Gavin.controller;



import com.Gavin.common.ServerResponse;
import com.Gavin.entity.Book;
import com.Gavin.common.PageInfo;
import com.Gavin.entity.BookType;
import com.Gavin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController         //=@controller+@responsebody（响应数据全为json）
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping("/findBookByPage")
    public ServerResponse<PageInfo<Book>> findBookByPage(int page, int size,String search,int firstTypeId,int secondTypeId){
        PageInfo<Book> pageInfo = bookService.findBookByPage(page, size,search,firstTypeId,secondTypeId);
        return ServerResponse.success(pageInfo);
    }

    @GetMapping("/findFirstType")
    public ServerResponse<List<BookType>> findFirstType(){
        List<BookType> firstType=bookService.findFirstType();
        return ServerResponse.success(firstType);
    }

    @GetMapping("/findSecondType")
    public ServerResponse<List<BookType>> findSecondType(int parentId){
        List<BookType> secondType=bookService.findSecondType(parentId);
        return ServerResponse.success(secondType);
    }

    @GetMapping("/addOrUpdateBook")
    public ServerResponse<?> addOrUpdateBook(String bookName,String authorName,String firstTypeId,String secondTypeId,String imgUrl,String description){
        Book book=new Book();
        book.setBookName(bookName);
        book.setAuthorName(authorName);
        book.setFirstTypeName(firstTypeId);
        book.setSecondTypeName(secondTypeId);
        book.setImgUrl(imgUrl);
        book.setDescription(description);
        bookService.addOrUpdateBook(book);
        return ServerResponse.success();
    }

    @PostMapping("/imgupload")
    public String imgupload(HttpServletRequest request, MultipartFile image) throws IOException {
        //创建目录
        String superDirectory=request
                .getServletContext().getRealPath("bookImg");
        String curDateStr= LocalDate.now().toString();
        File targetDirectory=new File(superDirectory,curDateStr);
        if (!targetDirectory.exists())
            targetDirectory.mkdirs();
        String targetFileName= UUID.randomUUID().toString().replaceAll("-","");

        //获得用户提交的文件
        String sourceFileName=image.getOriginalFilename();  //获得源文件名称
        String extension= StringUtils.getFilenameExtension(sourceFileName); //获得文件后缀名

        //文件上传
        image.transferTo(new File(targetDirectory,targetFileName+"."+extension));           //文件写入磁盘
        return "http://127.0.0.1:8080/bookImg/"+curDateStr+"/"+targetFileName+"."+extension;
    }
}
