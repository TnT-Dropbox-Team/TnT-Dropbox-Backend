package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.models.Test;
import com.tntteam.tntdropbox.repositories.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class TestService {
    private TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }
    public Test getTest(@PathVariable long id) {
        return testRepository.findById(id).orElse(null);
    }
    public void uploadTest(Test test) {
        testRepository.save(test);
    }
}
