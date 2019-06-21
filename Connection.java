package jp.ac.uryukyu.e165703;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;

public class Connection {
    final static String HOST = "121.109.14.50"; // 接続先アドレス
    final static int    PORT = 8001;        // 接続先ポート番号
    public static void main(String[] args) {
        String filepath = "test.txt";             // 送信するファイルのパス
        File   file     = new File(filepath); // 送信するファイルのオブジェクト
        byte[] buffer   = new byte[512];      // ファイル送信時のバッファ

        try {
            // ソケットの準備
            Socket socket = new Socket(HOST, PORT);
            // ストリームの準備
            InputStream  inputStream  = new FileInputStream(file);
            OutputStream outputStream = socket.getOutputStream();

            // ファイルをストリームで送信
            int fileLength;
            while ((fileLength = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, fileLength);
            }

            // 終了処理
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}