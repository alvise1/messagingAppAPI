package com.codeCrunch.messagingAppAPI.init;

import com.codeCrunch.messagingAppAPI.models.AppUser;
import com.codeCrunch.messagingAppAPI.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        String adminEmail = "<user>@gmail.com";

        // If no admin user exists with this email, create one
        if (!userService.adminExistsByEmail(adminEmail)) {
            AppUser adminUser = new AppUser();
            adminUser.setUsername("admin");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword("Admin123!");
            adminUser.setBio("I'm the super admin!");

            userService.createAdminUser(adminUser);

            System.out.println("=== Admin user created at startup with email: " + adminEmail + " ===");
        } else {
            System.out.println("=== Admin user already exists with email: " + adminEmail + " ===");
        }
    }
}
