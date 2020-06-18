package me.local.kitsplus.nbt;

import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagString;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NbtWrapper
{
    public ItemStack setNBTTag(String tagName, String value, ItemStack itemStack)
    {
        net.minecraft.server.v1_15_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = nmsStack.getOrCreateTag();
        tagCompound.set( tagName, NBTTagString.a(value) );
        itemStack = CraftItemStack.asBukkitCopy( nmsStack );

        return itemStack;
    }

    public String getNBTTag(String key, ItemStack itemStack)
    {
        net.minecraft.server.v1_15_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy( itemStack );
        NBTTagCompound tagCompound = nmsStack.getTag();
        if (tagCompound == null) return null;
        return tagCompound.getString( key );
    }
}