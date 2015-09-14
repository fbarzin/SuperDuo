package barqsoft.footballscores.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.fbarzin.footballwebapi.FootballApi;
import com.fbarzin.footballwebapi.FootballService;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FootballFetchService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPDATE_SCORES = "barqsoft.footballscores.service.action.UPDATESCORS";
    private static final String ACTION_BAZ = "barqsoft.footballscores.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "barqsoft.footballscores.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "barqsoft.footballscores.service.extra.PARAM2";

    private FootballApi api;
    private FootballService footballService;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startUpdateScores(Context context) {
        Intent intent = new Intent(context, FootballFetchService.class);
        intent.setAction(ACTION_UPDATE_SCORES);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FootballFetchService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public FootballFetchService() {
        super("FootballFetchService");
        api = new FootballApi();
        footballService = api.getService();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_SCORES.equals(action)) {
                updateScores("n2");
                updateScores("p2");
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
            return;
        }
    }

    private void updateScores(String timeFrame) {
        footballService.getTimeFrameFixtures(timeFrame);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
