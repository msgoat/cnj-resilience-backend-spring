package group.msg.at.cloud.cloudtrain.core.control;

import group.msg.at.cloud.cloudtrain.adapter.rest.out.recommendations.RecommendationsClient;
import group.msg.at.cloud.cloudtrain.core.entity.RecommendedItem;
import group.msg.at.cloud.cloudtrain.core.entity.WelcomeItems;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResilientRecommendationsCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResilientRecommendationsCollector.class);

    @Autowired
    RecommendationsClient client;

    @CircuitBreaker(name = "downstreamA", fallbackMethod = "collectAfterFailure")
    public void collect(WelcomeItems target, String userId) {
        LOGGER.info("retrieving recommendations...");
        List<RecommendedItem> recommendations = client.getRecommendations(userId);
        target.setRecommendations(recommendations);
        LOGGER.info("... succeeded to retrieve recommendations...");
    }

    public void collectAfterFailure(WelcomeItems target, String userId, Exception caught) {
        LOGGER.info("...failed to retrieve recommendations");
        target.addStatusMessage("recommendations are temporarily unavailable!");
    }
}
