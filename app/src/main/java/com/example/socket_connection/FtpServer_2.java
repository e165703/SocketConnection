package com.example.socket_connection;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import java.util.List;
import java.util.ArrayList;
import android.util.Log;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.ftpserver.*;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;
import org.apache.ftpserver.usermanager.impl.BaseUser;

public class FtpServer_2 {
    static public FtpServer server;
    public void Ftp(String grantsResults){
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(2221);
        serverFactory.addListener("default",listenerFactory.createListener());

        ConnectionConfigFactory connectionConfigFactory = new ConnectionConfigFactory();
        connectionConfigFactory.setAnonymousLoginEnabled(true);
        serverFactory.setConnectionConfig(connectionConfigFactory.createConnectionConfig());

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setPasswordEncryptor(new SaltedPasswordEncryptor());
        UserManager userManager = userManagerFactory.createUserManager();

        BaseUser user = new BaseUser();
        user.setName("AAA");
        user.setPassword("AAA");
        user.setHomeDirectory(grantsResults);

        List<Authority> authorities = new ArrayList<>();
        Authority authority = new WritePermission();
        authorities.add(authority);
        user.setAuthorities(authorities);




        try {
            userManager.save(user);
            serverFactory.setUserManager(userManager);
            server = serverFactory.createServer();
            server.start();
        } catch (FtpServerConfigurationException | FtpException e) {
            e.printStackTrace();
        }
    }
    public void Ftp_Server_Stop(){
        if(server.isStopped() == false){
            server.stop();
            Log.d("Ftp_Server_Stop","FTP server stoped");
        }
    }
}
