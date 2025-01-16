package br.com.api_str_innovation.entities.user;

public enum Status {
    UNAVAILABLE("unavailable"),
    ACTIVE("active"),
    INACTIVE("inactive");

    private String status;

    Status(String status)  {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
