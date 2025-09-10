package lesson9.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserData {
    @JsonProperty("application")
    private String application;

    @JsonProperty("version")
    private String version;

    @JsonProperty("users")
    private List<User> users;

    @JsonProperty("settings")
    private Settings settings;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
