package br.com.desafioSicredi.qa.tests.users;

import br.com.desafioSicredi.qa.client.UserClient;
import br.com.desafioSicredi.qa.tests.BaseTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

public class UserTest extends BaseTest {
    private UserClient userClient = new UserClient();

    @Test
    @DisplayName("Deve listar todos os usuários com sucesso")
    public void deveListarUsuariosComSucesso() {
        userClient.listarTodos()
                .then()
                .statusCode(200)
                .body("users", not(empty()))
                .body("total", is(notNullValue()));
    }

    @Test
    @DisplayName("Deve buscar um usuário específico por ID")
    public void deveBuscarUsuarioPorId() {
        userClient.buscarPorId(1)
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("username", not(emptyString()));
    }

    @Test
    @Disabled("BUG: O endpoint está expondo o campo password na listagem. Reportado na documentação do projeto.")
    @DisplayName("Deve garantir que a senha não seja exposta na listagem")
    public void deveGarantirSegurancaDosDados() {
        userClient.listarTodos()
                .then()
                .body("users", everyItem(not(hasKey("password"))));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar um recurso inexistente")
    public void deveRetornarErroAoBuscarRecursoInexistente() {
        userClient.buscarPorId(999999)
                .then()
                .statusCode(404)
                .body("message", containsString("not found"));
    }

    @Test
    @DisplayName("Deve buscar usuários por um termo específico")
    public void deveBuscarUsuariosPorTermo() {
        String termoBusca = "Emily";

        userClient.buscarUsuarios(termoBusca)
                .then()
                .statusCode(200)
                .body("users.firstName", everyItem(containsStringIgnoringCase(termoBusca)))
                .body("total", is(greaterThanOrEqualTo(0)));
    }
}