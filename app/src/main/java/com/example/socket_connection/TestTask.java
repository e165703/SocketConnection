package com.example.socket_connection;
import android.os.AsyncTask;
public class TestTask extends AsyncTask<Integer,Integer,Integer> {

    // 非同期処理
    @Override
    protected Integer doInBackground(Integer... params) {

        return params[0] ;
    }

    // 途中経過をメインスレッドに返す
    @Override
    protected void onProgressUpdate(Integer... progress) {
        // ...
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(Integer integer) {
        // ...
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}