package com.lzl;

import com.lzl.index.IndexEstablish;
import com.lzl.index.SearchLucene;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class LuceneSearchApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void indexSet() throws IOException {
        IndexEstablish.indexEstablish();
    }

    @Test
    public void test() throws IOException, ParseException, InvalidTokenOffsetsException {
            SearchLucene searchLucene=new SearchLucene();
            List<Map<String, String>> list = searchLucene.searchTxt("张飞");
            for (Map<String, String> map : list) {
                System.out.println(map.get("SCORE"));
                System.out.println(map.get("NAME"));
                System.out.println(map.get("TEXT"));
                System.out.println("------------------------");

            }
    }

}
