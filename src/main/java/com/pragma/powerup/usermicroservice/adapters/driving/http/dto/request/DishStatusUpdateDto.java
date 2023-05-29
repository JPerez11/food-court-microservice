package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;

public class DishStatusUpdateDto {

    @NotNull(message = "The state cannot be empty")
    private boolean active;

    public DishStatusUpdateDto() {
    }

    public DishStatusUpdateDto(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
