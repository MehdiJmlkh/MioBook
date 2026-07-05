package ir.ac.ut.ece.ie.carts;

import ir.ac.ut.ece.ie.utils.EndpointTestCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {
    @Autowired
    MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("protectedEndpoints")
    @WithMockUser(roles = "ADMIN")
    void admin_cannot_access_protected_endpoint(EndpointTestCase tc) throws Exception {
        mockMvc.perform(request(tc.method(), tc.url()))
                .andExpect(status().isForbidden());
    }

    static Stream<EndpointTestCase> protectedEndpoints() {
        return Stream.of(
                new EndpointTestCase("/carts/username", HttpMethod.GET),
                new EndpointTestCase("/carts/items", HttpMethod.POST),
                new EndpointTestCase("/carts/borrowed-items", HttpMethod.POST),
                new EndpointTestCase("/carts/items/1", HttpMethod.DELETE),
                new EndpointTestCase("/carts/purchase", HttpMethod.POST)
        );
    }
}