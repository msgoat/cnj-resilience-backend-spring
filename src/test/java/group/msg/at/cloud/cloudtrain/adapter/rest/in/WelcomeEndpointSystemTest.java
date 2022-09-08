package group.msg.at.cloud.cloudtrain.adapter.rest.in;

import group.msg.at.cloud.common.test.rest.RestAssuredSystemTestFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.net.URI;
import java.net.URISyntaxException;

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

    private String downstreamASabotageEndpointUrl;

    private String downstreamBSabotageEndpointUrl;

    @BeforeAll
    public void onBeforeClass() {
        fixture.onBefore();
    }

    @AfterAll
    public void onAfterClass() {
        fixture.onAfter();
    }

    @AfterEach
    public void onAfterEach() {
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
        if (downstreamASabotageEndpointUrl == null) {
            URI testTargetRoute = getTestTargetRoute();
            String baseUrl = "http://localhost:31080";
            String sabotageUri = "/api/v1/sabotage";
            downstreamASabotageEndpointUrl = baseUrl + sabotageUri;
        }
        return downstreamASabotageEndpointUrl;
    }

    private String getDownStreamBSabotageEndpointUrl() {
        if (downstreamBSabotageEndpointUrl == null) {
            String baseUrl = "http://localhost:32080";
            String sabotageUri = "/api/v1/sabotage";
            downstreamBSabotageEndpointUrl = baseUrl + sabotageUri;
        }
        return downstreamBSabotageEndpointUrl;
    }

    private void resetDownStreamASabotageState() {
        given().param("noResponse", "false")
                .param("slowResponse", "false")
                .param("alwaysFail", "false")
                .get(getDownStreamASabotageEndpointUrl())
                .then().assertThat()
                .statusCode(200);
    }

    private void resetDownStreamBSabotageState() {
        given().param("noResponse", "false")
                .param("slowResponse", "false")
                .param("alwaysFail", "false")
                .get(getDownStreamBSabotageEndpointUrl())
                .then().assertThat()
                .statusCode(200);
    }

    private URI getTestTargetRoute() {
        URI result = null;
        try {
            result = new URI(RestAssured.basePath);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(String.format("Test target route is invalid: expected valid URI but got [%s]", RestAssured.basePath));
        }
        return result;
    }
}
