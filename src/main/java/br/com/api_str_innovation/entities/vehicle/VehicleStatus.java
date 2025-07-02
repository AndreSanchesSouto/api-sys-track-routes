package br.com.api_str_innovation.entities.vehicle;

public enum VehicleStatus {
        WAITING("waiting"),
        ACTIVE("active"),
        UNAVAILABLE("unavailable"),
        ON_USE("on_use"),
        ON_ROAD("on_road"),
        INACTIVE("inactive");

        private String status;

        VehicleStatus(String status)  {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

}
