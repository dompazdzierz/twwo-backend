package pl.pwr.dissertation.api.controller.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.dissertation.api.controller.IndexController;


@RequestMapping
@RestController
public class IndexControllerImpl implements IndexController {

    @Override
    public String index() {
        return "Hello";
    }
}
