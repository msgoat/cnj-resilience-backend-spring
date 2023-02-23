package group.msg.at.cloud.cloudtrain.adapter.rest.out.recommendations;

import group.msg.at.cloud.cloudtrain.core.entity.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class RestTemplateRecommendationsClient implements RecommendationsClient {

    /**
     * Inject a {@code RestTemplateBuilder} instead of a {@code RestTemplate}
     * to be able to create a RestTemplate with some common configuration and
     * some endpoint specific configuration.
     */
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    /**
     * Inject configuration property with downstream service base URL.
     */
    @Value("${cloudtrain.services.recommendations.url}")
    String downstreamServiceUrl;

    /**
     * RestTemplate to be used to call downstream service.
     */
    RestTemplate restTemplate;

    /**
     * Build a {@code RestTemplate} using the provided {@code RestTemplateBuilder}.
     */
    @PostConstruct
    public void onPostConstruct() {
        restTemplate = restTemplateBuilder.rootUri(downstreamServiceUrl).build();
    }

    @Override
    public List<RecommendedItem> getRecommendations(String userId) {
        RecommendedItem[] found = restTemplate.getForObject("/api/v1/recommendations/{userId}", RecommendedItem[].class, userId);
        return Arrays.asList(found);
    }
}
