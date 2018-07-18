package com.hxg.quartz.demo.cluster;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConfigController {

    @Value("#{'${monitor.url}'.split(',')}")
    private List<String> monitorUrl;

    @GetMapping("/test")
    public List<String> test() {
        System.out.println(monitorUrl.get(0));
        return monitorUrl;
    }

}
