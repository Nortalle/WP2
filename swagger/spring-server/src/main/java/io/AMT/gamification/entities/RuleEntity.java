package io.AMT.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class RuleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String apiKey;

    private String ifEventType;

    private String ifPropertyName;

    private String ifPropertyCondition;

    @ManyToOne
    private BadgeEntity badge;

    private Long thenPointScaleId;

    private int thenAwardPoint;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIfEventType() {
        return ifEventType;
    }

    public void setIfEventType(String ifEventType) {
        this.ifEventType = ifEventType;
    }

    public String getIfPropertyName() {
        return ifPropertyName;
    }

    public void setIfPropertyName(String ifPropertyName) {
        this.ifPropertyName = ifPropertyName;
    }

    public String getIfPropertyCondition() {
        return ifPropertyCondition;
    }

    public void setIfPropertyCondition(String ifPropertyCondition) {
        this.ifPropertyCondition = ifPropertyCondition;
    }

    public Long getThenPointScaleId() {
        return thenPointScaleId;
    }

    public void setThenPointScaleId(Long thenPointScaleId) {
        this.thenPointScaleId = thenPointScaleId;
    }

    public int getThenAwardPoint() {
        return thenAwardPoint;
    }

    public void setThenAwardPoint(int thenAwardPoint) {
        this.thenAwardPoint = thenAwardPoint;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public BadgeEntity getBadge() {
        return badge;
    }

    public void setBadge(BadgeEntity badge) {
        this.badge = badge;
    }
}
