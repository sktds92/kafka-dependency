package org.example.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

    public void initProperties(){
        String configFilePath = this.getConfigFilePath();
        this.initProperties(configFilePath);
    }

    private String getConfigFilePath() {
        String env = System.getenv("ENV");
        String fileName = "";
        if(env.equals("prod") || env.equals("PROD") || env.equals("live")){
            fileName = "config-prod.properties";
        }else if(env.equals("dev") || env.equals("DEV") || env.equals("develop")){
            fileName = "config-dev.properties";
        }else{
            fileName = "config.properties";
        }
        return this.makeConfigFilePath(fileName);
    }

    private String makeConfigFilePath(String fileName) {
        URL resource = Configure.class.getClassLoader().getResource(fileName);
        return resource.getPath();
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
