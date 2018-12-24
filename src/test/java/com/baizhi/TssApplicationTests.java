package com.baizhi;

import com.baizhi.entity.PagePoetries;
import com.baizhi.entity.Poet;
import com.baizhi.entity.Poetries;
import com.baizhi.service.PoetriesService;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TssApplicationTests {
    @Autowired
    private PoetriesService poetriesService;
    @Test
    public void contextLoads() {

        List<Poetries> x = poetriesService.queryAll();
        for (Poetries poetries : x) {
            System.out.println(poetries);
        }

    }
    @Test
    public void createIndex() throws IOException {
        //指定索引储存的位置
        FSDirectory fsDirectory=FSDirectory.open(Paths.get("D:\\Lucene\\07"));
        //创建写入对象
        IndexWriter indexWriter=new IndexWriter(fsDirectory,new IndexWriterConfig(new SmartChineseAnalyzer()));
        //创建Document
        Document document=null;
        List<Poetries> poetries = poetriesService.queryAll();
        for (Poetries poetry : poetries) {
            //将poetry存入
            document=new Document();
            document.add(new IntField("id",poetry.getId(), Field.Store.YES));
            document.add(new TextField("author",poetry.getPoet().getName(),Field.Store.YES));
            document.add(new TextField("title",poetry.getTitle(),Field.Store.YES));
            document.add(new TextField("content",poetry.getContent(),Field.Store.YES));
            indexWriter.addDocument(document);
        }
        indexWriter.commit();
        indexWriter.close();

    }
    @Test
    public void query() throws IOException, ParseException {
        //获得索引地址
        FSDirectory fsDirectory=FSDirectory.open(Paths.get("D:\\Lucene\\07"));
        //创建索引读入器
        IndexReader indexReader= DirectoryReader.open(fsDirectory);
        //创建检索器
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
        String text="野老篱前江岸回";
        //创建查询解析器对象  作用解析查询表达式
        QueryParser queryParser=new QueryParser("content",new SmartChineseAnalyzer());
        Query query=queryParser.parse("author:"+text+" OR title:"+text+" OR content"+text+"");
        TopDocs topDocs=null;
        Integer nowPage=1;

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
        //获得总条数
        int totalHits = topDocs.totalHits;
        List<Poetries> poetries=new ArrayList<Poetries>();
        Poetries poetry=new Poetries();
        Poet poet=new Poet();
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            //获得索引的下标
            int doc = scoreDoc.doc;
            Document document = indexReader.document(doc);
            System.out.println(document.get("title"));
            poetry.setPoet(poet);
        }

        indexReader.close();

    }
}

