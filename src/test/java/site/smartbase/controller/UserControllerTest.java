package site.smartbase.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import site.smartbase.exception.handler.CustomExceptionHandler;
import site.smartbase.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new CustomExceptionHandler(errorAttributes))
                .build();
    }

    @Test
    void setStatusTest() throws Exception {
        Long userId = 1L;
        Boolean status = false;

        doNothing().when(userService).setStatus(userId, status);
        mockMvc.perform(put("/api/users/status/" + userId)
                        .param("status", String.valueOf(status)))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).setStatus(userId, status);
    }

    @Test
    void setStatus_BadRequestTest() throws Exception {
        Long userId = 1L;

        mockMvc.perform(put("/api/users/status/" + userId))
                .andExpect(status().isBadRequest());

        verify(userService, times(0)).setStatus(eq(userId), anyBoolean());
    }
}
