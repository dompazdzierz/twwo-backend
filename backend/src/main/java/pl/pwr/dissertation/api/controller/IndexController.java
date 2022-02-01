package pl.pwr.dissertation.api.controller;

import org.springframework.web.bind.annotation.GetMapping;

public interface IndexController {

    @GetMapping("/")
    String index();
}
