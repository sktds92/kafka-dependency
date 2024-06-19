package org.example.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configure {

    private static Configure configure;
    private Properties properties;

    private Configure(){
        this.properties = new Properties();
    }

    public void initProperties(String path){
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(path);
            this.properties.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized  Configure getInstance(){
        if(configure == null) {
            configure = new Configure();
        }
        return configure;
    }

    public String getProperty(String project) {
        return this.properties.getProperty(project);
    }
}
