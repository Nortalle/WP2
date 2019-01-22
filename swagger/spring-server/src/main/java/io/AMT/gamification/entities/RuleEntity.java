package io.AMT.gamification.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class RuleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String event;
    private String propertyName;
    private String propertyCondition;

    private


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
