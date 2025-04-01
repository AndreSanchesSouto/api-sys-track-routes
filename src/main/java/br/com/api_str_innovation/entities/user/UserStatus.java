package br.com.api_str_innovation.entities.user;

public enum UserStatus {
    UNAVAILABLE("unavailable"),
    ACTIVE("active"),
    INACTIVE("inactive");

    private String status;

    UserStatus(String status)  {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
