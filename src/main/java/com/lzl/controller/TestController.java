package com.lzl.controller;

import com.lzl.index.SearchLucene;
import com.lzl.vo.ReturnVO;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Resource
    private SearchLucene searchLucene;

    @RequestMapping("/search")
    public ReturnVO search(@RequestParam String s) throws InvalidTokenOffsetsException, IOException, ParseException {
        List<Map<String, String>> list = searchLucene.searchTxt(s);
        return new ReturnVO("200","ok",list);
    }
}
