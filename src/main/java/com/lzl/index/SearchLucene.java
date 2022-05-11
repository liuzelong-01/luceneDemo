package com.lzl.index;

import org.apache.lucene.analysis.Analyzer;
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
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class SearchLucene {
    public  List<Map<String,String>> searchTxt(String queryString) throws IOException, ParseException, InvalidTokenOffsetsException {
        String indexDir="C:\\Users\\20136\\Desktop\\vscode_java\\threeKingdom\\dir";
        IndexReader indexReader;

        indexReader= DirectoryReader.open(FSDirectory.open(Path.of(indexDir)));
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
        Analyzer analyzer= new IKAnalyzer();
        QueryParser queryParser=new QueryParser("text",analyzer);
        Query query=queryParser.parse(queryString);

        TopDocs docs=indexSearcher.search(query,10);
        ScoreDoc[] scoreDocs= docs.scoreDocs;
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> map;
        for (ScoreDoc scoreDoc : scoreDocs) {

            float score = scoreDoc.score;
            Document document = indexSearcher.doc(scoreDoc.doc);
            String name = document.get("name");
            String text = document.get("text");
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color: red'>","</span>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter,new QueryScorer(query));
            highlighter.setTextFragmenter(new SimpleFragmenter(100));
            text=highlighter.getBestFragment(analyzer,"text",text);

            map=new HashMap<>();
            map.put("SCORE", String.valueOf(score));
            map.put("NAME",name);
            map.put("TEXT",text);
            list.add(map);
        }
        return list;
    }

}
