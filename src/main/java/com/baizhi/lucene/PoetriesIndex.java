package com.baizhi.lucene;

import com.baizhi.entity.PagePoetries;
import com.baizhi.entity.Poet;
import com.baizhi.entity.Poetries;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PoetriesIndex {

    public PagePoetries queryPoetries(String text, Integer nowPage) throws IOException, ParseException, InvalidTokenOffsetsException {
        //获得索引地址
        FSDirectory fsDirectory=FSDirectory.open(Paths.get("D:\\Lucene\\07"));
        //创建索引读入器
        IndexReader indexReader= DirectoryReader.open(fsDirectory);
        //创建检索器
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
        //创建查询解析器对象  作用解析查询表达式
        QueryParser queryParser=new QueryParser("content",new SmartChineseAnalyzer());
        Query query=queryParser.parse("author:"+text+" OR title:"+text+" OR content"+text+"");
        TopDocs topDocs=null;
        //判断是不是第一页
        if(nowPage==null||nowPage==1){
            topDocs=indexSearcher.search(query,10);
        }else if(nowPage>1){
            //获得上一页的最后一个对象
            topDocs=indexSearcher.search(query,(nowPage-1)*10);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            ScoreDoc scoreDoc = scoreDocs[scoreDocs.length-1];
            topDocs=indexSearcher.searchAfter(scoreDoc,query,10);
        }
        //设置高亮
        Scorer scorer = new QueryScorer(query);
        SimpleHTMLFormatter  simpleHTMLFormatter=new SimpleHTMLFormatter("<span style=\"color:red\">","</span>");
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter,scorer);
        //获得总条数
        int totalHits = topDocs.totalHits;
        List<Poetries> poetries=new ArrayList<Poetries>();

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            Poetries poetry=new Poetries();
            Poet poet=new Poet();
            //获得索引的下标
            int doc = scoreDoc.doc;
            Document document = indexReader.document(doc);
            String title = highlighter.getBestFragment(new SmartChineseAnalyzer(), "title", document.get("title"));
            String content = highlighter.getBestFragment(new SmartChineseAnalyzer(), "content", document.get("content"));
            String author = highlighter.getBestFragment(new SmartChineseAnalyzer(), "author", document.get("author"));
            if(title==null){
                poetry.setTitle(document.get("title"));
            }else{

                poetry.setTitle(title);
            }
            if(content==null){
                poetry.setContent(document.get("content"));
            }else{
                poetry.setContent(content);
            }
            if(author==null){
                poet.setName(document.get("author"));
            }else{
                poet.setName(author);
            }

            poetry.setPoet(poet);
            poetries.add(poetry);
        }
        PagePoetries pagePoetries=new PagePoetries();
        pagePoetries.setCount(totalHits);
        pagePoetries.setPoetries(poetries);
        indexReader.close();
        return pagePoetries;
    }

}
