//PL_RedstoneManager.java
//Auth Liam McKenna, 2025

package powerline;

import static net.kyori.adventure.text.Component.text;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.Chunk;
import org.bukkit.World;

public class PL_RedstoneManager implements Listener
{
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) 
    {
        Block block = event.getBlock();
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            if (!PL_GameManager.gm.dynamic_blocks.contains(block.getType())) event.setCancelled(true);
            break;
            case PLAYING:
            if (block.getType() == Material.REDSTONE_WIRE || block.getType() == Material.REPEATER)
            {
                Player p = event.getPlayer();
                PL_Player player = PL_GameManager.gm.player_map.get(p);
                PL_GameManager.gm.most_recent_conductor = player;
                player.AddRedstonePlaced();
            } else if (!PL_GameManager.gm.dynamic_blocks.contains(block.getType())) event.setCancelled(true);
            break;
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
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player p = event.getPlayer();
        if (p == null) return;
        Block block = event.getBlock();

        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            case STARTING:
            if (!PL_GameManager.gm.dynamic_blocks.contains(block.getType())) event.setCancelled(true);
            break;
            case PLAYING:
            if (!PL_GameManager.gm.dynamic_blocks.contains(block.getType())) event.setCancelled(true);
            if (block.getType() == Material.REDSTONE_WIRE || block.getType() == Material.REPEATER)
            {
                //PL_Player player = PL_GameManager.gm.player_map.get(p);
                //Bukkit.broadcast(text(player.player.getName() + " HAS BROKEN A REDSTONE LOL"));
                //player.AddRedstoneDestroyed();
                //Bukkit.broadcast(text(player.player.getName() + " CURRENT REDSTONE DESTORYED: " + Integer.toString(player.GetRedstoneDestroyed())));
            }
            break;
            case ENDING:
            event.setCancelled(true);
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }
}
