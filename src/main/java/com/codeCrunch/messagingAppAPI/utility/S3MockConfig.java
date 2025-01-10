/**
 * TEST CLASS WHILE I HAVE NOT YET SET UP AWS S3 CONNECTION FOR ATTACHMENTS
 */

package com.codeCrunch.messagingAppAPI.utility;

import com.amazonaws.services.s3.AmazonS3;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3MockConfig {

    @Bean
    public AmazonS3 amazonS3() {
        return Mockito.mock(AmazonS3.class);
    }
}
