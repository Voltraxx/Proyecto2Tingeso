package main.core.Backend.Services;

import main.core.Backend.Entities.User;
import main.core.Backend.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        // Arrange
        ArrayList<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User());
        mockUsers.add(new User());
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        ArrayList<User> users = userService.getUsers();

        // Assert
        assertThat(users).hasSize(2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act
        User user = userService.getUserById(1L);

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveUser() {
        // Arrange
        User mockUser = new User();
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        // Act
        User savedUser = userService.saveUser(mockUser);

        // Assert
        assertThat(savedUser).isNotNull();
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        User mockUser = new User();
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        // Act
        User updatedUser = userService.updateUser(mockUser);

        // Assert
        assertThat(updatedUser).isNotNull();
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testDeleteUser() throws Exception {
        // Arrange
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        boolean result = userService.deleteUser(userId);

        // Assert
        assertThat(result).isTrue();
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserThrowsException() {
        // Arrange
        Long userId = 1L;
        doThrow(new RuntimeException("User not found")).when(userRepository).deleteById(userId);

        // Act & Assert
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            userService.deleteUser(userId);
        });
        assertThat(exception.getMessage()).isEqualTo("User not found");
        verify(userRepository, times(1)).deleteById(userId);
    }
}
