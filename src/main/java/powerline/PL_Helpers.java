//PL_Helpers.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Location;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.block.Chest;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.scheduler.BukkitRunnable;
import static net.kyori.adventure.text.Component.text;

public class PL_Helpers {
    public static ItemStack CreatePotion(Material potion_type, PotionType effect, Boolean extended, Boolean upgraded) {
        ItemStack potion = new ItemStack(potion_type);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(effect, extended, upgraded)); // extended = true
        potion.setItemMeta(meta);
        return potion;
    }

    public static Boolean CompareLocations(Location a, Location b) {
        return (a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ());
    }

    public static void WipeItemEntities() {
        for (Entity entity : Bukkit.getWorlds().stream().flatMap(w -> w.getEntities().stream()).toList()) {
            if (entity instanceof Item) {
                entity.remove();
            }
        }
    }

    public static void WipeBlock(Material material, PL_Map map) {
        int minX = (int) Math.min(map.corner_a.location.getX(), map.corner_b.location.getX());
        int maxX = (int) Math.max(map.corner_a.location.getX(), map.corner_b.location.getX());
        int minY = (int) Math.min(map.corner_a.location.getY(), map.corner_b.location.getY());
        int maxY = (int) Math.max(map.corner_a.location.getY(), map.corner_b.location.getY());
        int minZ = (int) Math.min(map.corner_a.location.getZ(), map.corner_b.location.getZ());
        int maxZ = (int) Math.max(map.corner_a.location.getZ(), map.corner_b.location.getZ());
        World world = map.corner_a.location.getWorld();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == material) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }

    public static void ClearChests(PL_Map map) {

        World world = Bukkit.getWorlds().get(0);
        Block chest_a = world.getBlockAt((int) map.team_a.team_chest.location.getX(),
                (int) map.team_a.team_chest.location.getY(), (int) map.team_a.team_chest.location.getZ());
        Block chest_b = world.getBlockAt((int) map.team_b.team_chest.location.getX(),
                (int) map.team_b.team_chest.location.getY(), (int) map.team_b.team_chest.location.getZ());

        if (chest_a.getState() instanceof Chest chest)
            chest.getInventory().clear();
        if (chest_b.getState() instanceof Chest chest)
            chest.getInventory().clear();

        for (Player player : Bukkit.getOnlinePlayers())
            player.getEnderChest().clear();
    }

    public static Boolean ValidateTeam(PL_Team team) {
        Boolean valid = false;
        for (PL_Player plp : team.members) {
            if (!plp.IsEliminated() && plp.player.isOnline()) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    public static Boolean ValidateWithout(PL_Player player) {
        PL_Team team = player.team;
        Boolean valid = false;
        for (PL_Player plp : team.members) {
            if (!plp.IsEliminated() && plp.player.isOnline()) {
                if (plp == player)
                    continue;
                valid = true;
                break;
            }
        }
        return valid;
    }
}
