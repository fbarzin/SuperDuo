package com.fbarzin.footballwebapi.model;

import java.util.List;

/**
 * @author farzad
 * @date 9/11/15
 */
public class LeagueTable {
    public Links _links;
    public String leagueCaption;
    public int matchday;
    public List<Standing> standing;
}
