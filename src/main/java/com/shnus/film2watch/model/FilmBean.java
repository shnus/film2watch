package com.shnus.film2watch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class FilmBean  {

    public int id;
    public float vote_average;
    public float vote;
    public String title;
    public String poster_path;
    public String original_language;
    public String overview;
    public String release_date;


    public FilmBean(){}

}
