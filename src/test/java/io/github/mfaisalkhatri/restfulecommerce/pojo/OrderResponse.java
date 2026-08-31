package io.github.mfaisalkhatri.restfulecommerce.pojo;

import java.util.List;

import lombok.Getter;

@Getter
public class OrderResponse {
    private String      message;
    private List<Order> orders;
}
