package com.restaurant.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.security.CustomUserDetails;
import com.restaurant.security.CustomUserDetailsService;
import com.restaurant.security.JwtFilter;
import com.restaurant.security.JwtUtil;

@WebMvcTest(controllers = SecurityConfigTest.ProbeController.class)
@Import({SecurityConfig.class, JwtFilter.class, JwtUtil.class})
@TestPropertySource(properties = {
        "jwt.secret=test-only-jwt-signing-secret-that-is-over-64-bytes-long-for-hs256",
        "jwt.expiration-ms=3600000"
})
class SecurityConfigTest {

    @SpringBootConfiguration
    @EnableAutoConfiguration(exclude = {
            DataSourceAutoConfiguration.class,
            UserDetailsServiceAutoConfiguration.class
    })
    @Import(ProbeController.class)
    static class TestApp {
    }

    @RestController
    static class ProbeController {
        @GetMapping("/accounting/tally-xml")
        public Map<String, String> accounting() { return Map.of("ok", "true"); }

        @GetMapping("/reservation")
        public Map<String, String> getReservations() { return Map.of("ok", "true"); }

        @PostMapping("/reservation")
        public Map<String, String> createReservation() { return Map.of("ok", "true"); }

        @PostMapping("/notifications/whatsapp/send")
        public Map<String, String> sendNotification() { return Map.of("ok", "true"); }

        @PostMapping("/recipe")
        public Map<String, String> createRecipe() { return Map.of("ok", "true"); }

        @GetMapping("/modifiers/item/{id}")
        public Map<String, String> getModifiers(@PathVariable int id) { return Map.of("ok", "true"); }

        @PostMapping("/modifiers")
        public Map<String, String> createModifier() { return Map.of("ok", "true"); }

        @GetMapping("/payment/qr-code/{id}")
        public Map<String, String> qrCode(@PathVariable int id) { return Map.of("ok", "true"); }

        @GetMapping("/bills")
        public Map<String, String> listBillsPlural() { return Map.of("ok", "true"); }

        @GetMapping("/menu")
        public Map<String, String> getMenu() { return Map.of("ok", "true"); }

        @PostMapping("/menus")
        public Map<String, String> createMenuPlural() { return Map.of("ok", "true"); }

        @PostMapping("/categories")
        public Map<String, String> createCategoryPlural() { return Map.of("ok", "true"); }

        @GetMapping("/users")
        public Map<String, String> listUsers() { return Map.of("ok", "true"); }

        @PostMapping("/auth/login")
        public Map<String, String> login() { return Map.of("ok", "true"); }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    private String adminToken;
    private String staffToken;
    private String chefToken;

    @BeforeEach
    void setUp() {
        CustomUserDetails admin = new CustomUserDetails(1, "admin", "pw", "ADMIN", 1, "Sankalp");
        CustomUserDetails staff = new CustomUserDetails(2, "staff", "pw", "STAFF", 1, "Sankalp");
        CustomUserDetails chef  = new CustomUserDetails(3, "chef",  "pw", "CHEF",  1, "Sankalp");

        Mockito.when(userDetailsService.loadUserByUsername("admin")).thenReturn(admin);
        Mockito.when(userDetailsService.loadUserByUsername("staff")).thenReturn(staff);
        Mockito.when(userDetailsService.loadUserByUsername("chef")).thenReturn(chef);

        adminToken = jwtUtil.generateToken("admin");
        staffToken = jwtUtil.generateToken("staff");
        chefToken  = jwtUtil.generateToken("chef");
    }

    @Test
    void publicEndpointsRemainAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/menu")).andExpect(status().isOk());
        mockMvc.perform(get("/modifiers/item/1")).andExpect(status().isOk());
        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void sensitiveEndpointsRejectUnauthenticatedRequests() throws Exception {
        mockMvc.perform(get("/accounting/tally-xml")).andExpect(status().isForbidden());
        mockMvc.perform(get("/reservation")).andExpect(status().isForbidden());
        mockMvc.perform(post("/notifications/whatsapp/send")).andExpect(status().isForbidden());
        mockMvc.perform(post("/recipe")).andExpect(status().isForbidden());
        mockMvc.perform(post("/modifiers")).andExpect(status().isForbidden());
        mockMvc.perform(get("/payment/qr-code/1")).andExpect(status().isForbidden());
        mockMvc.perform(get("/bills")).andExpect(status().isForbidden());
        mockMvc.perform(post("/menus")).andExpect(status().isForbidden());
        mockMvc.perform(post("/categories")).andExpect(status().isForbidden());
        mockMvc.perform(get("/users")).andExpect(status().isForbidden());
    }

    @Test
    void roleBasedAccessControlEnforcedAcrossSingularAndPluralRoutes() throws Exception {
        // CHEF cannot access billing, accounting, reservations, or admin menu writes
        mockMvc.perform(get("/bills").header("Authorization", "Bearer " + chefToken))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/accounting/tally-xml").header("Authorization", "Bearer " + chefToken))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/menus").header("Authorization", "Bearer " + chefToken))
                .andExpect(status().isForbidden());
        // CHEF can manage recipes
        mockMvc.perform(post("/recipe").header("Authorization", "Bearer " + chefToken))
                .andExpect(status().isOk());

        // STAFF cannot access accounting, admin menu/category writes, or recipes
        mockMvc.perform(get("/accounting/tally-xml").header("Authorization", "Bearer " + staffToken))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/menus").header("Authorization", "Bearer " + staffToken))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/categories").header("Authorization", "Bearer " + staffToken))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/modifiers").header("Authorization", "Bearer " + staffToken))
                .andExpect(status().isForbidden());
        // STAFF can access bills, reservations GET, payment QR, notifications
        mockMvc.perform(get("/bills").header("Authorization", "Bearer " + staffToken))
                .andExpect(status().isOk());
        mockMvc.perform(get("/reservation").header("Authorization", "Bearer " + staffToken))
                .andExpect(status().isOk());
        mockMvc.perform(get("/payment/qr-code/1").header("Authorization", "Bearer " + staffToken))
                .andExpect(status().isOk());
        mockMvc.perform(post("/notifications/whatsapp/send").header("Authorization", "Bearer " + staffToken))
                .andExpect(status().isOk());

        // ADMIN can access accounting, users, and plural menu/category/modifier writes
        mockMvc.perform(get("/accounting/tally-xml").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        mockMvc.perform(get("/users").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        mockMvc.perform(post("/menus").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        mockMvc.perform(post("/categories").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        mockMvc.perform(post("/modifiers").header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }
}
