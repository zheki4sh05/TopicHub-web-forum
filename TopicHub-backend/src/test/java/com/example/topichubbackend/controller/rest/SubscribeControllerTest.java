package com.example.topichubbackend.controller.rest;

import com.example.topichubbackend.security.util.*;
import com.example.topichubbackend.services.interfaces.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscribeControllerTest {


        @Mock
        private IReactionService reactionService;

        @Mock
        private CustomSecurityExpression customSecurityExpression;

        @InjectMocks
        private SubscribeController subscribeController;


    @Test
    public void testDoGetSubscribes() {
        String userId = "testUserId";
        when(customSecurityExpression.getUserId()).thenReturn(userId);
        when(reactionService.fetchAllSubscribes(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = subscribeController.doGet("subscribes");

        verify(customSecurityExpression).getUserId();
        verify(reactionService).fetchAllSubscribes(userId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody() instanceof List);
    }

}