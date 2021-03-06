package com.company.template.client.web.dtos.errors;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.springframework.http.HttpMethod;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("HttpRequestMethodError")
@Getter
@Setter
@Builder
public class HttpRequestMethodErrorDto implements Serializable {
    private static final long serialVersionUID = 4115067500106084449L;

    private String actualMethod;

    @Singular
    private List<HttpMethod> supportedMethods;
}
