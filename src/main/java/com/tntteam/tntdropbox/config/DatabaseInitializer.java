package com.tntteam.tntdropbox.config;

import com.tntteam.tntdropbox.models.*;
import com.tntteam.tntdropbox.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;

    public DatabaseInitializer(UserRepository userRepository, FileRepository fileRepository, BCryptPasswordEncoder passwordEncoder, GroupRepository groupRepository, CommentRepository commentRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.passwordEncoder = passwordEncoder;
        this.groupRepository = groupRepository;
        this.commentRepository = commentRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setFirstName("First");
        user.setLastName("User");
        user.setGroups(new ArrayList<>());
        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword(passwordEncoder.encode("test"));
        user2.setFirstName("Second");
        user2.setLastName("User");
        user2.setGroups(new ArrayList<>());
        userRepository.save(user2);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword(passwordEncoder.encode("test"));
        user3.setFirstName("Third");
        user3.setLastName("User");
        userRepository.save(user3);

        File file1 = new File();
        file1.setName("file1.txt");
        file1.setFileData("Sample content for file 1".getBytes());
        file1.setSize((long) file1.getFileData().length);
        file1.setType("text/plain");
        file1.setCreatedAt(LocalDateTime.now());
        file1.setUpdatedAt(LocalDateTime.now());
        file1.setUser(user);

        File file2 = new File();
        file2.setName("file2.jpg");
        file2.setFileData(new byte[]{});
        file2.setSize(1024L);
        file2.setType("image/jpeg");
        file2.setCreatedAt(LocalDateTime.now());
        file2.setUpdatedAt(LocalDateTime.now());
        file2.setUser(user);

        fileRepository.saveAll(Arrays.asList(file1, file2));

        Group group1 = new Group();
        group1.setName("group1");
        group1.setCreatedAt(LocalDateTime.now());
        group1.setAdmin(user);
        group1.setUsers(Arrays.asList(user, user2));
        groupRepository.save(group1);
        user.getGroups().add(group1);
        user2.getGroups().add(group1);
        userRepository.saveAll(Arrays.asList(user, user2));

        Comment comment1 = new Comment();
        comment1.setText("Hello World");
        comment1.setCreatedAt(LocalDateTime.now());
        comment1.setUser(user);
        comment1.setGroup(group1);
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setText("Happy new year!!!");
        comment2.setCreatedAt(LocalDateTime.now());
        comment2.setUser(user2);
        comment2.setGroup(group1);
        commentRepository.save(comment2);

        Notification notification1 = new Notification();
        notification1.setTitle("Testno obvestilo");
        notification1.setBody("To je vsebina testnega sporočila");
        notification1.setCreatedAt(LocalDateTime.now());
        notification1.setUser(user);
        notificationRepository.save(notification1);

        Notification notification2 = new Notification();
        notification2.setTitle("drugo sporočilo");
        notification2.setBody("Pošiljamo vam še eno testno sporočilo");
        notification2.setCreatedAt(LocalDateTime.now());
        notification2.setUser(user);
        notificationRepository.save(notification2);

        System.out.println("Database seeded with test data.");
    }

}
