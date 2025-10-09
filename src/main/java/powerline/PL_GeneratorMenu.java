//PL_GeneratorMenu.java
//Auth Liam McKenna, 2025

package powerline;

import java.util.Arrays;
import java.util.Vector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;

public class PL_GeneratorMenu
{
    Vector<PL_Resource> options;
    Inventory menu;

    public PL_GeneratorMenu(int level, int team) 
    {
        options = new Vector<>();
        switch (level)
        {
            case 1:
                options.add(PL_Resource.plr_sticks);
                if (team == 0) options.add(PL_Resource.plr_wool_a);
                else if (team == 1) options.add(PL_Resource.plr_wool_b);
                options.add(PL_Resource.plr_string_and_arrows);
                break;
            case 2:
                options.add(PL_Resource.plr_iron);
                options.add(PL_Resource.plr_potatoes);
                options.add(PL_Resource.plr_redstone);
                options.add(PL_Resource.plr_explosives);
                break;
            case 3:
                options.add(PL_Resource.plr_diamond);
                options.add(PL_Resource.plr_nether);
                options.add(PL_Resource.plr_potions);
            default:
                break; 
        }
        menu = Bukkit.createInventory(null, 9, "Generator Menu");
        int i = 1;
        if (options.size() == 3) i = 2;
        for (PL_Resource option : options) 
        {
            ItemMeta meta = option.icon.getItemMeta();
            meta.setDisplayName(option.name);
            meta.setLore(Arrays.asList(ChatColor.GRAY + option.description));
            option.icon.setItemMeta(meta);
            menu.setItem(i, option.icon);
            i+=2;
        }
    }
}
