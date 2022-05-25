package net.hawon.spacesim.common.block.pipe.cables;

import net.hawon.spacesim.common.energy.Electricity;

public enum CableMaterial {
    // Ohm * meter
    COPPER_THIN("copper_thin", 0.2), // 10^-2
    COPPER_MEDIUM("copper_medium", 0.1),
    COPPER_THICK("copper_thick", 0.05),
    ALUMINIUM_THIN("aluminium_thin", 0.32),
    ALUMINIUM_MEDIUM("aluminium_medium", 0.16),
    ALUMINIUM_THICK("aluminium_thick", 0.08);

    final String id;
    final Electricity e = new Electricity();

    CableMaterial(String id, double ohmmeter) {
        this.id = id;
        e.resistance = ohmmeter / 100;
    }
}