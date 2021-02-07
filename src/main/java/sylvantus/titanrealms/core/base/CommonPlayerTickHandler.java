package sylvantus.titanrealms.core.base;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonPlayerTickHandler {

    public static boolean isOnGroundOrSleeping(PlayerEntity player) {
        if (player.isSleeping()) {
            return true;
        }
        int x = MathHelper.floor(player.getPosX());
        int y = MathHelper.floor(player.getPosY() - 0.01);
        int z = MathHelper.floor(player.getPosZ());
        BlockPos pos = new BlockPos(x, y, z);
        BlockState s = player.world.getBlockState(pos);
        VoxelShape shape = s.getShape(player.world, pos);
        if (shape.isEmpty()) {
            return false;
        }
        AxisAlignedBB playerBox = player.getBoundingBox();
        return !s.isAir(player.world, pos) && playerBox.offset(0, -0.01, 0).intersects(shape.getBoundingBox().offset(pos));

    }

    // TODO: Add awareness logic here for equipped items/conditions/tracked abilities that need updates on a per Tick basis

    @SubscribeEvent
    public void onTick(PlayerTickEvent event) {
        if (event.phase == Phase.END && event.side.isServer()) {
            tickEnd(event.player);
        }
    }

    private void tickEnd(PlayerEntity player) {
        // TODO: Update any server side handlers of any relevant changes in player state
    }

    // LivingEntity has been attacked, result of that attack has not completed
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event) {
        LivingEntity base = event.getEntityLiving();

        // Example of how to prevent fall damage

//        if (event.getSource() == DamageSource.FALL) {
//            IEnergyContainer energyContainer = getFallAbsorptionEnergyContainer(base);
//            if (energyContainer != null) {
//                FloatingLong energyRequirement = MekanismConfig.gear.freeRunnerFallEnergyCost.get().multiply(event.getAmount());
//                FloatingLong simulatedExtract = energyContainer.extract(energyRequirement, Action.SIMULATE, AutomationType.MANUAL);
//                if (simulatedExtract.equals(energyRequirement)) {
//                    //If we could fully negate the damage cancel the event
//                    energyContainer.extract(energyRequirement, Action.EXECUTE, AutomationType.MANUAL);
//                    event.setCanceled(true);
//                }
//            }
//        }
    }

    // LivingEntity was attacked and successfully hurt
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        // TODO: Add checks for any special items/gear that could potentially prevent damage through means other than armor
    }

    @SubscribeEvent
    public void onLivingJump(LivingJumpEvent event) {
        // TODO: Add checks for special items/gear that can affect an Entities jump range or distance.
    }
}
