//PL_SpecialItems.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.block.Barrel;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.block.Action;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.block.Chest;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.entity.ItemFrame;

import java.util.List;

public class PL_SpecialItems implements Listener
{
    private final Plugin plugin;

    public PL_SpecialItems(Plugin plugin) { this.plugin = plugin; }


    public static void RefillEEChests()
    {
        if (PL_GameManager.gm.match.map.name == "factory")
        {
            Location loc = PL_GameManager.gm.match.map.easter_egg_chest.location;
            Block block = loc.getBlock();
            if (!(block.getState() instanceof Chest chest)) return;
            ItemStack stick = new ItemStack(Material.STICK);
            ItemMeta stick_meta = stick.getItemMeta();
            stick_meta.setDisplayName("Le STICK");
            stick_meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
            stick.setItemMeta(stick_meta);
            chest.getBlockInventory().clear();
            chest.getBlockInventory().setItem(13, stick);

            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
            ItemMeta boots_meta = boots.getItemMeta();
            boots_meta.setDisplayName("DA UGGS");
            boots_meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
            boots.setItemMeta(boots_meta);
            chest.getBlockInventory().setItem(22, boots);
        }
    }


    @EventHandler
    public void onBarrelOpen (InventoryOpenEvent event)
    {
        if (!(event.getPlayer() instanceof Player player)) return;
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            case PLAYING:
            case ENDING:
            if (event.getInventory().getLocation() != null)
            {
                Block block = event.getInventory().getLocation().getBlock();
                if (block.getState() instanceof Barrel) event.setCancelled(true);
            }
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }

    @EventHandler
    public void onItemFrameHit(EntityDamageByEntityEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            break;
            case STARTING:
            case PLAYING:
            case ENDING:
            if (event.getEntityType() == EntityType.ITEM_FRAME) event.setCancelled(true);
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }

    @EventHandler
    public void onItemFrameBreak(HangingBreakEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            case PLAYING:
            case ENDING:
            if (event.getEntity().getType() == EntityType.ITEM_FRAME) event.setCancelled(true);
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }

    @EventHandler
    public void onItemFrameClick(PlayerInteractEntityEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            case PLAYING:
            case ENDING:
            if (event.getRightClicked() instanceof ItemFrame) event.setCancelled(true);
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            return;
            case STARTING:
            case PLAYING:
            case ENDING:
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            if (block == null) return;
            if (block.getState() instanceof Sign) event.setCancelled(true);
            case DEBUG:
            break;
            default:
            break;
        }
    }

    @EventHandler
    public void onButtonPress(PlayerInteractEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            case PLAYING:
            case ENDING:
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
            Block block = event.getClickedBlock();
            if (block == null) return;
            Material type = block.getType();
            if (type.name().endsWith("_BUTTON")) event.setCancelled(true);
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }

    @EventHandler
    public void onFireChargeUse(PlayerInteractEvent event) 
    {

        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            break;
            case STARTING:
            break;
            case PLAYING:
            case ENDING:
            case DEBUG:
            Player player = event.getPlayer();
            if (event.getItem() != null && event.getItem().getType() == Material.FIRE_CHARGE)
            {
                Fireball fireball = player.getWorld().spawn(player.getEyeLocation(), Fireball.class);
                fireball.setYield(2);
                fireball.setDirection(player.getLocation().getDirection().normalize());
                event.getItem().setAmount(event.getItem().getAmount() - 1);
            }
            break;
            default:
            break;
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) 
    {

        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            case PLAYING:
            case ENDING:
            Block block = event.getBlock();
            if (!PL_GameManager.gm.dynamic_blocks.contains(block.getType())) event.setCancelled(true);
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }
    
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) 
    {
        Block block = event.getBlock();
        IgniteCause cause = event.getCause();

        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            case PLAYING:
            case ENDING:
            if (!PL_GameManager.gm.dynamic_blocks.contains(block.getType())) event.setCancelled(true);
            break;
            case DEBUG:
            break;
            default:
            break;
        }

    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) 
    {
        List<Block> blocks = event.blockList();
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            case PLAYING:
            case ENDING:
            blocks.removeIf(block -> 
            {
                Boolean stat = true;
                for (Material mat : PL_GameManager.gm.dynamic_blocks) if (block.getType() == mat) stat = false;
                return stat;
            });
            break;
            case DEBUG:
            break;
            default:
            break;
        }

    }

    @EventHandler
    public void onPotionDrink(PlayerItemConsumeEvent event)
    {

                switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            case PLAYING:
            case ENDING:
            ItemStack item = event.getItem();
            Player player = event.getPlayer();

            if (item.getType() == Material.POTION) 
            {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.getInventory().removeItem(new ItemStack(Material.GLASS_BOTTLE, 1));
                }, 1L);
            }
            break;
            case DEBUG:
            break;
            default:
            break;
        }


        
    }
}
