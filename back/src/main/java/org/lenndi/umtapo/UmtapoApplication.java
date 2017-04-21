package org.lenndi.umtapo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Umtapo Spring Boot main (embedded server)
 * <p>
 * Created by axel on 28/11/16.
 */
@SpringBootApplication
public class UmtapoApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
   SpringApplication.run(UmtapoApplication.class, args);
  }

}
