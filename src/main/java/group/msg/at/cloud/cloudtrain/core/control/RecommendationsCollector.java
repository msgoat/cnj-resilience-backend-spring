package group.msg.at.cloud.cloudtrain.core.control;

import group.msg.at.cloud.cloudtrain.adapter.rest.out.recommendations.RecommendationsClient;
import group.msg.at.cloud.cloudtrain.core.entity.RecommendedItem;
import group.msg.at.cloud.cloudtrain.core.entity.WelcomeItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationsCollector {

    @Autowired
    RecommendationsClient client;

    public void collect(WelcomeItems target, String userId) {
        List<RecommendedItem> recommendations = client.getRecommendations(userId);
        target.setRecommendations(recommendations);
    }
}
