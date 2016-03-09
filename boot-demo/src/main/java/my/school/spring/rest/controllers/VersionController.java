package my.school.spring.rest.controllers;

import my.school.spring.beans.CustomDataProvider;
import my.school.spring.beans.Profiling;
import my.school.spring.services.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergii Krymets <skrymets@intropro.com>
 */
@RestController
@EnableAutoConfiguration
//@Profiling
public class VersionController {

    @Autowired
    private EchoService echoService;

    @CustomDataProvider(seed = "NewValue")
    private String data;

    @RequestMapping(
            path = "/version",
            produces = "application/json"
    )
    public String showVersion() {
        echoService.showMessage();
        return "1.0.0" + "-" + data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
