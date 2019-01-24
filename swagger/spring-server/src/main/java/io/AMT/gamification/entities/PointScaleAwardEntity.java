package io.AMT.gamification.entities;

import javax.persistence.*;

@Entity
public class PointScaleAwardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private PointScaleEntity pointScaleEntity;

    @ManyToOne
    private UserEntity userEntity;

    private int amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PointScaleEntity getPointScaleEntity() {
        return pointScaleEntity;
    }

    public void setPointScaleEntity(PointScaleEntity pointScaleEntity) {
        this.pointScaleEntity = pointScaleEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
