package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Usuario;

public class UsuarioTest {

	@Test
	public void getListaUsuarios() {
		XmlPath xml = given()
				.header("Accept", "application/xml")
				.get("/usuarios")
				.andReturn()
				.xmlPath();
		
		List<Usuario> usuarios = xml.getList("list.usuario", Usuario.class);
		
		Usuario esperado1 = new Usuario(1l, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		Usuario esperado2 = new Usuario(2l, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
		
		assertEquals(usuarios.get(0), esperado1);
		assertEquals(usuarios.get(1), esperado2);
	}
	
	@Test
	public void getUsuarioJsonPath() {
		JsonPath json = given()
				.header("Accept", "application/json")
//				.parameter("usuario.id", 1)
				.queryParam("usuario.id", 1)
				.get("/usuarios/show")
				.andReturn()
				.jsonPath();
		
		Usuario usuario = json.getObject("usuario", Usuario.class);
		
		Usuario esperado = new Usuario(1l, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		
		
		assertEquals(usuario, esperado);
	}
	
	@Test
	public void postUsuarioXml() {
		
		Usuario user = new Usuario(5l, "Felipe", "flpnucci@mail.com");
		XmlPath xml = given()
				.header("Accept", "application/xml")
				.contentType("application/xml")
				.body(user)
				.when()
				.expect()
				.statusCode(200)
				.post("/usuarios")
				.andReturn()
				.xmlPath();
		
		Usuario usuario = xml.getObject("usuario", Usuario.class);
		
		
		assertEquals("Felipe", usuario.getNome());
		assertEquals("flpnucci@mail.com", usuario.getEmail());
		
		given()
		.contentType("application/xml")
		.body(usuario)
		.expect()
		.statusCode(200)
		.when()
		.delete("/usuarios/deleta")
		.andReturn()
		.asString();

	}
	
}
