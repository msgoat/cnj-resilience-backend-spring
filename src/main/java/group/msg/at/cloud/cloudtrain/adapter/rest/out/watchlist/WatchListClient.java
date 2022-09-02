package group.msg.at.cloud.cloudtrain.adapter.rest.out.watchlist;

import group.msg.at.cloud.cloudtrain.core.entity.WatchedItem;

import java.util.List;

public interface WatchListClient {

    List<WatchedItem> getWatchedItems(String userId);
}
