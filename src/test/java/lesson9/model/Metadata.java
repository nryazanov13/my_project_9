package lesson9.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {
    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("lastLogin")
    private String lastLogin;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
