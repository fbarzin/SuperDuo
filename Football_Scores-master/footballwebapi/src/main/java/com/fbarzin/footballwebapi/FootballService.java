package com.fbarzin.footballwebapi;

import com.fbarzin.footballwebapi.model.Fixtures;
import com.fbarzin.footballwebapi.model.Leagues;
import com.fbarzin.footballwebapi.model.LeagueTable;
import com.fbarzin.footballwebapi.model.Teams;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author farzad
 * @date 9/11/15
 */
public interface FootballService {


    /******************
     * Soccer Seasons *
     ******************/

    /**
     * Lists all available soccer leagues for 2015 season
     *
     * @param callback
     */
    @GET("/soccerseasons/")
    void getSoccerSeason(Callback<List<Leagues>> callback);

    /**
     * Lists all available soccer leagues for 2015 season
     *
     * @return SoccerSeason
     */
    @GET("/soccerseasons/")
    List<Leagues> getSoccerSeason();


    /**
     * Lists all available soccer leagues for queried season
     *
     * @param year ex: 2014
     * @param callback
     */
    @GET("/soccerseasons/")
    void getSoccerSeason(@Query("season") int year, Callback<List<Leagues>> callback);


    /**
     * Lists all available soccer leagues for queried season
     *
     * @param year ex: 2014
     * @return SoccerSeason
     */
    @GET("/soccerseasons/")
    List<Leagues> getSoccerSeason(@Query("season") int year);


    /**
     * Lists all the teams in the league with given id
     *
     * @param id This is the league id
     * @param callback
     */
    @GET("/soccerseasons/{id}/teams")
    void getTeamsOfTheLeague(@Path("leagueId") int id, Callback<Teams> callback);


    /**
     * Lists all the teams in the league with given id
     *
     * @param id
     * @return Teams
     */
    @GET("/soccerseasons/{id}/teams")
    Teams getTeamsOfTheLeague(@Path("leagueId") int id);


    /**
     * Gives the league table/current standing of the given league id
     *
     * @param id This is the league id
     * @param callback
     */
    @GET("/soccerseasons/{id}/leagueTable")
    void getLeagueTable(@Path("leagueId") int id, Callback<LeagueTable> callback);


    /**
     * Gives the league table/current standing of the given league id
     *
     * @param id This is the league id
     * @return LeagueTable
     */
    @GET("/soccerseasons/{id}/leagueTable")
    LeagueTable getLeagueTable(@Path("leagueId") int id);


    /**
     * Lists all fixtures for a certain soccer season
     *
     * @param id This is the league id
     */
    @GET("soccerseasons/{id}/fixtures")
    void getFixtures(@Path("leagueId") int id, Callback<Fixtures> callback);


    /**
     * Lists all fixtures for a certain soccer season
     *
     * @param id This is the league id
     * @return Fixtures
     */
    @GET("soccerseasons/{id}/fixtures")
    Fixtures getFixtures(@Path("leagueId") int id);


    /******************
     * Fixtures *
     ******************/

    /**
     * Lists recent fixtures
     *
     * @param callback
     */
    @GET("fixtures")
    void getRecentFixtures(Callback<Fixtures> callback);


    /**
     * Lists recent fixtures
     *
     * @return Fixtures
     */
    @GET("fixtures")
    Fixtures getRecentFixtures();

}
