package com.zs.bean;

import java.util.Map;

public class Movie {

    private volatile String title;
    private volatile String mId;
    private volatile String director;
    private volatile String actor;
    private volatile String m_type;
    private volatile String country;
    private volatile String language;
    private volatile String other_name;
    private volatile String release;
    private volatile String introduction;
    private volatile String tag;
    private volatile String message;
    private volatile String url;
    private volatile String img;

    private volatile String kw1;
    private volatile String kw2;
    private volatile String kw3;
    private volatile String kw4;
    private volatile String kw5;

    private double grade;

    public Movie(Map<String,String> movie,String mId) {
        this.title = movie.get("title");
        this.mId=mId;
        this.director = movie.get("director");
        this.actor = movie.get("actor");
        this.m_type = movie.get("m_type");
        this.country = movie.get("country");
        this.language = movie.get("language");
        this.other_name = movie.get("other_name");
        this.release = movie.get("release");
        this.introduction = movie.get("introduction");
        this.tag = movie.get("tag");
        this.message = movie.get("message");
        this.url = movie.get("url");
        this.img = movie.get("img");
        this.kw1 = movie.get(mId+"kw1");
        this.kw2 = movie.get(mId+"kw2");
        this.kw3 = movie.get(mId+"kw3");
        this.kw4 = movie.get(mId+"kw4");
        this.kw5 = movie.get(mId+"kw5");
        this.grade=0;
    }

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", m_type='" + m_type + '\'' +
                ", country='" + country + '\'' +
                ", language='" + language + '\'' +
                ", other_name='" + other_name + '\'' +
                ", release='" + release + '\'' +
                ", introduction='" + introduction + '\'' +
                ", tag='" + tag + '\'' +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getKw5() {
        return kw5;
    }

    public void setKw5(String kw5) {
        this.kw5 = kw5;
    }

    public String getKw1() {
        return kw1;
    }

    public void setKw1(String kw1) {
        this.kw1 = kw1;
    }

    public String getKw2() {
        return kw2;
    }

    public void setKw2(String kw2) {
        this.kw2 = kw2;
    }

    public String getKw3() {
        return kw3;
    }

    public void setKw3(String kw3) {
        this.kw3 = kw3;
    }

    public String getKw4() {
        return kw4;
    }

    public void setKw4(String kw4) {
        this.kw4 = kw4;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getM_type() {
        return m_type;
    }

    public void setM_type(String m_type) {
        this.m_type = m_type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
