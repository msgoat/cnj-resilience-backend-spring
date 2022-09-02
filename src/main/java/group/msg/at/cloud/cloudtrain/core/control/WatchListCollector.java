package group.msg.at.cloud.cloudtrain.core.control;

import group.msg.at.cloud.cloudtrain.adapter.rest.out.watchlist.WatchListClient;
import group.msg.at.cloud.cloudtrain.core.entity.WatchedItem;
import group.msg.at.cloud.cloudtrain.core.entity.WelcomeItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WatchListCollector {

    @Autowired
    WatchListClient client;

    public void collect(WelcomeItems target, String userId) {
        List<WatchedItem> watchList = client.getWatchedItems(userId);
        target.setWatchList(watchList);
    }
}
