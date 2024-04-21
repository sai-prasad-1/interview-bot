package io.levantate.interviewbot.seeder;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.levantate.interviewbot.models.User;
import io.levantate.interviewbot.repository.UserRepository;



@Component
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the user list is empty
        // if (userRepository.count() == 0) {
        //     // Populate User data only if the user list is empty
        //     User user1 = new User();
        //     user1.setUsername("aljodavis125@gmail.com");
        //     user1.setPassword("12345678");
        //     userRepository.save(user1);

        //     User user2 = new User();
        //     user2.setUsername("sai.prasad@levantate.io");
        //     user2.setPassword("12345678");
        //     userRepository.save(user2);
        // }
    }
}
