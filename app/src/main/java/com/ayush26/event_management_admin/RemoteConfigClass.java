package com.ayush26.event_management_admin;

public class RemoteConfigClass {
    private String baseURL;
    private String apiKey;

    private static final RemoteConfigClass remoteConfig = new RemoteConfigClass();

    public static RemoteConfigClass getRemoteConfig() {
        return remoteConfig;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
}
