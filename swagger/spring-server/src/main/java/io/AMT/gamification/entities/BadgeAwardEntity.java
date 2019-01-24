package io.AMT.gamification.entities;

import javax.persistence.*;

@Entity
public class BadgeAwardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private BadgeEntity badgeEntity;

    @ManyToOne
    private UserEntity userEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BadgeEntity getBadgeEntity() {
        return badgeEntity;
    }

    public void setBadgeEntity(BadgeEntity badgeEntity) {
        this.badgeEntity = badgeEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
