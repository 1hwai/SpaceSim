package net.hawon.spacesim.common.datagen;

import net.hawon.spacesim.SpaceSim;
import net.hawon.spacesim.common.block.edges.cables.CableBlock;
import net.hawon.spacesim.common.block.edges.cables.CableType;
import net.hawon.spacesim.core.Init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.hawon.spacesim.common.block.utils.SpaceBlockProperties.*;

public class SpaceBlockStates extends BlockStateProvider {

    public SpaceBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, SpaceSim.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {

        //BLOCK ENTITY
        registerCrusher();
        registerTestgen();
        registerCables();
        simpleBlock(BlockInit.DOCKING_PORT.get());
        simpleBlock(BlockInit.EXAMPLE_CHEST.get());
        //ORE
        simpleBlock(BlockInit.BAUXITE_ORE.get());
        simpleBlock(BlockInit.URANIUM_ORE.get());
        simpleBlock(BlockInit.TITANIUM_ORE.get());
        simpleBlock(BlockInit.DEEPSLATE_TITANIUM_ORE.get());
        simpleBlock(BlockInit.DEEPSLATE_URANIUM_ORE.get());
        simpleBlock(BlockInit.DEEPSLATE_BAUXITE_ORE.get());
    }

    protected void registerCrusher() {
        BlockModelBuilder crusher = models().getBuilder("block/crusher");

        VariantBlockStateBuilder bld = getVariantBuilder(BlockInit.CRUSHER.get());

        bld.partialState().with(FACING, Direction.NORTH).modelForState().modelFile(crusher).addModel();
        bld.partialState().with(FACING, Direction.SOUTH).modelForState().modelFile(crusher).rotationY(180).addModel();
        bld.partialState().with(FACING, Direction.EAST).modelForState().modelFile(crusher).rotationY(90).addModel();;
        bld.partialState().with(FACING, Direction.WEST).modelForState().modelFile(crusher).rotationY(270).addModel();
    }

    protected void registerTestgen() {
        BlockModelBuilder testgen = models().getBuilder("block/testgen");

        VariantBlockStateBuilder bld = getVariantBuilder(BlockInit.TESTGEN.get());

        bld.partialState().with(FACING, Direction.NORTH).modelForState().modelFile(testgen).addModel();
        bld.partialState().with(FACING, Direction.SOUTH).modelForState().modelFile(testgen).rotationY(180).addModel();
        bld.partialState().with(FACING, Direction.WEST).modelForState().modelFile(testgen).rotationY(270).addModel();
        bld.partialState().with(FACING, Direction.EAST).modelForState().modelFile(testgen).rotationY(90).addModel();
    }

    protected void registerCables() {
        //Direction Order : UDNSEW
        //Never change this Order

        CableBlock[] cableBlocks = { BlockInit.COPPER_CABLE_MID.get(), BlockInit.ALUMINUM_CABLE_MID.get() , BlockInit.TIN_CABLE_MID.get() };
        for (CableBlock cableBlock : cableBlocks) {

            VariantBlockStateBuilder bld = getVariantBuilder(cableBlock);

            Queue<ArrayList<Boolean>> queue = new LinkedList<>();
            ArrayList<Boolean> start = new ArrayList<>();
            queue.add(start);

            while (!queue.isEmpty()) {
                ArrayList<Boolean> element = queue.poll();
                if (element.size() < 6) {
                    ArrayList<Boolean> temp1 = new ArrayList<>(element);
                    ArrayList<Boolean> temp2 = new ArrayList<>(element);
                    temp1.add(true);
                    temp2.add(false);
                    queue.add(temp1);
                    queue.add(temp2);
                } else if (element.size() == 6){
                    String status = (element.get(0) ? "u" : "") + (element.get(1) ? "d" : "")
                            + (element.get(2) ? "n" : "") + (element.get(3) ? "s" : "") + (element.get(4) ? "e" : "") + (element.get(5) ? "w" : "");
                    if (status.equals("")) status = "none";

                    BlockModelBuilder cable = models().getBuilder("block/cable/" + cableBlock.getType().toString() + "/" + status);
                    bld.partialState().with(UP, element.get(0)).with(DOWN, element.get(1)).with(NORTH, element.get(2)).with(SOUTH, element.get(3)).with(EAST, element.get(4)).with(WEST, element.get(5))
                            .modelForState().modelFile(cable).addModel();
                }
            }
        }

    }

}
