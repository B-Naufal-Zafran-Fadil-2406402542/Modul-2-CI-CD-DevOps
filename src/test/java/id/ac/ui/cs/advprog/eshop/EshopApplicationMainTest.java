package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class EshopApplicationMainTest {

    @InjectMocks
    private EshopApplication eshopApplication;

    @Test
    void main() {
        try (var mocked = mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(any(Class.class), any(String[].class))).thenReturn(null);
            EshopApplication.main(new String[]{});
            mocked.verify(() -> SpringApplication.run(EshopApplication.class, new String[]{}));
        }
    }
}
