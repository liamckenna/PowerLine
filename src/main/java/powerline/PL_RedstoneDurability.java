//PL_RedstoneDurability.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class PL_RedstoneDurability implements Listener
{
    private final Map<Player, Block> breaking = new HashMap<>();
    private final Plugin plugin;

    public PL_RedstoneDurability(Plugin plugin) { this.plugin = plugin; }

    public int getHardness(ItemStack tool, Material block)
    {
        if (block == Material.REDSTONE_WIRE) 
        {
            if (tool.getType() == Material.WOODEN_HOE) return 15;
            else if (tool.getType() == Material.STONE_HOE) return 15;
            else if (tool.getType() == Material.IRON_HOE) return 10;
            else if (tool.getType() == Material.DIAMOND_HOE) return 8;
            else if (tool.getType() == Material.NETHERITE_HOE) return 8;
            else if (tool.getType() == Material.GOLDEN_HOE) return 5;
            else return 30;
        }
        else if (block == Material.REPEATER)
        {
            if (tool.getType() == Material.WOODEN_HOE) return 30;
            else if (tool.getType() == Material.STONE_HOE) return 30;
            else if (tool.getType() == Material.IRON_HOE) return 15;
            else if (tool.getType() == Material.DIAMOND_HOE) return 10;
            else if (tool.getType() == Material.NETHERITE_HOE) return 10;
            else if (tool.getType() == Material.GOLDEN_HOE) return 8;
            else return 45;
        } else return 45;
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event)
    {

        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            case PLAYING:
            case ENDING:
            if (event.getBlock().getType() == Material.REDSTONE_WIRE || event.getBlock().getType() == Material.REPEATER)
                redstoneDamage(event);
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }


    public void redstoneDamage(BlockDamageEvent event)
    {   
        event.setCancelled(true);
        
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();

        if (breaking.containsKey(player)) return;

        breaking.put(player, block);

        new BukkitRunnable()
        {
            int ticks = 0;
            int hardnessTicks = getHardness(tool, block.getType());

            @Override
            public void run()
            {
                Block target = player.getTargetBlockExact(5);

                if (target == null || !target.equals(block))
                {
                    breaking.remove(player);
                    ticks = 0;
                    cancel();
                } else 
                {
                    ticks++;
                    if (ticks >= hardnessTicks)
                    {
                        block.breakNaturally(player.getInventory().getItemInMainHand());
                        if (PL_GameManager.gm.match.status == PL_MatchStatus.PLAYING)
                        {
                            PL_Player p = PL_GameManager.gm.player_map.get(player);
                            p.AddRedstoneDestroyed();
                        }
                        breaking.remove(player);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);

    }
}