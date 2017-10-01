package com.company.app.client.web.dtos.types;

import com.company.app.client.EnumUtils;
import com.company.app.client.IdentifierType;

import java.util.Objects;

/**
 * @author Idan Rozenfeld
 */
public enum ProductCategoryDto implements IdentifierType<String> {
    GAME("G"), CLOTHING("Clth");

    private final String id;

    ProductCategoryDto(String id) {
        this.id = id;
    }

    public static ProductCategoryDto byValue(String value) {
        if (Objects.nonNull(value)) {
            return EnumUtils.getByValue(ProductCategoryDto.class, value);
        }

        return null;
    }

    @Override
    public String getValue() {
        return id;
    }
}
