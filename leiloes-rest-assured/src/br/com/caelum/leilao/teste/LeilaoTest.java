package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Leilao;
import br.com.caelum.leilao.modelo.Usuario;

public class LeilaoTest {

	@Test
	public void getLeilaoJsonPath() {
		JsonPath json = given()
				.header("Accept", "application/json")
				.parameter("leilao.id", 1)
//				.queryParam("usuario.id", 1)
				.get("/leiloes/show")
				.andReturn()
				.jsonPath();
		
		Leilao leilao = json.getObject("leilao", Leilao.class);
		
		Usuario esperado = new Usuario(1l, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		Leilao leilaoEsperado = new Leilao(1l, "Geladeira", 800.0, esperado, false );
		
		assertEquals(leilao, leilaoEsperado);
	}
	
	@Test
	public void getTotalLeiloes() {
		XmlPath xml = given()
				.header("Accept", "application/xml")
				.get("/leiloes/total")
				.andReturn()
				.xmlPath();
		
		int leiloes = 2;
		
		int valorEsperado = xml.getInt("int");

		assertEquals(leiloes, valorEsperado);
	}
	
	@Test
	public void postLeilaoXml() {
		
		Usuario user = new Usuario(5l, "Felipe", "flpnucci@mail.com");
		Leilao leilao = new Leilao(10l, "Playstation 5", 7800.0, user, false );
		
		XmlPath xml = given()
				.header("Accept", "application/xml")
				.contentType("application/xml")
				.body(leilao)
				.when()
				.expect()
				.statusCode(200)
				.post("/leiloes")
				.andReturn()
				.xmlPath();
		
		Leilao leilaoEsperado = xml.getObject("leilao", Leilao.class);
		
		
		assertEquals(leilao, leilaoEsperado);
		
		given()
		.contentType("application/xml")
		.body(leilao)
		.expect()
		.statusCode(200)
		.when()
		.delete("/leiloes/deletar")
		.andReturn()
		.asString();
		
		given()
		.contentType("application/xml")
		.body(user)
		.expect()
		.statusCode(200)
		.when()
		.delete("/usuarios/deleta")
		.andReturn()
		.asString();
	}
}
