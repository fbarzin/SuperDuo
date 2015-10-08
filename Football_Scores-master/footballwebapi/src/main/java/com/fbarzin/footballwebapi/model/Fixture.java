package com.fbarzin.footballwebapi.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author farzad
 * @date 9/14/15
 */
public class Fixture {
    @SerializedName("_links") public Links links;
    public String date;
    public String status;
    public int matchday;
    public String homeTeamName;
    public String awayTeamName;
    public Result result;
}
