package org.linker.provider.controller;

import org.apache.commons.lang.StringUtils;
import org.linker.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RWM
 * @date 2018/11/28
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public String findOne(@PathVariable Long id) {
        return userService.findOne(id);
    }

    @GetMapping("/users")
    public List<String> findAll(@RequestParam String ids) {
        List<String> idsStr = Arrays.asList(StringUtils.split(ids, ","));
        return userService.findAll(idsStr.stream().map(Long::parseLong).collect(Collectors.toList()));
    }
}
