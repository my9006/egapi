package utils;

import com.github.javafaker.Faker;

import java.util.Random;

public class TestUtils {

    public static final Random RANDOM = new Random();
    public static final Faker FAKER = new Faker();

    public static int getRandomInt(int... bound) {
        if (bound.length != 0) {
            return RANDOM.nextInt(bound[0]);
        }
        return RANDOM.nextInt();
    }

    public static String getRandomEmail() {
        final String validEmail = getRandomInt() +
                FAKER.name().username() +
                "@testateguru.com";
        return validEmail.toLowerCase();
    }

    public static <T> T getRandomItemFromDataProvider(Object[][] dataProvider) {
        return (T) dataProvider[getRandomInt(dataProvider.length)][0];
    }

}
