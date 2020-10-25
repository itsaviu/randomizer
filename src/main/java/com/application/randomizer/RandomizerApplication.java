package com.application.randomizer;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class RandomizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RandomizerApplication.class, args);
    }

    @Bean(name = "default")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "custom")
    public RestTemplate customResttTemplate() {
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setRedirectStrategy(getRedirectStrategy())
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }

    public DefaultRedirectStrategy getRedirectStrategy() {
        return new DefaultRedirectStrategy() {
            public boolean isRedirected(HttpRequest request, HttpResponse response,
                                        HttpContext context) {
                return false;
            }
        };
    }

    @Bean
    public Docket apiMonitor() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API")
                .description("ImageDetails Service")
                .build();
    }

}
