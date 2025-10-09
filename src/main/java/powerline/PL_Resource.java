//PL_Resource.java
//Auth Liam McKenna, 2025

package powerline;

import java.util.Vector;
import org.bukkit.inventory.ItemStack;

public class PL_Resource
{
    Vector<PL_Drop> drops;
    Vector<ItemStack> pool;
    String name;
    String description;
    ItemStack icon;
    float speed_multiplier;

    public PL_Resource() { drops = new Vector<>(); pool = new Vector<>(); }

    public void AddDrop(PL_Drop drop)
    {
        drops.add(drop);
        for (int i = 0; i < drop.weight; i++) pool.add(drop.item);
    }

    public static PL_Resource plr_sticks;
    public static PL_Resource plr_wool_a;
    public static PL_Resource plr_wool_b;
    public static PL_Resource plr_string_and_arrows;
    public static PL_Resource plr_potatoes;
    public static PL_Resource plr_iron;
    public static PL_Resource plr_nether;
    public static PL_Resource plr_diamond;
    public static PL_Resource plr_potions;
    public static PL_Resource plr_explosives;
    public static PL_Resource plr_redstone;
}
