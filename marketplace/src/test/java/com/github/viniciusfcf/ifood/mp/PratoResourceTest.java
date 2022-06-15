package com.github.viniciusfcf.ifood.mp;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PratoResourceTest {

	@Test
	public void testHelloEndpoint() {
		String body = given().when().get("/pratos").then().statusCode(200).extract().asString();
		System.out.println(body);
	}

}