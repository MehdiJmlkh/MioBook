package ir.ac.ut.ece.ie.purchases;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {
    @MockitoBean
    PurchaseService purchaseService;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void credits_access_allowed_for_customer() throws Exception {
        when(purchaseService.getAllPurchases(any())).thenReturn(new PurchaseHistoryDto());

        mockMvc.perform(get("/purchases/username"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void credits_access_denied_for_admin() throws Exception {
        mockMvc.perform(get("/purchases/username"))
                .andExpect(status().isForbidden());
    }
}
