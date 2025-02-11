package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.dto.*;
import com.example.topichubbackend.exceptions.*;
import com.example.topichubbackend.mapper.*;
import com.example.topichubbackend.model.*;
import com.example.topichubbackend.repository.*;
import org.jetbrains.annotations.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;

    @InjectMocks
    AuthService authService;

    UUID authorId = UUID.randomUUID();

    @Test
    void change_user_status(){
        var user = User.builder()
                .uuid(authorId)
                .status(StatusDto.ACTIVE.type())
                .login("author")
                .password("123456")
                .email("email@mail.ru")
                .roles(new ArrayList<>())
                .build();
        when(userRepository.findById(authorId)).thenReturn(Optional.of(user));
        doReturn(user).when(userRepository).save(user);
        when(userMapper.toDto(user)).thenReturn(UserDto.builder()
                        .id(authorId.toString())
                        .status(StatusDto.BLOCK.type())
                        .email(user.getEmail())
                .build());
        assertDoesNotThrow(()->{
            var userDto = authService.manageBlock(authorId.toString(),StatusDto.BLOCK.type());
            assertEquals(StatusDto.BLOCK.type(), userDto.getStatus());
            assertEquals(user.getEmail(), userDto.getEmail());
        });
    }

    @Test
    void manage_not_exists_user(){
        when(userRepository.findById(authorId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->{
            authService.manageBlock(authorId.toString(),StatusDto.BLOCK.type());
        });
    }

    @Test
    void testSearchForAdmin() {
        String login = "login";
        String email = "email@mail.ru";
        Integer page = 1;
        Boolean isAdmin = true;

        User user = new User();
        Page<User> users = new PageImpl<>(Collections.singletonList(user), PageRequest.of(page - 1, 15), 1);
        when(userRepository.searchByLoginOrEmail(eq(login), eq(email), any(PageRequest.class)))
                .thenReturn(users);

        UserDto userDto = new UserDto();
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        PageResponse<UserDto> response = authService.search(login, email, page, isAdmin);

        assertEquals(1, response.getItems().size());
        assertEquals(userDto, response.getItems().get(0));
    }

    @Test
    void testSearchForNonAdmin() {
        String login = "login";
        String email = "email@mail.ru";
        Integer page = 1;
        Boolean isAdmin = false;

        User user = new User();
        Page<User> users = new PageImpl<>(Collections.singletonList(user), PageRequest.of(page - 1, 15), 1);


        when(userRepository.searchByLoginOrEmail(eq(login), eq(email), any(PageRequest.class)))
                .thenReturn(users);

        UserDto userDto = new UserDto();
        when(userMapper.toAuthor(any(User.class))).thenReturn(userDto);

        PageResponse<UserDto> response = authService.search(login, email, page, isAdmin);
        assertEquals(1, response.getItems().size());
        assertEquals(userDto, response.getItems().get(0));
    }

}