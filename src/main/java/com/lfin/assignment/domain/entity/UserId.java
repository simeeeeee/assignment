package com.lfin.assignment.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
public class UserId {

    private UUID userId;

    private UserId(final UUID userId){
        this.userId = userId;
    }

    public static UserId create(){
        return new UserId(UUID.randomUUID());
    }
}
