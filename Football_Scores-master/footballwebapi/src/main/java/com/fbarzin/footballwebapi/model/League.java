package com.fbarzin.footballwebapi.model;

import java.util.List;

/**
 * @author farzad
 * @date 9/11/15
 */
public class League {

    public List<Links> _links;
    public String caption;
    public String league;
    public String year;
    public int numberOfTeams;
    public int numberOfGames;
    public String lastUpdated;

}
