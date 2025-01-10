package com.tntteam.tntdropbox.config;

import com.tntteam.tntdropbox.models.File;
import com.tntteam.tntdropbox.models.Group;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.repositories.FileRepository;
import com.tntteam.tntdropbox.repositories.GroupRepository;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;

    public DatabaseInitializer(UserRepository userRepository, FileRepository fileRepository,
                               BCryptPasswordEncoder passwordEncoder, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.passwordEncoder = passwordEncoder;
        this.groupRepository = groupRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setFirstName("Test");
        user.setLastName("User");
        userRepository.save(user);

        // Creating and saving the Group
        Group group = new Group();
        group.setName("Test Group");
        group.setCreatedAt(LocalDateTime.now());
        group.setAdmin(user);
        groupRepository.save(group);

        user.setGroups(List.of(group));
        userRepository.save(user);

        File file1 = new File();
        file1.setName("file1.txt");
        file1.setFileData("Sample content for file 1".getBytes());
        file1.setSize((long) file1.getFileData().length);
        file1.setType("text/plain");
        file1.setCreatedAt(LocalDateTime.now());
        file1.setUpdatedAt(LocalDateTime.now());
        file1.setUser(user);
        file1.setGroup(group);
        fileRepository.save(file1);

        File file2 = new File();
        file2.setName("file2.jpg");
        file2.setFileData(new byte[]{});
        file2.setSize(1024L);
        file2.setType("image/jpeg");
        file2.setCreatedAt(LocalDateTime.now());
        file2.setUpdatedAt(LocalDateTime.now());
        file2.setUser(user);
        fileRepository.save(file2);

        System.out.println("Database seeded with test data.");
    }

}
