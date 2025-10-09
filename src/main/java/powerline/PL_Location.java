//PL_Location.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class PL_Location
{
    Location location;

    PL_Location(float x, float y, float z) 
    { 
        location = new Location(Bukkit.getWorlds().get(0), (double)x, (double)y, (double)z); 
    }

    PL_Location(float x, float y, float z, float yaw, float pitch) 
    { 
        location = new Location(Bukkit.getWorlds().get(0), (double)x, (double)y, (double)z, yaw, pitch); 
    }
}
