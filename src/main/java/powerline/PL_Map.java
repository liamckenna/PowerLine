//PL_Map.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.World;

public class PL_Map
{
    World world;
    String name;
    PL_Team team_a;
    PL_Team team_b;
    PL_Tower tower;
    PL_Generator redstone_gen;
    PL_Location spectator_spawn;
    PL_Location corner_a;
    PL_Location corner_b;
    PL_Location center;
    PL_Location easter_egg_chest;
    int void_level;

    PL_Map(String name) 
    {
        this.name = name;
        team_a = new PL_Team("Team Emerald");
        team_a.name = "Team Emerald";
        team_b = new PL_Team("Team Lapis");
        team_b.name = "Team Lapis";
        tower = new PL_Tower();
        redstone_gen = new PL_Generator(0, "Center Redstone", null);
    }
}
