package com.baizhi.entity;

import java.util.List;

public class PagePoetries {
    private List<Poetries> poetries;
    private Integer count;

    @Override
    public String toString() {
        return "PagePoetries{" +
                "poetries=" + poetries +
                ", count=" + count +
                '}';
    }

    public List<Poetries> getPoetries() {
        return poetries;
    }

    public void setPoetries(List<Poetries> poetries) {
        this.poetries = poetries;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public PagePoetries() {
    }

    public PagePoetries(List<Poetries> poetries, Integer count) {
        this.poetries = poetries;
        this.count = count;
    }
}
