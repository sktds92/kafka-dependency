package org.example.util;

import org.example.common.AbsSdnObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configure extends AbsSdnObject {
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
            logger.debug(path);
            this.readSystemEnvironments();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readSystemEnvironments() {
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        for(String key: this.properties.stringPropertyNames()){
            String value = this.properties.getProperty(key);
            Matcher matcher = pattern.matcher(value);
            if(matcher.find()){
                String group = matcher.group().substring(2, matcher.group().length()-1);
                this.properties.put(key,System.getenv(group));
            }
        }
    }

    public void initProperties(){
        String configFilePath = this.getConfigFilePath();
        this.initProperties(configFilePath);
    }

    private String getConfigFilePath() {
        String env = System.getenv("ENV");
        String fileName = "";
        try {
            if (env.equals("prod") || env.equals("PROD") || env.equals("live")) {
                fileName = "config-prod.properties";
            } else if (env.equals("dev") || env.equals("DEV") || env.equals("develop")) {
                fileName = "config-dev.properties";
            } else {
                fileName = "config.properties";
            }
        }catch(Exception e){
            e.printStackTrace();
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
