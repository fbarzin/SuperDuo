package com.fbarzin.footballwebapi.model;

import java.util.List;

/**
 * @author farzad
 * @date 9/14/15
 */
public class Fixtures {
    public Links _links;
    public int count;
    public String timeFrameStart;
    public String timeFrameEnd;
    public List<Fixture> fixtures;
}
