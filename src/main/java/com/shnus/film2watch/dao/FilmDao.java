package com.shnus.film2watch.dao;

import com.shnus.film2watch.model.FilmBean;

import java.util.List;


public interface FilmDao {
    boolean addFilm(FilmBean film, Long userId);

    List<FilmBean> getWatchlist(Long id);

    boolean filmExist(Long userId, Long filmId);

    boolean rateFilm(long userId, int filmId, float vote);

    boolean deleteFilm(long userId, int filmId);
}
