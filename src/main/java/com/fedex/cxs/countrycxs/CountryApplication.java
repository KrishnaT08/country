/**
 * 
 */
package com.fedex.cxs.countrycxs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.fedex.cxs.countrycxs.util.DefaultProfileUtil;

/**
 * @author KrishnaPC
 *
 */
@SpringBootApplication
public class CountryApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryApplication.class);

	private static final Object SPRING_PROFILE_DEVELOPMENT = "dev";

	private static final Object SPRING_PROFILE_PRODUCTION = "prod";

	private static final Object SPRING_PROFILE_CLOUD = "cloud";

	@Inject
	private Environment env;

	
	/**
     * Initializes App.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     */
    @PostConstruct
    public void initApplication() {
        LOGGER.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SPRING_PROFILE_PRODUCTION)) {
            LOGGER.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SPRING_PROFILE_CLOUD)) {
            LOGGER.error("You have misconfigured your application! It should not" +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(CountryApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        LOGGER.info("\n----------------------------------------------------------\n\t" +
                "Country Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "External: \t{}://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            env.getActiveProfiles());
        
      /*  String configServerStatus = env.getProperty("configserver.status");
        LOGGER.info("\n----------------------------------------------------------\n\t" +
        "Config Server: {}\t status:{}\n----------------------------------------------------------",
        	env.getProperty("spring.cloud.config.uri"),
            configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);*/
        
    }
	
}
