package com.zs.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.util.*;

public class LuceneUtils {

    public Map<String,Integer> getFrequency(String data) throws Exception{

        Map<String,Integer> DataMap=new HashMap<>();

        Analyzer analyzer=new IKAnalyzer();

        TokenStream tokenStream=analyzer.tokenStream("",data);

        CharTermAttribute charTermAttribute=tokenStream.addAttribute(CharTermAttribute.class);

        tokenStream.reset();

        while (tokenStream.incrementToken()){
            String word = charTermAttribute.toString();

            if (DataMap.containsKey(word)){
                int count = DataMap.get(word);
                DataMap.put(word,count+1);
            }else {
                DataMap.put(word,1);
            }

        }

        tokenStream.close();

        return DataMap;
    }

    //相似性1
    public double Similarity(Map<String,Integer> keywords,Map<String,Integer> words){

        double value=0;

        Iterator<Map.Entry<String, Integer>> entries = keywords.entrySet().iterator();
        while(entries.hasNext()){

            Map.Entry<String, Integer> entry = entries.next();
            String key = entry.getKey();

            if(words.get(key)!=null){
                value+=1;
//                value+=1+words.get(key)*0.01;
            }

        }

//        System.out.println("匹配："+value+" 总数："+n+" 得分："+value/n);
//        System.out.println("匹配："+value+" 总数："+words.size()+" 得分："+value/words.size());

        return value/keywords.size();

    }

    //相似性2
    public double Similarity2(Map<String,Integer> keywords,String kw){

        double value=0;

        String[] kws=kw.split("/");

        Iterator<Map.Entry<String, Integer>> entries = keywords.entrySet().iterator();
        while(entries.hasNext()){

            Map.Entry<String, Integer> entry = entries.next();
            String key = entry.getKey();

            for (String s: kws) {
                if (key.equals(s)){
                    value+=1;
                }
            }

        }

        return value/keywords.size();

    }


    public double SimilarityTitle(Map<String, Integer> keywords, Map<String, Integer> words, String s) {

        Map<String, Integer> tKeywords=new HashMap<>();
        keywords.putAll(tKeywords);
        Map<String, Integer> tWords=new HashMap<>();
        words.putAll(tWords);

        for (int i = 0; i < s.length(); i++) {
            tKeywords.put(s.substring(i,i+1),1);
        }

        Iterator<Map.Entry<String, Integer>> entries = words.entrySet().iterator();
        while(entries.hasNext()){

            Map.Entry<String, Integer> entry = entries.next();
            String key = entry.getKey();

            for (int i = 0; i < key.length(); i++) {
                tWords.put(key.substring(i,i+1),1);
            }

        }

        return Similarity(tKeywords,tWords);

    }
}
