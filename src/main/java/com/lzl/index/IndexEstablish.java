package com.lzl.index;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.*;
import java.nio.file.Paths;

public class IndexEstablish {
    public static void indexEstablish() {
        File filelist=new File("C:\\Users\\20136\\Desktop\\vscode_java\\threeKingdom\\filelist");
        String dirPath="C:\\Users\\20136\\Desktop\\vscode_java\\threeKingdom\\dir";
        IndexWriter indexWriter=null;
        try {
            Analyzer analyzer=new IKAnalyzer();
            IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);
            FSDirectory dictionary= FSDirectory.open(Paths.get(dirPath));
            indexWriter =new IndexWriter(dictionary,indexWriterConfig);
            File[] files=filelist.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isFile()){
                    System.out.println("File " + file.getCanonicalPath()+ "正在被索引....");
                    String s = txtReader(file.getCanonicalPath());
                    Document document=new Document();
                    Field name =new StringField("name",file.getName(), Field.Store.YES);
                    Field text =new TextField("text",s,Field.Store.YES);
                    document.add(name);
                    document.add(text);
                    indexWriter.addDocument(document);
                    System.out.println("被索引的文档个数：" + indexWriter.numRamDocs());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (indexWriter!=null){
                try {
                    indexWriter.close();// 关闭writer
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static String txtReader(String path) throws IOException {
        File file=new File(path);
        InputStream inputStream=new FileInputStream(file);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"GBK"));
        StringBuilder stringBuilder=new StringBuilder();
        while (true){
            String line = bufferedReader.readLine();
            if ("".equals(line)||null==line){
                break;
            }
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }
}
