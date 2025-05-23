package br.com.api_str_innovation.projections;

import java.math.BigDecimal;

public interface LocationProjection {
    BigDecimal getLongitude();
    BigDecimal getLatitude();
}
