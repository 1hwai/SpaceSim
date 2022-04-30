package net.hawon.spacesim.common.energy;

public enum EnergyTier {
    BASIC("Basic", 1, 64),
    STANDARD("Standard", 2, 128),
    ADVANCED("Advanced", 3, 256),
    SUPER("Super", 4, 512);

    private final String name;
    private final int lvl;
    private final int maxTransfer;

    EnergyTier(String name, int lvl, int maxTransfer) {
        this.name = name;
        this.lvl = lvl;
        this.maxTransfer = maxTransfer;
    }

    public String getName() {
        return name;
    }

    public int getLvl() {
        return lvl;
    }

    public int getMaxTransfer() {
        return maxTransfer;
    }

}
