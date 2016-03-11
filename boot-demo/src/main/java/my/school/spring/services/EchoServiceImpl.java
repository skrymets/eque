package my.school.spring.services;

import my.school.spring.beans.PostProxy;
import my.school.spring.rest.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skrymets
 */
@Component
public class EchoServiceImpl implements EchoService {
    
    private static final Logger LOG = LoggerFactory.getLogger(EchoServiceImpl.class);
    
    @Autowired
    private Settings settings;
    
    @Override
    @PostProxy
    public void showMessage() {
        LOG.info("{}\n{}", settings.getTitle(), settings.getMessage());
    }

}
