package com.example.socket_connection;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.util.List;
import java.io.InputStream;
import java.io.Reader;
import java.io.OutputStream;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ProgressDialog;

//getApplicationContext().getFilesDir() スマホの内部ストレージパス
public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView_2;
    private TextView textView_3;
    private TextView textView_4;

    private ProgressDialog mProgressDialog;
    final static String HOST = "192.168.0.4";
    final static int PORT = 5000;
    private boolean flag = false;
    private boolean flag_2 = false;
    //File file = new File(getApplicationContext().getFilesDir()+"test.txt");
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView_2 = findViewById(R.id.textView_2);
        textView_3 = findViewById(R.id.textView_3);
        textView_4 = findViewById(R.id.textView_4);
        //final String path = Environment.getExternalStorageDirectory().getPath();
        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("A A A");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Socket_Connection_AsyncTask();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Socket_Server_AsyncTask();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File_delete();
            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(flag){
                    textView.setText("Hello");
                    //File_output();
                    flag = false;
                }
                else{
                    File_input();
                    //textView.setText("World");
                    flag = true;
                }
            }
        });
    }
    public void File_output(){
        try {
            FileOutputStream fileOutputStream = openFileOutput("test.txt", MODE_PRIVATE);
            fileOutputStream.write("aaaaa".getBytes());// FileOutputStreamは文字列を直接書き込みができない。そのためgetBytes()でバイトの配列に変更する必要がある。
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void File_input(){
        try{
            FileInputStream fileInputStream = openFileInput("test.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"));
            String lineBuffer = bufferedReader.readLine();//readlineは一行のみ
            String text_contents = lineBuffer;
            while(lineBuffer != null){
                lineBuffer = bufferedReader.readLine();
                if(lineBuffer != null){
                    text_contents = text_contents+"\n"+lineBuffer;
                }
            }
            textView.setText(text_contents);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void File_delete(){
        File file = this.getFileStreamPath("test.txt");
        boolean fileExists = file.exists();
        if(fileExists == true) {
            textView_2.setText("ファイルが存在したので削除しました。");
            deleteFile("test.txt");
        }else{
            textView_2.setText("ファイルが存在しません。");
        }
    }

    public void Socket_Server(){
        textView_3.setText("データ受信中…");
        String outputFilepath = "ac.txt";       // 受信したファイルの保存先
        byte[] buffer         = new byte[512]; // ファイル受信時のバッファ
        try {
// ソケットの準備
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket       socket       = serverSocket.accept();
            //BufferedReader reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            //String receive_data = reader.readLine();

            //reader.close();
            socket.close();
            serverSocket.close();
            textView_3.setText("受信しました！"+" ");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Socket_Server_AsyncTask(){
        textView_3.setText("データ受信中…");
        AsyncTask<Void,Integer,String> task = new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String receive_data = "aaaa";
                String whicch_go = "";
                List<String> Answer = new ArrayList<String>();

                String filename_1 = null;
                try {


                    ServerSocket serverSocket = new ServerSocket(5000);
                    Socket       socket       = serverSocket.accept();
                    int count = 0;

                    BufferedReader reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));//受信したデータの行数を取得して、readline()で一行ずつファイルに書き込む際、ProgressDiaLogで進捗状況を確認したい


                    filename_1 = reader.readLine(); //一回目のreadlineで受け取るデータはファイル名: これを取得し、相手側と同じファイル名に出力できる。

                    FileOutputStream fileOutputStream = openFileOutput(filename_1, MODE_PRIVATE);//ファイルの権限がアプリしか使用できないになっているため、あとで変えること

                    int count_1 = 0;

                    while (receive_data!= null){
                        receive_data = reader.readLine();
                        if(receive_data != null){
                            count += 1;
                            Answer.add(receive_data);
                        }
                    }
                    reader.close();
                    socket.close();
                    serverSocket.close();


                    for (int i=0;i<Answer.size();i++){
                        fileOutputStream.write(Answer.get(i).getBytes());
                        fileOutputStream.write("\n".getBytes());
                        count_1 += 1;
                        publishProgress(count_1*100/count);
                        if(count_1*100/count == 100){
                            publishProgress(0);
                            mProgressDialog.dismiss();
                        }
                    }

                    fileOutputStream.flush();
                    fileOutputStream.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                    return filename_1;
            }
            @Override
            protected void onPostExecute(String string){
                textView_3.setText("受信しました！"+" "+string);
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog.show();
            }
            @Override
            protected void onProgressUpdate(Integer... progress) {
                mProgressDialog.setProgress(progress[0]);
            }


        };
        task.execute();
    }
    public void Socket_Connection_AsyncTask(){
        textView_4.setText("データ送信中");
        AsyncTask<Void,Integer,String> task = new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... Voids){
                byte[] buffer = new byte[512];
                String a = "送信しました!";
                List<String> Answer_2 = new ArrayList<String>();
                String file_name = "test.pdf"+"\n";
                try {
                    Socket socket = new Socket(HOST, PORT);
                    FileInputStream inputStream = openFileInput("test.pdf");
                    BufferedReader reader_2 = new BufferedReader(new InputStreamReader(inputStream));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(file_name,0,file_name.length());

                    String FileLength;
                    while((FileLength = reader_2.readLine()) != null){
                        Answer_2.add(FileLength);
                    }
                    for (int i=1;i<Answer_2.size()+1;i++){
                        writer.write(Answer_2.get(i-1));
                        publishProgress(i*100/Answer_2.size());
                        if(i*100/Answer_2.size() == 100){
                            publishProgress(0);
                            mProgressDialog.dismiss();
                        }
                    }

                    while((FileLength = reader_2.readLine()) != null){
                        writer.write(FileLength,0,FileLength.length());
                    }
                    writer.flush();
                    writer.close();
                    inputStream.close();
                    socket.close();

                }catch (IOException e){
                    a = "送信に失敗しました！";
                    publishProgress(0);
                    mProgressDialog.dismiss();

                    e.printStackTrace();
                }
                return a;
            }
            @Override
            protected void onPostExecute(String string){
                textView_4.setText(string);
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog.show();
            }
            @Override
            protected void onProgressUpdate(Integer... progress) {
                mProgressDialog.setProgress(progress[0]);
            }
        };
        task.execute();
    }
}
