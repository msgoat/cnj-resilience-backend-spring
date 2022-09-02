package group.msg.at.cloud.cloudtrain.adapter.rest.out.recommendations;

import group.msg.at.cloud.cloudtrain.core.entity.RecommendedItem;

import java.util.List;

public interface RecommendationsClient {

    List<RecommendedItem> getRecommendations(String userId);
}
