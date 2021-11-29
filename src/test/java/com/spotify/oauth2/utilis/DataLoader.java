package com.spotify.oauth2.utilis;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader dataLoader;

    private DataLoader() {
        this.properties = PropertyUtils.propertyLoader("src/test/resources/data.properties");
    }

    public static DataLoader getInstance(){
        if(dataLoader == null) {
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    public String getPlayListId(){
        String prop = properties.getProperty("get_playlist_id");
        if(prop != null) return prop;
        else throw new RuntimeException("property get playlist id is not specified in the data.properties file");
    }

    public String getUpdatePlayListId(){
        String prop = properties.getProperty("update_playlist_id");
        if(prop != null) return prop;
        else throw new RuntimeException("property update playlist id is not specified in the data.properties file");
    }

}
