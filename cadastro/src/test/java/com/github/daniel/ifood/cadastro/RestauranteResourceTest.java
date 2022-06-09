package com.github.daniel.ifood.cadastro;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteResourceTest {

	@Test
	public void testBuscarRestaurantes() {
		String resultado = given()
				.when().get("/restaurantes")
				.then()
				.statusCode(200)
				.extract().asString();
//		Approvals.verifyJson(resultado);
	}
	
//    private RequestSpecification given() {
//        return RestAssured.given().contentType(ContentType.JSON);
//    }
	
}
