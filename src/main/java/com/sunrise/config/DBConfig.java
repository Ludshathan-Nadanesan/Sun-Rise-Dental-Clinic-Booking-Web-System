package com.sunrise.config;

import java.io.InputStream;
import java.util.Properties;

public class DBConfig {

    private static Properties properties = new Properties();

    static {

        try {

            InputStream input =
            DBConfig.class
            .getClassLoader()
            .getResourceAsStream("db.properties");


            properties.load(input);

        } catch(Exception e){

            e.printStackTrace();

        }

    }


    public static String get(String key){

        return properties.getProperty(key);

    }

}