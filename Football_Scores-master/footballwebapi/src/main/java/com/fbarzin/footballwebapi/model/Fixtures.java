package com.fbarzin.footballwebapi.model;

import java.util.List;

/**
 * @author farzad
 * @date 9/14/15
 */
public class Fixtures {
    public int count;
    public String timeFrameStart;
    public String timeFrameEnd;
    private List<Fixture> fixtures;

    public List<Fixture> getFixtures() {
        return fixtures;
    }
}
