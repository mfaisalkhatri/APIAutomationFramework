package io.github.mfaisalkhatri.restfulecommerce;

import io.github.mfaisalkhatri.client.ApiRequestContext;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected ApiRequestContext apiRequestContext;

    @BeforeClass
    public void setup () {
        this.apiRequestContext = new ApiRequestContext ();
    }
}
