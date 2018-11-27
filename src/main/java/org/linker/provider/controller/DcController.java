package org.linker.provider.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.linker.provider.collapser.DcCollapseCommand;
import org.linker.provider.service.DcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author RWM
 * @date 2018/8/20
 */
@RestController
@Api(description = "微服务相关接口")
public class DcController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private DcService dcService;

    /*@GetMapping("/dc")
    @ApiOperation("服务发现")
    public List<String> dc() {
        List<String> services = discoveryClient.getServices();
        return services;
    }*/

    @GetMapping("/dc/{id}")
    public String dc(@PathVariable Long id) {
        return dcService.findOne(id);
    }

    @GetMapping("/dc")
    public List<String> dcs(@RequestParam String ids) {
        String[] idArray = StringUtils.split(ids, ",");
        List<Long> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Long.parseLong(id));
        }
        return dcService.findAll(idList);
    }

    @GetMapping("/collapse")
    public void collapse() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<String> f1 = new DcCollapseCommand(dcService, 1L).queue();
            Future<String> f2 = new DcCollapseCommand(dcService, 2L).queue();
            Future<String> f3 = new DcCollapseCommand(dcService, 3L).queue();
            Thread.sleep(100);

            Future<String> f4 = new DcCollapseCommand(dcService, 4L).queue();
            Future<String> f5 = new DcCollapseCommand(dcService, 5L).queue();

            String s1 = f1.get();
            String s2 = f2.get();
            String s3 = f3.get();
            String s4 = f4.get();
            String s5 = f5.get();

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s3);
            System.out.println(s4);
            System.out.println(s5);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.close();
        }

    }

}
