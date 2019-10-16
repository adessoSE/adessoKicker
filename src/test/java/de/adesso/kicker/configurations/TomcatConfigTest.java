package de.adesso.kicker.configurations;

import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import({ KeycloakAutoConfiguration.class, TomcatConfig.class })
@TestPropertySource("classpath:application-test.properties")
class TomcatConfigTest {

    @Test
    void checkPortRedirection() throws Exception {
        // given
        URL url = new URL("http://localhost/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // when
        con.connect();

        // then
        assertEquals("Status should be 302", con.getResponseCode(), 302);
        assertEquals("Redirect location should be https", con.getHeaderField("Location"), "https://localhost/");
        con.disconnect();
    }
}
