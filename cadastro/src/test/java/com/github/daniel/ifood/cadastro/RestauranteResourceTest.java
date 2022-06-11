package com.github.daniel.ifood.cadastro;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.github.daniel.ifood.cadastro.dto.AdicionarRestauranteDTO;
import com.github.daniel.ifood.cadastro.dto.AtualizarRestauranteDTO;
import com.github.daniel.ifood.cadastro.dto.LocalizacaoDTO;
import com.github.daniel.ifood.cadastro.util.TokenUtils;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

import org.approvaltests.Approvals;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteResourceTest {
	
	private String token;
	
    @BeforeEach
    public void gereToken() throws Exception {
        token = TokenUtils.generateTokenString("/JWTProprietarioClaims.json", null);
    }

	@Test
	@DataSet("restaurantes-cenario-1.yml")
	public void testBuscarRestaurantes() {
		String resultado = given().when().get("/restaurantes").then().statusCode(200).extract().asString();
		Approvals.verifyJson(resultado);
	}

	private RequestSpecification given() {
		return RestAssured.given()
				.contentType(ContentType.JSON)
				.header(new Header("Authorization", "Bearer " + token));
	}

	@Test
	@DataSet("restaurantes-cenario-1.yml")
	public void testAlterarRestaurante() {
		AtualizarRestauranteDTO dto = new AtualizarRestauranteDTO();
		dto.nomeFantasia = "novoNome";
		Long parameterValue = 123L;

		given().with().pathParam("id", parameterValue).body(dto).when().put("/restaurantes/{id}").then()
				.statusCode(Status.NO_CONTENT.getStatusCode()).extract().asString();

		Restaurante findById = Restaurante.findById(parameterValue);

		Assert.assertEquals(dto.nomeFantasia, findById.nome);
	}

	@Test
	public void adicionarNovoRestaurante() {

		LocalizacaoDTO localizacaoDTO = new LocalizacaoDTO();
		localizacaoDTO.latitude = (double) 222;
		localizacaoDTO.longitude = (double) 333;

		AdicionarRestauranteDTO restauranteDTO = new AdicionarRestauranteDTO();
		restauranteDTO.proprietario = "Alessandro";
		restauranteDTO.cnpj = "51.148.669/0001-66";
		restauranteDTO.nomeFantasia = "Mariando";
		restauranteDTO.localizacao = localizacaoDTO;

		given().with().body(restauranteDTO).when().post("/restaurantes").then()
				.statusCode(Status.CREATED.getStatusCode()).extract().asString();

		Restaurante restaurante = Restaurante.find("cnpj", "51.148.669/0001-66").firstResult();
		Assert.assertEquals(restaurante.cnpj, restauranteDTO.cnpj);
		Assert.assertEquals(restaurante.proprietario, restauranteDTO.proprietario);
		Assert.assertEquals(restaurante.nome, restauranteDTO.nomeFantasia);
		Assert.assertEquals(restaurante.localizacao.longitude, restauranteDTO.localizacao.longitude);
		Assert.assertEquals(restaurante.localizacao.latitude, restauranteDTO.localizacao.latitude);
	}

	@Test
	@DataSet("restaurantes-cenario-1.yml")
	public void deletarRestaurante() {

		List<Restaurante> todosRestaurantes = Restaurante.listAll();
		Integer totalRestaurantesAntes = todosRestaurantes.size();

		Restaurante restaurante = Restaurante.findById(123L);
		given().with().pathParam("id", restaurante.id).when().delete("/restaurantes/{id}").then()
				.statusCode(Status.NO_CONTENT.getStatusCode()).extract().asString();

		todosRestaurantes = Restaurante.listAll();
		Integer totalRestaurantesDepois = todosRestaurantes.size() + 1;

		Assert.assertEquals(totalRestaurantesAntes, totalRestaurantesDepois);
	}

}
