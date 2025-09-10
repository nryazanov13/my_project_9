package lesson9;

import com.fasterxml.jackson.databind.ObjectMapper;
import lesson9.model.Settings;
import lesson9.model.User;
import lesson9.model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTests {

    private final ClassLoader cl = JsonTests.class.getClassLoader();
    private final ObjectMapper mapper = new ObjectMapper();
    private UserData userData;

    @BeforeEach
    void setUp() throws Exception {
        try(Reader reader = new InputStreamReader(cl.getResourceAsStream("userdata.json"))){
            userData = mapper.readValue(reader, UserData.class);
        }
    }

    @Test
    @DisplayName("Проверить десериализацию json файла")
    void checkDeserializableTest() {
        // Теперь используем поле userData вместо повторного чтения
        assertEquals("Test App", userData.getApplication());
        assertEquals("1.0.0", userData.getVersion());
        assertEquals(2, userData.getUsers().size());
        assertEquals(100, userData.getSettings().getMaxUsers());
        assertEquals("DARK", userData.getSettings().getTheme());
        assertEquals(3, userData.getSettings().getFeaturesEnabled().size());
        assertTrue(userData.getSettings().getFeaturesEnabled()
                .containsAll(List.of("AUTH", "LOGGING", "NOTIFICATIONS")));
    }

    @Test
    @DisplayName("Проверить первого пользователя в json файла")
    void checkFirstUserTest() {
        User firstUser = userData.getUsers().get(0);

        assertEquals(101, firstUser.getId());
        assertEquals("john_doe", firstUser.getUsername());
        assertTrue(firstUser.isActive());
        assertTrue(firstUser.getPermissions().contains("WRITE"));
    }

    @Test
    @DisplayName("Проверить второго пользователя в json файла")
    void checkSecondUserTest() {
        User secondUser = userData.getUsers().get(1);

        assertEquals(102, secondUser.getId());
        assertEquals("jane_smith", secondUser.getUsername());
        assertFalse(secondUser.isActive());
        assertTrue(secondUser.getPermissions().contains("READ"));
    }

    @Test
    @DisplayName("Проверить раздел настройки в json файла")
    void checkSettingsTest() {
        Settings settings = userData.getSettings();

        assertEquals(100, settings.getMaxUsers());
        assertEquals("DARK", settings.getTheme());
        assertTrue(settings.getFeaturesEnabled().contains("AUTH"));
    }


}
