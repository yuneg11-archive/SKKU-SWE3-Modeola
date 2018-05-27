package skku.swprac3.modeola;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import net.openid.appauth.AuthState;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import cloud.artik.api.MessagesApi;
import cloud.artik.client.ApiCallback;
import cloud.artik.client.ApiClient;
import cloud.artik.client.ApiException;
import cloud.artik.model.NormalizedMessagesEnvelope;

public class ArtikNotificationService extends Service {
    SharedPreferences sharedPreferences;
    String stateStr;
    private String mAccessToken;
    Thread artikNotificationChecker;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences = getSharedPreferences(AuthStateDAL.AUTH_PREFERENCES_NAME, Context.MODE_PRIVATE);
        stateStr = sharedPreferences.getString(AuthStateDAL.AUTH_STATE, null);
        if (!TextUtils.isEmpty(stateStr)) {
            try {
                mAccessToken = AuthState.jsonDeserialize(stateStr).getAccessToken();
                artikNotificationChecker = new Thread(new ARTIKNotificationChecker());
                artikNotificationChecker.start();

                /*Debug*/Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
            } catch(JSONException ignore) {
                Log.w("AUTH", ignore.getMessage(), ignore);

                /*Debug*/Toast.makeText(this, "Auth Failed", Toast.LENGTH_SHORT).show();

                onDestroy();
            }
        } else {
            mAccessToken = null;

            /*Debug*/Toast.makeText(this, "Auth Failed", Toast.LENGTH_SHORT).show();

            onDestroy();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        /*Debug*/Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    private class ARTIKNotificationChecker implements Runnable {
        private static final String TAG = "ARTIKNotiServ";
        private long refreshInterval = 5000; // millisecond
        private MessagesApi mMessagesApi = null;
        private long prevMsgTime = 0; // UNIX Time
        private long latestMsgTime = 0; // UNIX Time
        private long NotificationInterval = 10000; // millisecond

        ARTIKNotificationChecker() {
            ApiClient mApiClient = new ApiClient();
            mApiClient.setAccessToken(mAccessToken);
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
                                } else {
                                    latestMsgTime = 0;
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
            while (true) {
                getLatestMsg();

                Log.v(TAG, ":: Prev Msg Time = " + prevMsgTime);
                Log.v(TAG, ":: Latest Msg Time = " + latestMsgTime);
                Log.v(TAG, ":: Msg Time Interval = " + (latestMsgTime - prevMsgTime) / 1000 + "second");

                if (latestMsgTime == 0) {
                    Log.w(TAG, "Auth Failed");
                } else if (prevMsgTime == 0) {
                    prevMsgTime = latestMsgTime;
                } else if (latestMsgTime - prevMsgTime > NotificationInterval || /*Debug*/ArtikConfig.debugNotification) {
                    prevMsgTime = latestMsgTime;
                    Intent problemSet = new Intent(getApplicationContext(), ProblemSetActivity.class);
                    problemSet.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(problemSet);
                    /*Debug*/ArtikConfig.debugNotification = false;
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