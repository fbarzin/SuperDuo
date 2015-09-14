package barqsoft.footballscores.base;

import android.app.Application;

/**
 * @author farzad
 * @date 9/14/15
 */
public class FootballApplication extends Application {

    private static FootballApplication singleton;

    public static FootballApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
