package skku.swprac3.modeola;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import net.openid.appauth.AuthState;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import cloud.artik.api.MessagesApi;
import cloud.artik.api.UsersApi;
import cloud.artik.client.ApiCallback;
import cloud.artik.client.ApiClient;
import cloud.artik.client.ApiException;
import cloud.artik.model.NormalizedMessagesEnvelope;

public class ArtikNotificationService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Thread artikNotificationChecker = new Thread(new ARTIKNotificationChecker());
        artikNotificationChecker.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class ARTIKNotificationChecker implements Runnable {
        private static final String TAG = "ARTIKNotiServ";
        private long refreshInterval = 5000; // millisecond
        private Handler handler = new Handler();
        private String mAccessToken;
        private UsersApi mUsersApi = null;
        private MessagesApi mMessagesApi = null;
        private long prevMsgTime = 0; // UNIX Time
        private long latestMsgTime = 0; // UNIX Time
        private long NotificationInterval = 10000; // millisecond

        ARTIKNotificationChecker() {
            SharedPreferences sharedPreferences = getSharedPreferences(AuthStateDAL.AUTH_PREFERENCES_NAME, Context.MODE_PRIVATE);
            String stateStr = sharedPreferences.getString(AuthStateDAL.AUTH_STATE, null);
            if (!TextUtils.isEmpty(stateStr)) {
                try {
                    mAccessToken = AuthState.jsonDeserialize(stateStr).getAccessToken();
                } catch(JSONException ignore) {
                    Log.w("AUTH", ignore.getMessage(), ignore);
                }
            } else {
                mAccessToken = new AuthState().getAccessToken();
            }
            Log.v(TAG, "::onCreate get access token = " + mAccessToken);

            setupArtikCloudApi();
        }

        private void setupArtikCloudApi() {
            ApiClient mApiClient = new ApiClient();
            mApiClient.setAccessToken(mAccessToken);

            mUsersApi = new UsersApi(mApiClient);
            mMessagesApi = new MessagesApi(mApiClient);
        }

        private void getLatestMsg() {
            final String tag = TAG + ".getLstMsg";
            try {
                int messageCount = 1;
                mMessagesApi.getLastNormalizedMessagesAsync(messageCount, ArtikConfig.DEVICE_ID, null,
                        new ApiCallback<NormalizedMessagesEnvelope>() {
                            @Override
                            public void onFailure(ApiException exc, int i, Map<String, List<String>> stringListMap) {
                                Log.w(tag, " onFailure with exception" + exc);
                                exc.printStackTrace();
                            }

                            @Override
                            public void onSuccess(NormalizedMessagesEnvelope result, int i, Map<String, List<String>> stringListMap) {
                                //Log.v(tag, " onSuccess latestMessage = " + result.getData().toString());
                                String data = "";
                                if (!result.getData().isEmpty()) {
                                    latestMsgTime = result.getData().get(0).getTs();
                                }
                            }
                            @Override
                            public void onUploadProgress(long bytes, long contentLen, boolean done) {}
                            @Override
                            public void onDownloadProgress(long bytes, long contentLen, boolean done) {}
                        });
            } catch (ApiException exc) {
                Log.w(tag, " onFailure with exception" + exc);
                exc.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(true) {
                getLatestMsg();

                Log.v(TAG, ":: Prev Msg Time = " + prevMsgTime);
                Log.v(TAG, ":: Latest Msg Time = " + latestMsgTime);
                Log.v(TAG, ":: Msg Time Interval = " + (latestMsgTime - prevMsgTime) / 1000 + "second");

                if ((latestMsgTime - prevMsgTime > NotificationInterval) && prevMsgTime != 0) {
                    prevMsgTime = latestMsgTime;
                    Intent problemSet = new Intent(getApplicationContext(), ProblemSetActivity.class);
                    startActivity(problemSet);
                }
                if(prevMsgTime == 0) {
                    prevMsgTime = latestMsgTime;
                }

                try {
                    Thread.sleep(refreshInterval);
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }
}