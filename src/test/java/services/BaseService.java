package services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseService {
    private LogConfig logConfig = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    private RestAssuredConfig config = RestAssuredConfig.config().logConfig(logConfig);
    protected final RequestSpecification requestSpec;
    protected final ResponseSpecification responseSpec;

    public BaseService(String baseUrl) {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setConfig(config)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();
    }
}
