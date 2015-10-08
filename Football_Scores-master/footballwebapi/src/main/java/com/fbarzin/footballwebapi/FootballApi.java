package com.fbarzin.footballwebapi;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Creates and configures a REST adapter for Football web API.
 *
 * Basic Usage:
 * FootballApi api = new FootballApi();
 * FootballService football = api.getService();
 *
 *
 * @author farzad
 * @date 9/11/15
 */
public class FootballApi {

    /**
     * Main Football Web API endpoint
     */
    public static final String FOOTBALL_WEB_API_ENDPOINT = "http://api.football-data.org/alpha";


    private final FootballService mFootballService;


    private class WebApiInterceptor implements RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            if (mAccessToken != null) {
                request.addHeader("X-Auth-Token", mAccessToken);
            }
        }
    }

    private String mAccessToken;


    /**
     * Create instance of FootballApi with given executors.
     *
     * @param httpExecutor executor for http request. cannot be null.
     * @param callbackExecutor executor for callbacks. If null is passed then the same
     *                         thread that created the instance is used.
     */
    public FootballApi(Executor httpExecutor, Executor callbackExecutor) {
        mFootballService = init(httpExecutor, callbackExecutor);
    }

    private FootballService init(Executor httpExecutor, Executor callbackExecutor) {

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setExecutors(httpExecutor, callbackExecutor)
                .setEndpoint(FOOTBALL_WEB_API_ENDPOINT)
                .setRequestInterceptor(new WebApiInterceptor())
                .build();

        return restAdapter.create(FootballService.class);
    }

    /**
     * New instance of FootballApi,
     * with single thread executor both for http and callbacks
     */
    public FootballApi() {
        Executor httpExecutor = Executors.newSingleThreadExecutor();
        mFootballService = init(httpExecutor, null);
    }

    public void setmAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    /**
     *
     * @return the FootballService instance
     */
    public FootballService getService() {
        return mFootballService;
    }
}
