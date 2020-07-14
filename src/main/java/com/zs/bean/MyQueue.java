package com.zs.bean;

import java.util.LinkedList;
import java.util.List;

public class MyQueue {

    public static volatile List<Movie> list;
    final int N=30;

    public MyQueue() {
        this.list = new LinkedList<>();
    }

    public void add(Movie movie){

        if (list.size()==0){
            list.add(movie);
        }else{

            int index=0;
            for (Movie m: list) {
                if (movie.getGrade()>m.getGrade()){
                    list.add(index,movie);

                    if (list.size()>N){
                        remove();
                    }

                    break;
                }
                index++;
            }

        }

    }

    public void remove(){
        list.remove(list.size()-1);
    }

    public int size(){
        return list.size();
    }

    @Override
    public String toString() {

        String s="";

        for (Movie m: list) {
            s+=m.getTitle()+"\t"+m.getGrade()+"\t"+m.getmId()+"\n";
        }

        return "MyQueue{\n" + s +'}';
    }
}
