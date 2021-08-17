package petstore;

import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class Pet {
    final String URI_SWAGGER = "https://petstore.swagger.io/v2/pet";
    final String PATH_PET_INCLUSAO = "db/petEntity.json";
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
                .statusCode(200);
    }
}
