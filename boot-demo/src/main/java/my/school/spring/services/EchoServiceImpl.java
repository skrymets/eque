package my.school.spring.services;

import my.school.spring.rest.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author skrymets
 */
@Component
public class EchoServiceImpl implements EchoService {
    
    @Autowired
    private Settings settings;
    
    @Override
    public void showMessage() {
        System.out.println("!!!!!!!!!!\n" + settings.getTitle() + "\n" + settings.getMessage());
    }

}
