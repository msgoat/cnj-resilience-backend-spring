package group.msg.at.cloud.cloudtrain.adapter.rest.in;

import group.msg.at.cloud.common.test.rest.RestAssuredSystemTestFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

/**
 * System test that verifies that the REST endpoint works as expected.
 * <p>
 * Assumes that a remote server hosting the REST endpoint is up and running.
 * </p>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WelcomeEndpointSystemTest {

    private static final RestAssuredSystemTestFixture fixture = new RestAssuredSystemTestFixture();

    private TargetRouteBuilder targetRouteBuilder;

    @BeforeAll
    public void onBeforeClass() {
        fixture.onBefore();
        targetRouteBuilder = new TargetRouteBuilder()
                .withTargetRoute(RestAssured.baseURI)
                .withLocalPortBinding("cnj-resilience-downstream-a", 31080)
                .withLocalPortBinding("cnj-resilience-downstream-b", 32080);
    }

    @AfterAll
    public void onAfterClass() {
        fixture.onAfter();
    }

    @BeforeEach
    public void onBeforeEach() {
        resetDownStreamASabotageState();
        resetDownStreamBSabotageState();
    }

    @Test
    public void getWelcomeItemsWithoutResilienceWorksWhenDownstreamIsAvailable() {
        given().get("api/v1/welcome/{userId}", "testUser")
                .then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void getWelcomeItemsWithResilienceWorksWhenDownstreamIsAvailable() {
        given().get("api/v1/welcome/resilient/{userId}", "testUser")
                .then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void getWelcomeItemsWithoutResilienceFailsWhenDownstreamAIsDown() {
        given().param("alwaysFail", "true")
                .get(getDownStreamASabotageEndpointUrl())
                .then().assertThat()
                .statusCode(200);
        given().get("api/v1/welcome/{userId}", "testUser")
                .then().assertThat()
                .statusCode(500);
    }

    @Test
    public void getWelcomeItemsWithResilienceWorksWhenDownstreamAIsDown() {
        given().param("alwaysFail", "true")
                .get(getDownStreamASabotageEndpointUrl())
                .then().assertThat()
                .statusCode(200);
        given().get("api/v1/welcome/resilient/{userId}", "testUser")
                .then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    private String getDownStreamASabotageEndpointUrl() {
        return targetRouteBuilder.buildFor("cnj-resilience-downstream-a") + "/api/v1/sabotage";
    }

    private String getDownStreamBSabotageEndpointUrl() {
        return targetRouteBuilder.buildFor("cnj-resilience-downstream-b") + "/api/v1/sabotage";
    }

    private void resetDownStreamASabotageState() {
        given().param("alwaysFail", "false")
                .get(getDownStreamASabotageEndpointUrl())
                .then().assertThat()
                .statusCode(200);
    }

    private void resetDownStreamBSabotageState() {
        given().param("alwaysFail", "false")
                .get(getDownStreamBSabotageEndpointUrl())
                .then().assertThat()
                .statusCode(200);
    }
}
