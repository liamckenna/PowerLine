//PL_FreezePlayer.java
//Auth Liam McKenna, 2025

package powerline;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class PL_FreezeManager implements Listener
{
    private static final Set<Player> frozen_players = new HashSet<>();

    public static void Freeze(Player player) { frozen_players.add(player); }

    public static void Unfreeze(Player player) { frozen_players.remove(player); }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            break;
            case STARTING:
            if (frozen_players.contains(player))
            {
                Location from = event.getFrom();
                Location to = event.getTo();
                if (to == null) return;
                to.setX(from.getX());
                to.setZ(from.getZ());
                event.setTo(to);
            }
            break;
            case PLAYING:
            break;
            case ENDING:
            break;
            case DEBUG:
            break;
            case TUTORIAL:
            event.setTo(event.getFrom());
            break;
            default:
            break;
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {

        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            break;
            case STARTING:
            if (event.getDamager() instanceof Player)
            {
                Player attacker = (Player) event.getDamager();
                if (frozen_players.contains(attacker)) event.setCancelled(true);
            }
            break;
            case PLAYING:
            break;
            case ENDING:
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }
}
