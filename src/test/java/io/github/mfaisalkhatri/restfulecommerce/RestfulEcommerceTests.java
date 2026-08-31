package io.github.mfaisalkhatri.restfulecommerce;

import static com.google.common.truth.Truth.assertThat;

import java.util.HashMap;
import java.util.Map;

import io.github.mfaisalkhatri.request.ApiRequest;
import io.github.mfaisalkhatri.response.ApiResponse;
import io.github.mfaisalkhatri.restfulecommerce.pojo.Order;
import io.github.mfaisalkhatri.restfulecommerce.pojo.OrderResponse;
import org.testng.annotations.Test;

public class RestfulEcommerceTests extends BaseTest {

    @Test
    public void testPOSTOrder () {
        final String orderBody = """
            [{
                "user_id": "1",
                "product_id": "1",
                "product_name": "iPhone",
                "product_amount": 500.00,
                "qty": 1,
                "tax_amt": 5.99,
                "total_amt": 505.99
            },
            {
                "user_id": "1",
                "product_id": "2",
                "product_name": "iPad",
                "product_amount": 699.00,
                "qty": 1,
                "tax_amt": 7.99,
                "total_amt": 706.99
            }]\
            """;
        final Map<String, String> headers = new HashMap<> ();
        headers.put ("accept", "application/json");
        headers.put ("Content-Type", "application/json");

        final ApiRequest request = ApiRequest.builder ()
            .post ()
            .endpoint ("/addOrder")
            .headers (headers)
            .body (orderBody)
            .build ();

        final ApiResponse response = this.apiRequestContext.execute (request);

        assertThat (response.getStatusCode ()).isEqualTo (201);
    }

    @Test
    public void testGETOrder () {
        final ApiRequest request = ApiRequest.builder ()
            .get ()
            .endpoint ("/getAllOrders")
            .build ();

        final ApiResponse response = this.apiRequestContext.execute (request);
        assertThat (response.getStatusCode ()).isEqualTo (200);
        final OrderResponse orderResponse = response.getBodyAs (OrderResponse.class);
        assertThat (orderResponse.getMessage ()).isEqualTo ("Orders fetched successfully!");
        assertThat (orderResponse.getOrders ()).hasSize (2);

        final Order firstOrder = orderResponse.getOrders ()
            .get (0);
        assertThat (firstOrder.getId ()).isEqualTo (1);
        assertThat (firstOrder.getUser_id ()).isEqualTo ("1");
        assertThat (firstOrder.getProduct_id ()).isEqualTo ("1");
        assertThat (firstOrder.getProduct_amount ()).isEqualTo (500);
        assertThat (firstOrder.getQty ()).isEqualTo (1);
        assertThat (firstOrder.getTax_amt ()).isEqualTo (5.99);
        assertThat (firstOrder.getTotal_amt ()).isEqualTo (505.99);
    }
}
