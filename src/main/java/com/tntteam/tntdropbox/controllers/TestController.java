package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.models.Test;
import com.tntteam.tntdropbox.services.TestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }
    @GetMapping("{id}")
    public Test getTest(@PathVariable long id) {
        return testService.getTest(id);
    }
    @PostMapping
    public void uploadTest(@RequestBody Test test) {
        testService.uploadTest(test);

    }
}
