package net.hawon.spacesim.common.block.pipe.cables;

import net.hawon.spacesim.common.energy.Electricity;

public enum CableMaterial {
    COPPER_THIN("copper_thin", 0.2),
    COPPER_MEDIUM("copper_medium", 0.1),
    COPPER_THICK("copper_thick", 0.05),
    ALUMINIUM_THIN("aluminium_thin", 0.35),
    ALUMINIUM_MEDIUM("aluminium_medium", 0.2),
    ALUMINIUM_THICK("aluminium_thick", 0.1);

    final String id;
    final Electricity electricity = new Electricity();

    CableMaterial(String id, double resistance) {
        this.id = id;
        electricity.resistance = resistance;
    }
}