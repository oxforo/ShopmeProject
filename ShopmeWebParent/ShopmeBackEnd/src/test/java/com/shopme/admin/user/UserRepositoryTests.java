package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userSM = new User("oxforo@naver.com", "sumin2022", "Sumin", "Chae");
        userSM.addRole(roleAdmin);

        User savedUser = userRepository.save(userSM);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRole() {
        User userRavi = new User("ravi@gmail.com", "ravi2022", "Ravi", "Kumar");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssistant);

        User savedUser = userRepository.save(userRavi);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(user -> System.out.println("user = " + user));
    }

    @Test
    public void testGetUSerById() {
        User userSM = userRepository.findById(1).get();
        System.out.println("userSM = " + userSM);
        assertThat(userSM).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userSM = userRepository.findById(1).get();
        userSM.setEnabled(true);
        userSM.setEmail("suminjavaprogrammer@gmail.com");

        userRepository.save(userSM);
    }

    @Test
    public void testUpdateUserRoles() {
        User userRavi = userRepository.findById(3).get();

        Role roleEditor = new Role(3);
        Role roleSalespserson = new Role(2);

        userRavi.removeRole(roleEditor);
        userRavi.addRole(roleSalespserson);
        userRepository.save(userRavi);
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 2;
        userRepository.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "ravi@gmail.com";
        User user = userRepository.getUserByEmail(email);

        assertThat(user).isNotNull();
    }
}