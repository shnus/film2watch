package com.shnus.film2watch.service;


import com.shnus.film2watch.dao.FilmDao;
import com.shnus.film2watch.dao.FilmDaoImpl;
import com.shnus.film2watch.model.FilmBean;
import com.shnus.film2watch.service.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class FilmServiceImpl {

    private final FilmDao filmDao;
    private final SecurityService securityService;

    @Autowired
    public FilmServiceImpl(JdbcTemplate jdbcTemplate) {
        this.filmDao = new FilmDaoImpl(jdbcTemplate);
        this.securityService = new SecurityServiceImpl();
    }

    public boolean addFilm(FilmBean film, long id) {
        return filmDao.addFilm(film, id);

    }

    public List<FilmBean> getWatchlist(long id) {
        return filmDao.getWatchlist(id);
    }

    public boolean filmExist(long userId, long filmId) {
        return filmDao.filmExist(userId, filmId);
    }

    public boolean rateFilm(long userId, @Valid FilmBean filmBean) {
        return filmDao.rateFilm(userId, filmBean.id, filmBean.vote);
    }

    public boolean deleteFilm(@Valid FilmBean filmBean, long userId) {
            return filmDao.deleteFilm(userId, filmBean.id);
    }
}
