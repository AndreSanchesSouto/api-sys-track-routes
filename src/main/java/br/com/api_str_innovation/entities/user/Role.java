package br.com.api_str_innovation.entities.user;

public enum Role {
    GENERAL_MANAGER("general_manager"),
    SHIPPING_MANAGER("shipping_manager"),
    DRIVER("driver");

    private String role;

    Role(String role)  {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
