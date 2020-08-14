package com.zs.util;

import com.zs.bean.Movie;
import com.zs.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.*;

public class RedisUtils {

    public static final int PORT = 6379;
    public static final String HOST = "localhost";

    @Autowired
    private static DataService dataService;

    public static Jedis getJedis(){
        Jedis jedis = new Jedis(HOST,PORT);

        return jedis;
    }

    public static Movie getMovie(String mId){

        Jedis jedis = new Jedis(HOST,PORT);

        return new Movie(jedis.hgetAll(mId),mId);

    }

    public static List<String> getHotWord(){

        List<String> list=new ArrayList<>();

        File file =new File("E:\\myProject\\java\\find_movies\\src\\main\\resources\\hotword.dic");
        BufferedReader input=null;
        String data="";

        try {

            input=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));

            while ((data=input.readLine())!=null){

                list.add(data);

            }

            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;

    }

    public static Long setHotWord(String word){

        Jedis jedis = new Jedis(HOST,PORT);

        Long n = jedis.sadd("hotWord", word);

        jedis.close();

        return n;

    }

    public static List<String> getNames(String mId){

        Jedis jedis = new Jedis(HOST,PORT);

        Map<String, String> movie = jedis.hgetAll(mId);

        jedis.close();

        List<String> list=new ArrayList<>();

        String actor[]=movie.get("actor").split("/");
        for (String a:actor) {
            list.add(a);
        }

        String director[]=movie.get("director").split("/");
        for (String a:director) {
            list.add(a);
        }

        return list;
    }

    public static void writeHotWord(String word){

        try {

            File file=new File("E:\\kelin\\java\\find_movies\\src\\main\\resources\\hotword.dic");

            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));


            bw.newLine();
            bw.write(word);
            bw.flush();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> getMovieId(){

        List<String> list=new ArrayList<>();

        Jedis jedis = new Jedis(HOST,PORT);

        list=jedis.lrange("m_list",0,-1);
//        list=jedis.lrange("m_list",0,10);

        return list;

    }

    public double getGrade(Map<String,Integer> keywords,String mId) throws Exception {

        double value=0;

        Jedis jedis = new RedisUtils().getJedis();

        Map<String, String> movie=jedis.hgetAll(mId);

        LuceneUtils luceneUtils = new LuceneUtils();

        int mIndex=1;

        //s1  片名 + 别名 = 40%
        //s2  导演 = 10%
        //s3  演员 = 10%
        //s4  标签 + 类型 = 10%
        //s5  国家，时间，简介，评论 = 30%
        List<String> sList=new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            sList.add(movie.get(mId+"kw"+i));
        }

        for (String m: sList) {
            switch (mIndex){
                case 1:
                    value+=luceneUtils.Similarity2(keywords,m)*0.4;
//                    value+=luceneUtils.SimilarityTitle(keywords,m,movie.get("title"))*0.4;
                    break;
                case 5:
                    value+=luceneUtils.Similarity2(keywords,m)*0.3;
                    break;
                default:
                    value+=luceneUtils.Similarity2(keywords,m)*0.1;
                    break;
            }
            mIndex++;
        }

        return value;

    }

    public Movie getMin(List<Movie> list){

        Movie movie=list.get(0);

        for (Movie m : list) {
            if (movie.getGrade()<m.getGrade()){
                movie=m;
            }
        }

        return movie;
    }

    public void setMoviesType(){

//        String types="剧情/喜剧/动作/爱情/科幻/动画/悬疑/惊悚/恐怖/犯罪/同性/音乐/歌舞/传记/历史/战争/西部/奇幻/冒险/灾难/武侠/情色";
//        String types="中国大陆/美国/中国香港/中国台湾/日本/韩国/英国/法国/德国/意大利/西班牙/印度/泰国/俄罗斯/伊朗/加拿大/澳大利亚/爱尔兰/瑞典/巴西/丹麦";
        String types="2020/2019/2010年代/2000年代/90年代/80年代/70年代/60年代/更早";


        String[] type=types.split("/");

        Jedis jedis=getJedis();

        for (String t: type) {
//            jedis.lpush("movieType", t);
//            jedis.lpush("movieCountry", t);
            jedis.lpush("movieTime", t);
        }

    }

    public void setTypeList() throws Exception {

        LuceneUtils luceneUtils=new LuceneUtils();

        Jedis jedis=getJedis();

//        List<String> movieTypes = jedis.lrange("movieType", 0, -1);
//        List<String> movieTypes = jedis.lrange("movieCountry", 0, -1);
//        List<String> movieTypes = jedis.lrange("movieTime", 0, -1);
        List<String> movieId = getMovieId();

        for (String mId: movieId) {

            Movie movie = getMovie(mId);

//            String types=movie.getM_type();
//            String types=movie.getCountry();
            String types=movie.getRelease();
            if (types!=null){

//                String[] type=types.split("/");

//            Map<String, Integer> m_type = luceneUtils.getFrequency(types);

//                for (int i=0;i<movieTypes.size();i++) {

//                    String movieType=movieTypes.get(i);

                    //国家
//                    for (String t: type) {
//                        if (t.equals(movieType)){
//                            jedis.sadd("country"+i, movie.getmId());
//                        }
//                    }

                    //类型
//                if (m_type.get(movieType)!=null){
//                    jedis.sadd("type"+i, movie.getmId());
//                }

//                }


                String[] years = types.split("/");
                for (String y: years) {
                    String[] temp = y.split("\\(");
                    String[] temp2 = temp[0].split("-");
                    String temp3=temp2[0].replace(" ","");
                    if(temp3.equals("")||temp2==null){
                        continue;
                    }

                    int year=0;

                    try {
                        year = Integer.parseInt(temp3);
                    } catch (NumberFormatException e) {
                        continue;
                    }


                    if(year==2020){
                        jedis.sadd("time"+8, movie.getmId());
                    }else if (year==2019){
                        jedis.sadd("time"+7, movie.getmId());
                    }else if (year>=2010){
                        jedis.sadd("time"+6, movie.getmId());
                    }else if (year>=2000){
                        jedis.sadd("time"+5, movie.getmId());
                    }else if (year>=1990){
                        jedis.sadd("time"+4, movie.getmId());
                    }else if (year>=1980){
                        jedis.sadd("time"+3, movie.getmId());
                    }else if (year>=1970){
                        jedis.sadd("time"+2, movie.getmId());
                    }else if (year>=1960){
                        jedis.sadd("time"+1, movie.getmId());
                    }else {
                        jedis.sadd("time"+0, movie.getmId());
                    }
                }



            }


        }


    }

    public void indexOptimization(String mId) throws Exception {

        Jedis jedis=getJedis();

        LuceneUtils luceneUtils=new LuceneUtils();

        Map<String, String> movie = jedis.hgetAll(mId);

        List<String> sList=new ArrayList<>();

        sList.add(movie.get("title")+movie.get("other_name"));
        sList.add(movie.get("director"));
        sList.add(movie.get("actor"));
        sList.add(movie.get("m_type")+movie.get("tag"));
        sList.add(movie.get("country")+movie.get("release")+movie.get("introduction")+movie.get("message"));

        List<Map<String,Integer>> mapList=new ArrayList<>();
        for (String s: sList) {
            mapList.add(luceneUtils.getFrequency(s));
        }

        int kwIndex=1;

        for (Map<String,Integer> map: mapList) {

            Map<String,String> kwMap=new HashMap<String,String>();

            StringBuffer kwString=new StringBuffer();

            Iterator<Map.Entry<String, Integer>> entries = map.entrySet().iterator();
            while(entries.hasNext()){

                Map.Entry<String, Integer> entry = entries.next();
                String key = entry.getKey();

                kwString.append(key).append("/");

            }

            kwMap.put(mId+"kw"+kwIndex,kwString.toString());

            jedis.hmset(mId,kwMap);

            kwIndex++;

        }

        jedis.close();

    }

    public List<Movie> getListByStartAndEnd(int start, int end,List<Movie> data){

        List<Movie> list=new ArrayList<>();

        for (int i = start; i <= end; i++) {

            list.add(data.get(i));

        }

        return list;

    }



}
