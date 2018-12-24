package com.baizhi.entity;

public class Poet {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "Poet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Poet() {
    }

    public Poet(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
