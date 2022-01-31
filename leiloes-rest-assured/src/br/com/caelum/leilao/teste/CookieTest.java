package br.com.caelum.leilao.teste;

import org.junit.Test;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;

public class CookieTest {

	@Test
	public void testeCookieGerado() {
		expect()
		.cookie("rest-assured", "funciona")
		.when()
			.get("/cookie/teste")
		;
	}
	
	@Test
	public void testeHeaderGerado() {
		expect()
		.header("novo-header", "abc")
		.when()
			.get("/cookie/test");
	}
	
}
