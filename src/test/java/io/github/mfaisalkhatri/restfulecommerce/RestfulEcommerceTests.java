package io.github.mfaisalkhatri.restfulecommerce;

import static com.google.common.truth.Truth.assertThat;

import java.util.HashMap;
import java.util.Map;

import io.github.mfaisalkhatri.request.ApiRequest;
import io.github.mfaisalkhatri.response.ApiResponse;
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
}
