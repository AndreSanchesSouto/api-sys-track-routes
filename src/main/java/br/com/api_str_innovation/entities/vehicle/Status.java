package br.com.api_str_innovation.entities.vehicle;

public enum Status {
        WAITING("waiting"),
        ACTIVE("active"),
        UNAVAILABLE("unavailable"),
        ON_USE("on_use"),
        INACTIVE("inactive");

        private String status;

        Status(String status)  {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

}
