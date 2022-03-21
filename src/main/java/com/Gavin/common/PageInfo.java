package com.Gavin.common;

import com.Gavin.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author: Gavin
 * @description:
 * @className: pageInfo
 * @date: 2022/3/8 16:59
 * @version:0.1
 * @since: jdk14.0
 */

@Setter
@Getter
public class PageInfo<T> {
    private long total;
    private List<T> list;

    public static <T> PageInfo<T> getInstance(long total,List<T> list){
        PageInfo<T> pageInfo=new PageInfo<>();
        pageInfo.setTotal(total);
        pageInfo.setList(list);
        return pageInfo;
    }
}
