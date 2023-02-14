package com.example.my42_asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

public class BackgroundTask extends AsyncTask<Void, Integer, String> {
    private static final String TAG = "main:BackgroundTask";

    ProgressBar progressBar;
    int value;

    // 우리는 무조건 생성자를 만들어 데이터를 넘겨받는다
    public BackgroundTask(ProgressBar progressBar, int value) {
        this.progressBar = progressBar;
        this.value = value;
    }

    // 1. 실제 작업을 하기전에 초기화나 사전작업을 하는곳
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        value = 0;
        progressBar.setProgress(0);
    }

    // 2. 실질적으로 작업을 하는곳
    @Override                   //   첫번째 파라메터
    protected String doInBackground(Void... voids) {
        while (isCancelled() == false){
            value++;
            if(value > 100){
                break;
            }else {
                // 데이터를 onProgressUpdate에게 넘겨줄수 있다 : 배열로 넘겨줌
                //publishProgress(value);
                publishProgress(value, value + 1, value + 2);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "100% 완료..."; // result를 보내준다
    }

    // 2-1. 작업중에 필요한 데이터를 받는곳 : doInBackground에 종속됨
    @Override                   //   두번째 파라메터
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        // 1-100까지의 수를 넘겨 받아 progressBar에 적용시킨다
        progressBar.setProgress(values[0]);
        Log.d(TAG, "onProgressUpdate: " + values[0]);
    }

    // 3. 작업이 종료한후 실행되는곳
    @Override                // 세번째 파라메터
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d(TAG, "onPostExecute: " + result);
    }

    // 실행취소
    @Override
    protected void onCancelled() {
        super.onCancelled();

        Log.d(TAG, "onCancelled: 실행취소됨 !!!");
        progressBar.setProgress(0);
    }
}
