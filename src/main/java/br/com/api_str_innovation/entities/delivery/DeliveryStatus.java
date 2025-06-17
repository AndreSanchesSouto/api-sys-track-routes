package br.com.api_str_innovation.entities.delivery;

public enum DeliveryStatus {
    WAITING("waiting"),
    ACTIVE("active"),
    ON_ROAD("on_road"),
    INACTIVE("inactive"),
    CONFIRMED("confirmed"),
    COMING_BACK("coming_back");

    private String status;

    DeliveryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
