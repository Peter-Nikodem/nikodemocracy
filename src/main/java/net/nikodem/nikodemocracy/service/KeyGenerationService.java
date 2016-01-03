package net.nikodem.nikodemocracy.service;

import com.google.gwt.thirdparty.guava.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Peter Nikodem
 */
@Service
public class KeyGenerationService {

    private SecureRandom srandom = new SecureRandom();
    private Random random = new Random();

    public static String generateElectionUrl( String adminUsername, String electionName){
        return Hashing.murmur3_32().hashString(adminUsername+electionName, StandardCharsets.UTF_8)
                .toString();
    }



    private static char[] VALID_CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456879".toCharArray();

    public String randomAlphanumericString() {
        int numChars = 16;
        char[] buffer = new char[numChars];

        for (int i = 0; i < numChars; ++i) {
            // reseed random once you've used up all available entropy bits
            if ((i % 12) == 0) {
                random.setSeed(srandom.nextLong()); // 64 bits of random!
            }
            buffer[i] = VALID_CHARACTERS[random.nextInt(VALID_CHARACTERS.length)];
        }
        return new String(buffer);
    }
}
