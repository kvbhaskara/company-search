package com.company.search;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CompanySearchRestTest {

    @Autowired
    private MockMvc mockMvc;

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8089));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);

        // Mock Company Search Response
        wireMockServer.stubFor(get(WireMock.urlMatching("/rest/Companies/v1/Search.*"))
                .willReturn(aResponse()
                        .withBodyFile("company-response.json")
                        .withHeader("Content-Type", "application/json")));

        // Mock Company Officers Response
        wireMockServer.stubFor(get(WireMock.urlMatching("/rest/Companies/v1/Officers.*"))
                .willReturn(aResponse()
                        .withBodyFile("officers-response.json")
                        .withHeader("Content-Type", "application/json")));
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void testGetCompany() throws Exception {
        mockMvc.perform(post("/api/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-api-key", "test-api-key")
                        .param("onlyActive", "true")
                        .content("{\"companyName\": \"BBC LIMITED\", \"companyNumber\": \"06500244\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].companyNumber").value("06500244"));
    }
}