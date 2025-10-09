//PL_PowerManager.java
//Auth Liam McKenna, 2025

package powerline;

import static net.kyori.adventure.text.Component.text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;


public class PL_PowerManager implements Listener
{
    @EventHandler
    public void onRedstoneChange(BlockRedstoneEvent event)
    {

        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            break;
            case STARTING:
            break;
            case PLAYING:
            Block block = event.getBlock();
            int old_power = event.getOldCurrent();
            int new_power = event.getNewCurrent();
            if (block.getType() == Material.REDSTONE_LAMP) 
            {
                if (old_power == 0 && new_power > 0) TurnON(block);
                else if (new_power == 0 && old_power > 0) TurnOFF(block);
            }
            break;
            case ENDING:
            break;
            case DEBUG:
            break;
            default:
            break;
        }        
    }

    public void TurnON(Block lamp) 
    {
        Location target = lamp.getLocation();
        Location tower_a = PL_GameManager.gm.match.map.tower.location_a.location;
        Location tower_b = PL_GameManager.gm.match.map.tower.location_b.location;
        if (PL_Helpers.CompareLocations(target, tower_a))
        {
            PL_GameManager.gm.match.map.tower.a_powered = true;
            PL_GameManager.gm.match.map.team_b.status = PL_TeamStatus.DISADVANTAGE;
            PL_Player player = PL_GameManager.gm.most_recent_conductor;
            String player_name = player.player.getName();
            Bukkit.broadcast(text(player_name + " has powered the tower. Team Lapis's respawn is DISABLED!"));
            for (PL_Player plp : PL_GameManager.gm.match.map.team_a.members) plp.player.playSound(player.player.getLocation(), Sound.BLOCK_END_GATEWAY_SPAWN, 1.0f, 1.0f);
            for (PL_Player plp : PL_GameManager.gm.match.map.team_b.members) plp.player.playSound(player.player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1.0f, 1.0f);
            if (PL_GameManager.gm.match.map.team_a.status == PL_TeamStatus.NEUTRAL) TurnOnTower();
            if (!PL_Helpers.ValidateTeam(PL_GameManager.gm.match.map.team_b)) PL_GameManager.gm.EndMatch(PL_GameManager.gm.match.map.team_b);
            return;
        } else if (PL_Helpers.CompareLocations(target, tower_b)) 
        {
            PL_GameManager.gm.match.map.tower.b_powered = true;
            PL_GameManager.gm.match.map.team_a.status = PL_TeamStatus.DISADVANTAGE;
            PL_Player player = PL_GameManager.gm.most_recent_conductor;
            String player_name = player.player.getName();
            Bukkit.broadcast(text(player_name + " has powered the tower. Team Emerald's respawn is DISABLED!"));
            for (PL_Player plp : PL_GameManager.gm.match.map.team_b.members) plp.player.playSound(player.player.getLocation(), Sound.BLOCK_END_GATEWAY_SPAWN, 1.0f, 1.0f);
            for (PL_Player plp : PL_GameManager.gm.match.map.team_a.members) plp.player.playSound(player.player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1.0f, 1.0f);
            if (PL_GameManager.gm.match.map.team_b.status == PL_TeamStatus.NEUTRAL) TurnOnTower();
            if (!PL_Helpers.ValidateTeam(PL_GameManager.gm.match.map.team_a)) PL_GameManager.gm.EndMatch(PL_GameManager.gm.match.map.team_a);
            return;
        }

        for (PL_Generator generator : PL_GameManager.gm.match.map.team_a.generators)
        {
            Location gen_loc = generator.power_location.location;
            if (PL_Helpers.CompareLocations(target, gen_loc))
            {
                generator.powered = true;
                if (generator.resource == null)
                {
                    PL_Player player = PL_GameManager.gm.most_recent_conductor;
                    player.current_menu = generator;
                    player.player.openInventory(generator.menu.menu);
                    String player_name = player.player.getName();
                    for (PL_Player plp : player.team.members) plp.player.playSound(player.player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.0f);
                    switch (player.team.GeneratorsReached())
                    {
                        case 1:
                        Bukkit.broadcast(text(player_name + " has powered Team Emerald's 1st generator!"));
                        break;
                        case 2:
                        Bukkit.broadcast(text(player_name + " has powered Team Emerald's 2nd generator!"));
                        break;
                        case 3:
                        Bukkit.broadcast(text(player_name + " has powered Team Emerald's 3rd generator!"));
                        break;
                        case 4:
                        Bukkit.broadcast(text(player_name + " has powered Team Emerald's 4th generator!"));
                        break;
                        case 5:
                        Bukkit.broadcast(text(player_name + " has powered Team Emerald's 5th generator!"));
                        break;
                        case 6:
                        Bukkit.broadcast(text(player_name + " has powered Team Emerald's 6th generator!"));
                        Bukkit.broadcast(text("They're unstoppable!"));
                        break;
                        default:
                        break;
                    }
                    
                } else generator.active = true;
                return;
            }
        }
        for (PL_Generator generator : PL_GameManager.gm.match.map.team_b.generators)
        {
            Location gen_loc = generator.power_location.location;
            if (PL_Helpers.CompareLocations(target, gen_loc))
            {
                generator.powered = true;
                if (generator.resource == null)
                {
                    PL_Player player = PL_GameManager.gm.most_recent_conductor;
                    player.current_menu = generator;
                    player.player.openInventory(generator.menu.menu);
                    String player_name = player.player.getName();
                    for (PL_Player plp : player.team.members) plp.player.playSound(player.player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.0f);
                    switch (player.team.GeneratorsReached())
                    {
                        case 1:
                        Bukkit.broadcast(text(player_name + " has powered Team Lapis's 1st generator!"));
                        break;
                        case 2:
                        Bukkit.broadcast(text(player_name + " has powered Team Lapis's 2nd generator!"));
                        break;
                        case 3:
                        Bukkit.broadcast(text(player_name + " has powered Team Lapis's 3rd generator!"));
                        break;
                        case 4:
                        Bukkit.broadcast(text(player_name + " has powered Team Lapis's 4th generator!"));
                        break;
                        case 5:
                        Bukkit.broadcast(text(player_name + " has powered Team Lapis's 5th generator!"));
                        break;
                        case 6:
                        Bukkit.broadcast(text(player_name + " has powered Team Lapis's 6th generator!"));
                        Bukkit.broadcast(text("They're unstoppable!"));
                        break;
                        default:
                        break;
                    }
                    
                } else generator.active = true;
                return;
            }
        }
    }

    public void TurnOFF(Block lamp) 
    {
        Location target = lamp.getLocation();
        Location tower_a = PL_GameManager.gm.match.map.tower.location_a.location;
        Location tower_b = PL_GameManager.gm.match.map.tower.location_b.location;
        if (PL_Helpers.CompareLocations(target, tower_a))
        {
            PL_GameManager.gm.match.map.team_b.status = PL_TeamStatus.NEUTRAL;
            Bukkit.broadcast(text("Team Emerald's tower connection has been severed. Team Lapis's respawn has been re-enabled!"));
            if (PL_GameManager.gm.match.map.team_a.status != PL_TeamStatus.DISADVANTAGE) TurnOffTower();
            return;
        } else if (PL_Helpers.CompareLocations(target, tower_b)) 
        {
            PL_GameManager.gm.match.map.team_a.status = PL_TeamStatus.NEUTRAL;
            Bukkit.broadcast(text("Team Lapis's tower connection has been severed. Team Emerald's respawn has been re-enabled!"));
            if (PL_GameManager.gm.match.map.team_b.status != PL_TeamStatus.DISADVANTAGE) TurnOffTower();
            return;
        }
        for (PL_Generator generator : PL_GameManager.gm.match.map.team_a.generators)
        {
            Location gen_loc = generator.power_location.location;
            if (PL_Helpers.CompareLocations(target, gen_loc))
            {
                generator.powered = false;
                generator.active = false;
                return;
            }
        }
        for (PL_Generator generator : PL_GameManager.gm.match.map.team_b.generators)
        {
            Location gen_loc = generator.power_location.location;
            if (PL_Helpers.CompareLocations(target, gen_loc))
            {
                generator.powered = false;
                generator.active = false;
                return;
            }
        }
    }

    public static void TurnOnTower()
    {
        Location tower = null;
        if (PL_GameManager.gm.match.status == PL_MatchStatus.TUTORIAL) tower = (new PL_Location(-1092, -46, -157)).location;
        else tower = PL_GameManager.gm.match.map.tower.redstone_center.location;

        World world = Bukkit.getWorld("powerline");

        for (int x = (int)tower.getX() - 2; x <= (int)tower.getX() + 2; x+=4)
        {
            for (int z = (int)tower.getZ() - 1; z <= (int)tower.getZ() + 1; z++)
            {
                Location loc = new Location(world, x, tower.getY(), z); 
                Block block = loc.getBlock();
                block.setType(Material.AIR);
            }
        }
        for (int z = (int)tower.getZ() - 2; z <= (int)tower.getZ() + 2; z+=4)
        {
            for (int x = (int)tower.getX() - 1; x <= (int)tower.getX() + 1; x++)
            {
                Location loc = new Location(world, x, tower.getY(), z); 
                Block block = loc.getBlock();
                block.setType(Material.AIR);
            }
        }
    }

    public static void TurnOffTower()
    {
        Location tower = null;
        
        if (PL_GameManager.gm.match.status == PL_MatchStatus.TUTORIAL) tower = (new PL_Location(-1092, -46, -157)).location;
        else tower = PL_GameManager.gm.match.map.tower.redstone_center.location;
        
        World world = Bukkit.getWorld("powerline");

        for (int x = (int)tower.getX() - 2; x <= (int)tower.getX() + 2; x+=4)
        {
            for (int z = (int)tower.getZ() - 1; z <= (int)tower.getZ() + 1; z++)
            {
                Location loc = new Location(world, x, tower.getY(), z); 
                Block block = loc.getBlock();
                block.setType(Material.REDSTONE_TORCH);
            }
        }
        for (int z = (int)tower.getZ() - 2; z <= (int)tower.getZ() + 2; z+=4)
        {
            for (int x = (int)tower.getX() - 1; x <= (int)tower.getX() + 1; x++)
            {
                Location loc = new Location(world, x, tower.getY(), z); 
                Block block = loc.getBlock();
                block.setType(Material.REDSTONE_TORCH);
            }
        }
    }
}
