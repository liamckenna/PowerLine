//PL_MenuManager.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import static net.kyori.adventure.text.Component.text;
import org.bukkit.Sound;

public class PL_MenuManager implements Listener
{
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            break;
            case STARTING:
            break;
            case PLAYING:
            PL_Player player = PL_GameManager.gm.player_map.get((Player) event.getPlayer()); 
            if (player == null) return;
            Location loc = event.getInventory().getLocation();

            if (event.getInventory().getType() == InventoryType.CHEST)
            {
                if (loc != null && player != null)
                {
                    if (player.team == PL_GameManager.gm.match.map.team_a)
                    {
                        if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.match.map.team_b.team_chest.location))
                        {
                            event.setCancelled(true);
                        }
                    } else if (player.team == PL_GameManager.gm.match.map.team_b)
                    {
                        if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.match.map.team_a.team_chest.location))
                        {
                            event.setCancelled(true);
                        }
                    }
                }
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

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            break;
            case STARTING:
            break;
            case PLAYING:
            case ENDING:
            PL_Player player = PL_GameManager.gm.player_map.get(event.getPlayer());
            if (player == null) return;
            if (player.current_menu == null) return;
            PL_Generator generator = player.current_menu;
            if (generator.resource == null)
            {
                switch (generator.level)
                {
                    case 1:
                    generator.resource = PL_Resource.plr_sticks;
                    break;
                    case 2:
                    generator.resource = PL_Resource.plr_iron;
                    break;
                    case 3:
                    generator.resource = PL_Resource.plr_diamond;
                    break;
                    default:
                    break;
                }
                for (PL_Player plp : player.team.members) if (plp.player.isOnline()) plp.player.sendMessage("Generator " + generator.name + " will now produce " + generator.resource.name + ".");
                if (generator.powered) generator.active = true;
                player.current_menu = null;
            }
            break;
            case DEBUG:
            break;
            default:
            break;
        }  
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            break;
            case STARTING:
            break;
            case PLAYING:
            case ENDING:
            PL_Player player = PL_GameManager.gm.player_map.get(event.getWhoClicked());
            if (player == null) return;
            if (player.current_menu == null) return;
            ItemStack clicked = event.getCurrentItem();
            PL_Generator generator = player.current_menu;

            if (clicked == null || clicked.getType() == Material.AIR) return;
            switch (clicked.getType()) 
            {
                case OAK_SAPLING:
                if (generator.level == 1) generator.resource = PL_Resource.plr_sticks;
                break;
                case LIME_WOOL:
                if (generator.level == 1) generator.resource = PL_Resource.plr_wool_a;
                break;
                case BLUE_WOOL:
                if (generator.level == 1) generator.resource = PL_Resource.plr_wool_b;
                break;
                case ARROW:
                if (generator.level == 1) generator.resource = PL_Resource.plr_string_and_arrows;
                break;
                case IRON_INGOT:
                if (generator.level == 2) generator.resource = PL_Resource.plr_iron;
                break;
                case BAKED_POTATO:
                if (generator.level == 2) generator.resource = PL_Resource.plr_potatoes;
                break;
                case REDSTONE:
                if (generator.level == 2) generator.resource = PL_Resource.plr_redstone;
                break;
                case GUNPOWDER:
                if (generator.level == 2) generator.resource = PL_Resource.plr_explosives;
                break;
                case DIAMOND:
                if (generator.level == 3) generator.resource = PL_Resource.plr_diamond;
                break;
                case ENCHANTED_GOLDEN_APPLE:
                if (generator.level == 3) generator.resource = PL_Resource.plr_nether;
                break;
                case POTION:
                if (generator.level == 3) generator.resource = PL_Resource.plr_potions;
                break;
                default:
                break;
            }
            player.player.playSound(player.player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
            for (PL_Player plp : player.team.members) plp.player.sendMessage("Generator " + generator.name + " will now produce " + generator.resource.name + ".");
            if (generator.powered) generator.active = true;
            player.current_menu = null;
            event.setCancelled(true);
            player.player.closeInventory();
            break;
            case DEBUG:
            break;
            default:
            break;
        }

        
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {

        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            break;
            case STARTING:
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

        Player p = event.getPlayer();
        Block clicked = event.getClickedBlock();

        if (clicked == null || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (clicked.getType() == Material.REDSTONE_LAMP) 
        {
            Location block_location = clicked.getLocation();
            PL_Player player = PL_GameManager.gm.player_map.get(p);
            if (player == null) return;
            for (PL_Generator generator : player.team.generators)
            {
                if (PL_Helpers.CompareLocations(block_location, generator.power_location.location) && generator.powered)
                {
                    player.current_menu = generator;
                    p.openInventory(generator.menu.menu);
                    break;
                }
            }
        } else if (clicked.getType() == Material.CHEST || clicked.getType() == Material.BARREL || clicked.getType() == Material.CRAFTING_TABLE)
        {
            PL_Player player = PL_GameManager.gm.player_map.get(p);
            player.current_menu = null;
        }
    }
}
