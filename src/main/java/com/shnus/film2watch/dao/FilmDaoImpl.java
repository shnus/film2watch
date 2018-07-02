package com.shnus.film2watch.dao;

import com.shnus.film2watch.model.FilmBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.ArrayList;
import java.util.List;

public class FilmDaoImpl implements FilmDao {

    private JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean addFilm(FilmBean film, Long userId) {
        String query = "INSERT INTO films(userId,"
                + "filmId,vote_average, vote, title,poster_path,original_language,overview,release_date) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, str_to_date(?,'%Y-%m-%d'));";

        int result = jdbcTemplate.update(
                query, userId, film.id, film.vote_average, film.vote, film.title,
                film.poster_path, film.original_language, film.overview, film.release_date);

        return result > 0;
    }


    @Override
    public List<FilmBean> getWatchlist(Long id) {
        String query = "SELECT * FROM films " +
                "WHERE UserId=?";
        Object[] args = new Long[1];
        args[0] = id;
        List films = jdbcTemplate.query(query, args, (ResultSetExtractor<List>) rs -> {
            List<FilmBean> filmList = new ArrayList<>();
            while (rs.next()) {
                FilmBean film = FilmBean.builder()
                        .id(rs.getInt("filmId"))
                        .original_language(rs.getString("original_language"))
                        .overview(rs.getString("overview"))
                        .poster_path(rs.getString("poster_path"))
                        .release_date(rs.getString("release_date"))
                        .title(rs.getString("title"))
                        .vote_average(rs.getFloat("vote_average"))
                        .vote(rs.getFloat("vote"))
                        .build();
                if(Float.valueOf(rs.getString("vote"))<1){
                    film.vote = -1;
                }
                filmList.add(film);
            }
            return filmList;
        });
        return films;
    }

    public boolean filmExist(Long Userid, Long FilmId){
        String query = "SELECT * FROM films " +
                "WHERE UserId=? AND FilmId=?";
        Object[] args = new Long[2];
        args[0] = Userid;
        args[1] = FilmId;
        List films = jdbcTemplate.query(query, args, (ResultSetExtractor<List>) rs -> {
            List<FilmBean> filmList = new ArrayList<>();
            while (rs.next()) {
                FilmBean film = FilmBean.builder().build();
                filmList.add(film);
            }
            return filmList;
        });

        return !films.isEmpty();
    }
}

