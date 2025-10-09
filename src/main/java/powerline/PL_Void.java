package powerline;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

public class PL_Void implements Listener
{
    private final Plugin plugin;

    public PL_Void(Plugin plugin) { this.plugin = plugin; }

    public void VoidCheck()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                
                switch (PL_GameManager.gm.match.status)
                {
                    case WAITING:
                    for (Player player : Bukkit.getOnlinePlayers()) 
                        if (player.getLocation().getY() <= PL_GameManager.gm.lobby.void_level)
                        {
                            player.teleport(PL_GameManager.gm.lobby.spawn.location);
                            player.setFallDistance(0);
                        }
                    break;
                    case STARTING:
                    case PLAYING:
                    for (Player player : Bukkit.getOnlinePlayers()) 
                        if (player.getLocation().getY() <= PL_GameManager.gm.match.map.void_level && player.getGameMode() != GameMode.SPECTATOR)
                            player.setHealth(0.0);
                    break;
                    case ENDING:
                    for (Player player : Bukkit.getOnlinePlayers()) 
                        if (player.getLocation().getY() <= PL_GameManager.gm.match.map.void_level && player.getGameMode() != GameMode.SPECTATOR)
                        {
                            PL_Player plp = PL_GameManager.gm.player_map.get(player);
                            plp.TeleportToSpawn();
                            player.setFallDistance(0);
                        }
                    break;
                    case DEBUG:
                    break;
                    default:
                    break;
                }

            }
        }.runTaskTimer(plugin, 0L, 10L);
    }
}
