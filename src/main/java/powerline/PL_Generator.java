//PL_Generator.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.util.Vector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Item;

public class PL_Generator
{
    PL_Team team;
    int level;
    String name;
    PL_GeneratorMenu menu;
    PL_Resource resource;
    PL_Location power_location;
    PL_Location spawner_location;
    java.util.Vector<PL_Location> spawners;
    Boolean powered;
    Boolean active;
    int timer;

    public PL_Generator(int level, String name, PL_Team team) 
    {
        spawners = new java.util.Vector<>();
        if (level == 0)
        {
            menu = null;
            powered = true;
            active = true;
            resource = PL_Resource.plr_redstone;
            team = null;
        } else 
        {
            this.team = team;
            if (team.name == "Team Emerald") menu = new PL_GeneratorMenu(level, 0);
            else if (team.name == "Team Lapis") menu = new PL_GeneratorMenu(level, 1);
            powered = false;
            active = false;
            resource = null;
        }
        this.level = level;
        this.name = name;
        timer = 0;
    }

    public void Generate()
    {
        int drop_idx = PL_GameManager.gm.random.nextInt(resource.pool.size());
        if (spawners.isEmpty()) 
        {
            World wrld = spawner_location.location.getWorld();
            Item item = wrld.dropItem(spawner_location.location, resource.pool.get(drop_idx));
            item.setVelocity(new org.bukkit.util.Vector(0, 0, 0));
        } else 
        {
            for (PL_Location spawner : spawners) 
            {
                World wrld = spawner.location.getWorld();
                Item item = wrld.dropItem(spawner.location, resource.pool.get(drop_idx));
                item.setVelocity(new org.bukkit.util.Vector(0, 0, 0));
                drop_idx = PL_GameManager.gm.random.nextInt(resource.pool.size());
            }
        }
        timer = 0;
    }
}
