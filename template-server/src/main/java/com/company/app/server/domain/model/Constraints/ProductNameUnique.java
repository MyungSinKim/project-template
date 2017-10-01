package com.company.app.server.domain.model.Constraints;

import org.springframework.stereotype.Component;

@Component
public class ProductNameUnique implements DataUniqueConstraint {

    public static final String CONSTRAINT_NAME = "UNIQUE_PRODUCT_NAME";
    public static final String FIELD_NAME = "name";

    @Override
    public String getConstraintName() {
        return CONSTRAINT_NAME;
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }
}