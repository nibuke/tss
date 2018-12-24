package com.baizhi;

import com.baizhi.entity.PagePoetries;
import com.baizhi.entity.Poetries;
import com.baizhi.lucene.PoetriesIndex;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/poetries")
public class controller {
    @RequestMapping("/showAll")
    public String showAll(Model model,String text,Integer nowPage) throws IOException, ParseException, InvalidTokenOffsetsException {
        PoetriesIndex poetriesIndex=new PoetriesIndex();
        if(nowPage==null){
            nowPage=1;
        }

        PagePoetries pagePoetries = poetriesIndex.queryPoetries(text, nowPage);
        List<Poetries> poetries = pagePoetries.getPoetries();
        Integer count = pagePoetries.getCount();
        Integer endPage=count%10==0?count/10:count/10+1;
        model.addAttribute("poetries", poetries);
        model.addAttribute("text",text);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("endPage",endPage);
        return "showall";
    }

}
