package group.msg.at.cloud.cloudtrain.core.control;

import group.msg.at.cloud.cloudtrain.adapter.rest.out.watchlist.WatchListClient;
import group.msg.at.cloud.cloudtrain.core.entity.WatchedItem;
import group.msg.at.cloud.cloudtrain.core.entity.WelcomeItems;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResilientWatchListCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResilientWatchListCollector.class);

    @Autowired
    WatchListClient client;

    @CircuitBreaker(name = "downstreamB", fallbackMethod = "collectAfterFailure")
    public void collect(WelcomeItems target, String userId) {
        LOGGER.info("retrieving watch list...");
        List<WatchedItem> watchList = client.getWatchedItems(userId);
        target.setWatchList(watchList);
        LOGGER.info("...succeeded to retrieve watch list");
    }

    public void collectAfterFailure(WelcomeItems target, String userId, Exception caught) {
        LOGGER.info("...failed to retrieve watch list");
        target.addStatusMessage("watch list is temporarily unavailable!");
    }
}
