package com.baizhi.service;

import com.baizhi.dao.PoetriesDAO;
import com.baizhi.entity.Poet;
import com.baizhi.entity.Poetries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("poetriesService")
public class PoetriesServiceImpl implements PoetriesService {
    @Autowired
    private PoetriesDAO poetriesDAO;


    @Override
    public List<Poetries> queryAll() {
        return poetriesDAO.findAll();
    }
}
