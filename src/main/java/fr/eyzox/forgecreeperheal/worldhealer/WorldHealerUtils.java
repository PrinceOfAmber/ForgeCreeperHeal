package fr.eyzox.forgecreeperheal.worldhealer;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldHealerUtils {
	
	protected static void dropInventory(World world, BlockPos cp, IInventory inventory) {
		for (int inventoryIndex = 0; inventoryIndex < inventory.getSizeInventory(); ++inventoryIndex)
		{
			ItemStack itemstack = inventory.getStackInSlot(inventoryIndex);

			if (!itemstack.isEmpty()){
				float f = world.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = world.rand.nextFloat() * 0.8F + 0.1F;

				for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; itemstack.getCount() > 0; ){
					int j1 = world.rand.nextInt(21) + 10;

					if (j1 > itemstack.getCount()){
						j1 = itemstack.getCount();
					}

					itemstack.shrink(j1);

					float f3 = 0.05F;
					EntityItem entityitem = getEntityItem(world, cp, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()), f, f1, f2, f3);
					 
					if(entityitem != null){
						if (itemstack.hasTagCompound()){
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}
						
						world.spawnEntity(entityitem);
					}
				}

				/*ADDED TO REMOVE ITEMSTACK FROM INVENTORY*/
				inventory.setInventorySlotContents(inventoryIndex, ItemStack.EMPTY);
			}
		}
	}
	
	protected static EntityItem getEntityItem(World world, BlockPos cp, ItemStack itemStack, float deltaX, float deltaY, float deltaZ, float motion) {
		if(itemStack.isEmpty() || itemStack.getItem() == null){
			return null;
		}
		EntityItem entityitem = new EntityItem(world, (double)((float)cp.getX() + deltaX), (double)((float)cp.getY() + deltaY), (double)((float)cp.getZ() + deltaZ), itemStack);
		entityitem.motionX = (double)((float)world.rand.nextGaussian() * motion);
		entityitem.motionY = (double)((float)world.rand.nextGaussian() * motion + 0.2F);
		entityitem.motionZ = (double)((float)world.rand.nextGaussian() * motion);
		return entityitem;
	}
}