package br.com.teravalt.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;


public class BoundService extends Service {

    private static String LOG_TAG = "BoundService";
    private IBinder binder = new MyBinder();
    private Chronometer chronometer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG,"**** in onCreate");
        chronometer = new Chronometer(this);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "**** in onBind");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(LOG_TAG, "**** in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG,"**** in onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "**** in onDestroy");
        chronometer.stop();
    }

    public String getTimeStamp(){
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        int horas = (int) (elapsedMillis / 3600000);
        int minutos = (int) (elapsedMillis - horas * 3600000) / 60000;
        int segundos = (int) (elapsedMillis - horas * 3600000 - minutos * 60000) / 1000;
        int millis = (int) (elapsedMillis - horas * 3600000 - minutos * 60000 - segundos * 1000);

        return horas + ":" +  minutos + ":" + segundos + ":" + millis;
    }

    public class MyBinder extends Binder{
        BoundService getService(){
            return BoundService.this;
        }
    }
}
