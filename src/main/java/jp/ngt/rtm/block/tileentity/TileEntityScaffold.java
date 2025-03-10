package jp.ngt.rtm.block.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.ngt.ngtlib.math.PooledVec3;
import jp.ngt.ngtlib.math.Vec3;
import jp.ngt.rtm.block.OrnamentType;
import net.minecraft.block.material.MapColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityScaffold extends TileEntityOrnament {
    private byte dir;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (this.getModelName().isEmpty()) {
            this.setModelName(this.getDefaultName());
            if (this.hasWorldObj()) {
                int meta = this.getBlockMetadata();
                this.getResourceState().color = MapColor.getMapColorForBlockColored(meta).colorValue;
            }
        }
        this.dir = nbt.getByte("direction");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        if (this.getModelName().isEmpty()) {
            this.setModelName(this.getDefaultName());
            if (this.hasWorldObj()) {
                int meta = this.getBlockMetadata();
                this.getResourceState().color = MapColor.getMapColorForBlockColored(meta).colorValue;
            }
        }
        super.writeToNBT(nbt);
        nbt.setByte("direction", this.dir);
    }

    public byte getDir() {
        return this.dir;
    }

    public void setDir(byte par1) {
        this.dir = par1;
        this.markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    public Vec3 getMotionVec() {
        float speed = this.getModelSet().getConfig().conveyorSpeed;
        if (speed != 0.0F) {
            Vec3 vec = this.getVec(speed);
            vec = vec.rotateAroundY(180.0F - (this.getDir() * 90.0F));
            return vec;
        }
        return Vec3.ZERO;
    }

    protected Vec3 getVec(float par1) {
        return PooledVec3.create(0.0F, 0.0F, par1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 2, this.zCoord + 1);
    }

    @Override
    public OrnamentType getOrnamentType() {
        return OrnamentType.Scaffold;
    }

    @Override
    protected String getDefaultName() {
        return "Scaffold01";
    }
}