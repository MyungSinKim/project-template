package com.company.template.server.services.mapping;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import com.company.template.server.domain.model.Product;
import com.company.template.server.domain.model.types.ProductCategory;
import com.github.rozidan.springboot.modelmapper.WithModelMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WithModelMapper(basePackageClasses = MappingBasePackage.class)
public class ProductMappingTest {

    @Autowired
    private ModelMapper mapper;

    @Test
    public void productDtoToEntityMappedSuccess() {
        ProductDto dto = ProductDto.builder()
                .id(1L)
                .name("John")
                .description("Desc")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100f)
                .build();

        Product result = mapper.map(dto, Product.class);

        assertThat(result.getId(), is(equalTo(1L)));
        assertThat(result.getName(), is(equalTo("John")));
        assertThat(result.getUnitPrice(), is(equalTo(100F)));
        assertThat(result.getCategory(), is(equalTo(ProductCategory.CLOTHING)));
        assertThat(result.getDesc(), is(equalTo("Desc")));
    }
}
