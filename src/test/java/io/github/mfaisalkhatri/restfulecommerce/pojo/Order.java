package io.github.mfaisalkhatri.restfulecommerce.pojo;

import lombok.Data;

@Data
public class Order {
    private int    id;
    private String user_id;
    private String product_id;
    private String product_name;
    private int    product_amount;
    private int    qty;
    private double tax_amt;
    private double total_amt;
}
