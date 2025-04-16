// package com.example.meetscribe;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
// class MeetscribeApplicationTests {

// 	@Test
// 	void contextLoads() {
// 	}

// }


package com.example.meetscribe;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TranscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void successCase_startEndpointShouldReturnOK() throws Exception {
        mockMvc.perform(get("/start"))
                .andExpect(status().isOk()); // ✅ Expected to pass
    }

    @Test
    public void failureCase_invalidEndpointShouldReturn404() throws Exception {
        mockMvc.perform(get("/invalid-endpoint"))
                .andExpect(status().isNotFound()); // ❌ Expected to fail (simulate a regression)
    }
}

