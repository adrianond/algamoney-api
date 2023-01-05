package com.algamoney.api.config;

import lombok.Data;

import java.util.Optional;

@Data
public class MarginConfig {

    private Double left;
    private Double right;
    private Double top;
    private Double bottom;

    private Double valueOrDefault(Double value) {
        return Optional.ofNullable(value).orElse(5.0);
    }

    public double getLeft() {
        return valueOrDefault(left);
    }

    public double getRight() {
        return valueOrDefault(right);
    }

    public double getTop() {
        return valueOrDefault(top);
    }

    public double getBottom() {
        return valueOrDefault(bottom);
    }

    public void setLeft(Double left) {
        this.left = valueOrDefault(left);
    }

    public void setRight(Double right) {
        this.right = valueOrDefault(right);
    }

    public void setTop(Double top) {
        this.top = valueOrDefault(top);
    }

    public void setBottom(Double bottom) {
        this.bottom = valueOrDefault(bottom);
    }
}
