package group.msg.at.cloud.cloudtrain.core.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class RecommendedItem {

    private UUID itemId;

    private String itemName;

    private BigDecimal weight;

    public RecommendedItem() {

    }

    public RecommendedItem(UUID itemId, String itemName, BigDecimal weight) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.weight = weight;
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
