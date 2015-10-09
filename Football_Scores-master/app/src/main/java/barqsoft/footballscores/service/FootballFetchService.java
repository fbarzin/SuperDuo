package barqsoft.footballscores.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fbarzin.footballwebapi.FootballApi;
import com.fbarzin.footballwebapi.FootballService;
import com.fbarzin.footballwebapi.model.Fixture;
import com.fbarzin.footballwebapi.model.Fixtures;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import barqsoft.footballscores.data.DatabaseContract;
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
    private static final String TAG = FootballFetchService.class.getSimpleName();

    private FootballApi api;
    private FootballService footballService;
    private Fixtures fixtures;
    private String mDate;
    private String mTime;

    final String SEASON_LINK = "http://api.football-data.org/alpha/soccerseasons/";
    final String MATCH_LINK = "http://api.football-data.org/alpha/fixtures/";

    /**
     * Starts this service to perform action ACTION_UPDATE_SCORES with the given parameters. If
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
        Log.d(TAG, "onHandleIntent()");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_SCORES.equals(action)) {
                updateScores("n2");
                updateScores("p2");
            }
        }
    }

    private void updateScores(String timeFrame) {
        Log.d(TAG, "updateScores()");
        if (api == null) {
            api = new FootballApi();
            api.setmAccessToken(getString(R.string.api_key));
        }
        footballService = api.getService();
        try {
            fixtures = footballService.getTimeFrameFixtures(timeFrame);
            writeFixturesToDatabase();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            // TODO: retrofit call failed! show error dialog here
        }
    }

    private void writeFixturesToDatabase() {
        Vector<ContentValues> values = new Vector<>(fixtures.count);
        if (fixtures.count > 0) {
            for (int i = 0; i < fixtures.getFixtures().size(); i++) {
                ContentValues matchValues = new ContentValues();
                Fixture fixture = fixtures.getFixtures().get(i);
                extractDateAndTimeFromDateObject(fixture.date);
                matchValues.put(DatabaseContract.scores_table.MATCH_ID, extractMatchIdFromSelfLink(fixture));
                matchValues.put(DatabaseContract.scores_table.DATE_COL, mDate);
                matchValues.put(DatabaseContract.scores_table.TIME_COL, mTime);
                matchValues.put(DatabaseContract.scores_table.HOME_COL, fixture.homeTeamName);
                matchValues.put(DatabaseContract.scores_table.AWAY_COL, fixture.awayTeamName);
                matchValues.put(DatabaseContract.scores_table.HOME_GOALS_COL, fixture.result.goalsHomeTeam);
                matchValues.put(DatabaseContract.scores_table.AWAY_GOALS_COL, fixture.result.goalsAwayTeam);
                matchValues.put(DatabaseContract.scores_table.LEAGUE_COL, extractLeagueIdFromSeasonLink(fixture));
                matchValues.put(DatabaseContract.scores_table.MATCH_DAY, fixture.matchday);
                values.add(matchValues);
            }
            ContentValues[] insert_data = new ContentValues[values.size()];
            values.toArray(insert_data);
            getApplicationContext().getContentResolver().bulkInsert(
                    DatabaseContract.BASE_CONTENT_URI, insert_data);
        }

    }

    private String extractMatchIdFromSelfLink(Fixture fixture) {
        String matchId = fixture.links.self.href;
        return matchId.replace(MATCH_LINK, "");
    }

    private String extractLeagueIdFromSeasonLink(Fixture fixture) {
        String leagueId = fixture.links.soccerseason.href;
        return leagueId.replace(SEASON_LINK, "");
    }

    private void extractDateAndTimeFromDateObject(String matchDate) {
        mTime = matchDate.substring(matchDate.indexOf('T') + 1, matchDate.indexOf('Z'));
        mDate = matchDate.substring(0, matchDate.indexOf('T'));
        SimpleDateFormat matchDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        matchDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date parsedDate = matchDateFormat.parse(mDate + mTime);
            SimpleDateFormat new_date = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
            new_date.setTimeZone(TimeZone.getDefault());
            mDate = new_date.format(parsedDate);
            mTime = mDate.substring(mDate.indexOf(":") + 1);
            mDate = mDate.substring(0, mDate.indexOf(":"));
        } catch (Exception e) {
            Log.d(TAG, "error here!");
            Log.e(TAG, e.getMessage());
        }
    }


}
