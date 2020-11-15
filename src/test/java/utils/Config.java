package utils;

/**
 * wrapper for session params
 *
 * @author Sergey Chevychelov {@literal <qa.schevychelov@gmail.com>}
 */
class Config {

    private TestEnv env;

    public Config(TestEnv env) {
        this.env = env;
    }

    TestEnv getEnv() {
        return env;
    }
}