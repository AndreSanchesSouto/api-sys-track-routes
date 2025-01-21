package br.com.api_str_innovation.entities.checklist;

import lombok.Getter;

public enum ChecklistFieldOptions {

    STATUS_DAMAGED("damaged"),
    STATUS_MISSING("missing"),
    STATUS_FALSE("false"),
    MINIMUM_FUEL_LEVEL("5"),
    MINIMUM_OIL_LEVEL("4"),
    MINIMUM_WATER_LEVEL("2");

    @Getter
    private String field;

    ChecklistFieldOptions(String field) {
        this.field = field;
    }

}
