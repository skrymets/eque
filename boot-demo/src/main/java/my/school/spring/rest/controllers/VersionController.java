package my.school.spring.rest.controllers;

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
public class VersionController {

    @Autowired
    private EchoService echoService;

    @Autowired
    private VersionDataProvider versionDataProvider;
    
    @RequestMapping(
            path = "/version",
            produces = "application/json"
    )
    public String showVersion() {
        echoService.showMessage();
        return versionDataProvider.getVersionInfo();
    }
}
