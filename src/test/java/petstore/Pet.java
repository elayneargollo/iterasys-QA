package petstore;

import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Pet {
    final String URI_SWAGGER = "https://petstore.swagger.io/v2/pet";
    final String PATH_PET_INCLUSAO = "db/petEntity.json";
    final String PATH_PET_ATUALIZACAO = "db/petEntityUpdate.json";
    final String CONTENT_TYPE = "application/json";

    public String lerJson(String caminhoJson) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void incluirPet() throws IOException
    {
        String jsonBody = lerJson(PATH_PET_INCLUSAO);

        given()
                .contentType(CONTENT_TYPE)
                .log().all()
                .body(jsonBody)
        .when()
                .post(URI_SWAGGER)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Nino"))
                .body("status", is("available"))
                .body("category.name",is("dog"))
                .body("tags.name",contains("sta"));
    }

    @Test
    public void consultarPet()
    {
        String IdEntity = "2018116017";

        given()
                .contentType(CONTENT_TYPE)
                .log().all()
        .when()
                .get(URI_SWAGGER.concat("/").concat(IdEntity))
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Nino"))
                .body("status", is("available"));
    }

    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson(PATH_PET_ATUALIZACAO);

        given()
                .contentType(CONTENT_TYPE)
                .log().all()
                .body(jsonBody)
        .when()
                .put(URI_SWAGGER)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Nino"))
                .body("status", is("sold"));
    }
}
