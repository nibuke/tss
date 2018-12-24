package com.baizhi.dao;

import com.baizhi.entity.Poetries;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PoetriesDAO {
    public List<Poetries> findAll();
}
