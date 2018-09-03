package com.sample.cxs.countrycxs.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ClassPathResource;



/**
 * Utility class to load a Spring profile to be used as default
 * when there is no <code>spring.profiles.active</code> set in the environment or as command line argument.
 * If the value is not available in <code>application.yml</code> then <code>dev</code> profile will be used as default.
 */
public final class DefaultProfileUtil {

    private static final Logger logger = LoggerFactory.getLogger(DefaultProfileUtil.class);

    private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

    private static final Properties BUILD_PROPERTIES = readProperties();

	private static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    /**
     * Get a default profile from <code>application.yml</code>.
     */
    public static String getDefaultActiveProfiles(){
        if (BUILD_PROPERTIES != null) {
            String activeProfile = BUILD_PROPERTIES.getProperty(SPRING_PROFILE_ACTIVE);
            if (activeProfile != null && !activeProfile.isEmpty()) {
                return activeProfile;
            }
        }
        logger.warn("No Spring profile configured, running with default profile: {}", SPRING_PROFILE_DEVELOPMENT);
        return SPRING_PROFILE_DEVELOPMENT;
    }

    /**
     * Set a default to use when no profile is configured.
     */
    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties =  new HashMap<String, Object>();
        /*
        * The default profile to use when no other profiles are defined
        * This cannot be set in the <code>application.yml</code> file.
        * See https://github.com/spring-projects/spring-boot/issues/1219
        */
        defProperties.put(SPRING_PROFILE_ACTIVE, getDefaultActiveProfiles());
        app.setDefaultProperties(defProperties);
    }

    /**
     * Load application.yml from classpath.
     */
    private static Properties readProperties() {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(new ClassPathResource("config/application.yml"));
            return factory.getObject();
        } catch (Exception e) {
            logger.error("Failed to read application.yml to get default profile", e);
        }
        return null;
    }
}
