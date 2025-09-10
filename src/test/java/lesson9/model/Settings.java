package lesson9.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Settings {

    @JsonProperty("maxUsers")
    private int maxUsers;

    @JsonProperty("theme")
    private String theme;

    @JsonProperty("featuresEnabled")
    private List<String> featuresEnabled;

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<String> getFeaturesEnabled() {
        return featuresEnabled;
    }

    public void setFeaturesEnabled(List<String> featuresEnabled) {
        this.featuresEnabled = featuresEnabled;
    }
}
