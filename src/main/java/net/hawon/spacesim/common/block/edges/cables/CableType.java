package net.hawon.spacesim.common.block.edges.cables;


public enum CableType {
    COPPER_MID("copper_cable_mid", 0.15),
    ALUMINIUM_MID("aluminum_cable_mid", 0.2),
    TIN_MID("tin_cable_mid", 1.0);

    private final String symbol;
    private final double resistance;

    CableType(String symbol, double resistance) {
        this.symbol = symbol;
        this.resistance = resistance;
    }

    public double getResistance() {
        return resistance;
    }

    public String toString() {
        return symbol;
    }

}
