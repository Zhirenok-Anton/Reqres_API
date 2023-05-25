package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import models.UserGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static specs.Specs.request;
import static specs.Specs.*;

@Epic("API Reqres")
@Feature("User registration")
@Tag("API")
public class RegisterTests {

    @Test
    @DisplayName("Successful registration of default user")
    @Description("POST /api/register")
    void successRegisterTest() {

        UserGenerator user = UserGenerator.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        given()
                .spec(request)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(response200)
                .body("token", is(notNullValue()));
    }

    @Test
    @DisplayName("Unsuccessful registration of unsupported user")
    @Description("POST /api/register")
    void unSuccessRegisterUnsupportedUserTest() {

        UserGenerator user = UserGenerator.builder()
                .email("diff.mail@reqres.in")
                .password("diff_pass")
                .build();

        given()
                .spec(request)
                .body(user)
                .when()
                .post("/register")
                .then()
                .spec(response400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    @DisplayName("Unsuccessful registration with no passed values")
    @Description("POST /api/register")
    void unSuccessRegisterTest() {

        given()
                .spec(request)
                .when()
                .post("/register")
                .then()
                .spec(response400);
    }
}