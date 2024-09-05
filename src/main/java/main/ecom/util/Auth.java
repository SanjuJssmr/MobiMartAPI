package main.ecom.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Auth {
    Random RANDOM = new SecureRandom();

    public String generateOtp() {
        int otp = RANDOM.nextInt(900000) + 100000;
        return String.valueOf(otp);
    }
}
