package com.baizhi.entity;

public class Poetries {
    private Integer id;
    private Integer poetId;
    private String title;
    private String content;
    private Poet poet;

    @Override
    public String toString() {
        return "Poetries{" +
                "id=" + id +
                ", poetId=" + poetId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", poet=" + poet +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoetId() {
        return poetId;
    }

    public void setPoetId(Integer poetId) {
        this.poetId = poetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Poet getPoet() {
        return poet;
    }

    public void setPoet(Poet poet) {
        this.poet = poet;
    }

    public Poetries() {
    }

    public Poetries(Integer id, Integer poetId, String title, String content, Poet poet) {
        this.id = id;
        this.poetId = poetId;
        this.title = title;
        this.content = content;
        this.poet = poet;
    }
}
