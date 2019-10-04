package com.primeholding.rushhour.controller;

import com.primeholding.rushhour.entity.User;
import com.primeholding.rushhour.model.UserGetModel;
import com.primeholding.rushhour.model.UserPostModel;
import com.primeholding.rushhour.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserController userController;

    private User user;

    private UserGetModel userGetModel;

    @Before
    public void init() {
        this.user = new User();
        this.userGetModel = new UserGetModel();
        UserPostModel userPostModel = new UserPostModel();
        user.setId(1);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("mail@mail.com");
        user.setPassword("mail");

        userPostModel.setFirstName(user.getFirstName());
        userPostModel.setLastName(user.getLastName());
        userPostModel.setEmail(user.getEmail());
        userPostModel.setPassword(user.getPassword());

//        when(userService.create(user)).thenReturn(Optional.of(user));
//        when(userService.update(user)).thenReturn(Optional.of(user));

//        when((modelMapper.map(user, UserPostModel.class))).thenReturn(userPostModel);
//        when(modelMapper.map(userPostModel, User.class)).thenReturn(user);
        when(modelMapper.map(user, UserGetModel.class)).thenReturn(userGetModel);
    }

    @Test
    public void getAllUsersTestShouldReturnAllUsers() {
        List<UserGetModel> userGetModels = new ArrayList<>();
        List<User> users = new ArrayList<>();
        userGetModels.add(modelMapper.map(user, UserGetModel.class));
        users.add(user);
        when(userService.getAll()).thenReturn(users);

        HttpEntity expectedResponse = ResponseEntity.ok(userGetModels);
        HttpEntity controllerResponse = userController.getAll();

        assertEquals(expectedResponse, controllerResponse);
    }

    @Test
    public void getUserByIdTestShouldReturnCorrespondingUser() {
      when(userService.get(user.getId())).thenReturn(Optional.of(user));

        HttpEntity expectedResponse = ResponseEntity.ok(modelMapper.map(user, UserGetModel.class));
        HttpEntity controllerResponse = userController.get(user.getId());

        assertEquals(expectedResponse, controllerResponse);
    }

    @Test
    public void createUserTestShouldReturnCreatedResponse(){
        when(userService.create(user)).thenReturn(Optional.of(user));

        HttpEntity expectedResponse = ResponseEntity.created()
    }




}

