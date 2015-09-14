package barqsoft.footballscores.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.fbarzin.footballwebapi.FootballApi;
import com.fbarzin.footballwebapi.FootballService;
import com.fbarzin.footballwebapi.model.Fixtures;

import barqsoft.footballscores.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class FootballFetchService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPDATE_SCORES = "barqsoft.footballscores.service.action.UPDATESCORS";

    private FootballApi api;
    private FootballService footballService;
    private Fixtures fixtures;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startUpdateScores(Context context) {
        Intent intent = new Intent(context, FootballFetchService.class);
        intent.setAction(ACTION_UPDATE_SCORES);
        context.startService(intent);
    }

    

    public FootballFetchService() {
        super("FootballFetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_SCORES.equals(action)) {
                updateScores("n2");
                updateScores("p2");
            }
        }
    }

    private void updateScores(String timeFrame) {
        if (api == null) {
            api = new FootballApi();
            api.setmAccessToken(getString(R.string.api_key));
        }
        footballService = api.getService();
        fixtures = footballService.getTimeFrameFixtures(timeFrame);
        writeFixturesToDatabase();
    }

    private void writeFixturesToDatabase() {

    }

}
