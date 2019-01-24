package io.AMT.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
public class UserEntity implements Serializable {

    @Id
    private long id;

    private String apiKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
