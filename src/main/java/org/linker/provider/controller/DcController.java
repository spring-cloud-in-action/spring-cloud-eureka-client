package org.linker.provider.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RWM
 * @date 2018/8/20
 */
@RestController
@Api(description = "微服务相关接口")
public class DcController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/dc")
    @ApiOperation("服务发现")
    public List<String> dc() {
        List<String> services = discoveryClient.getServices();
        return services;
    }
}
