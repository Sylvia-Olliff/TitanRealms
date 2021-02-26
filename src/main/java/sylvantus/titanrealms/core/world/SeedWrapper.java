package sylvantus.titanrealms.core.world;

public class SeedWrapper {
    private static long seed = 0;

    public static void setSeed(long newSeed) { seed = newSeed; }

    public static long getSeed() { return seed; }
}
