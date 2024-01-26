package ru.ylab;


import ru.ylab.configs.Config;
import ru.ylab.configs.JavaConfig;

import java.util.Map;

public class Application {

    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifcToImplClass) throws Exception {
        Config config = new JavaConfig(packageToScan, ifcToImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);

        context.setFactory(objectFactory);

        return context;
    }
}
