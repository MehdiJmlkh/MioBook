package ir.ac.ut.ece.ie.carts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {
    @MockitoBean
    CartService cartService;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void carts_access_allowed_for_customer() throws Exception {
        when(cartService.getCart(any())).thenReturn(new CartDto());

        mockMvc.perform(get("/carts/username"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void carts_access_denied_for_admin() throws Exception {
        mockMvc.perform(get("/carts/username"))
                .andExpect(status().isForbidden());
    }
}