//PL_Player.java
//Auth Liam McKenna, 2025

package powerline;

import static net.kyori.adventure.text.Component.text;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PL_Player
{
    Player player;
    PL_Team team;
    private int kills;
    private int deaths;
    private int final_eliminations;
    private int redstone_placed;
    private int redstone_destroyed;
    private Boolean fully_eliminated;
    private Boolean spawn_invincibility;
    PL_Generator current_menu;

    public PL_Player(Player player, PL_Team team) 
    {
        this.player = player;
        this.team = team;
        kills = 0;
        deaths = 0;
        final_eliminations = 0;
        redstone_placed = 0;
        redstone_destroyed = 0;
        fully_eliminated = false;
        current_menu = null;
        spawn_invincibility = true;
    }
    public void AddKill() { kills++; }
    public int GetKills() { return kills; }
    public void AddDeath() { deaths++; }
    public int GetDeaths() { return deaths; }
    public void AddFinalElim() { final_eliminations++; }
    public int GetFinalElims() { return final_eliminations; }
    public void AddRedstonePlaced() { redstone_placed++; }
    public int GetRedstonePlaced() { return redstone_placed; }
    public void AddRedstoneDestroyed() { redstone_destroyed++; }
    public int GetRedstoneDestroyed() { return redstone_destroyed; }
    public void Eliminate() { fully_eliminated = true; }
    public Boolean IsEliminated() { return fully_eliminated; }
    public void EnableInvincibility() { spawn_invincibility = true; }
    public void DisableInvincibility() { spawn_invincibility = false; }
    public Boolean HasInvincibility() { return spawn_invincibility; }

    public void TeleportToSpawn()
    {
        int x_min = Math.min((int)team.spawn_1.location.getX(), (int)team.spawn_2.location.getX());
        int x_max = Math.max((int)team.spawn_1.location.getX(), (int)team.spawn_2.location.getX());
        int x = PL_GameManager.gm.random.nextInt(x_min, x_max + 1);

        int z_min = Math.min((int)team.spawn_1.location.getZ(), (int)team.spawn_2.location.getZ());
        int z_max = Math.max((int)team.spawn_1.location.getZ(), (int)team.spawn_2.location.getZ());
        int z = PL_GameManager.gm.random.nextInt(z_min, z_max + 1);

        int y = (int)team.spawn_1.location.getY();
        float yaw = team.spawn_1.location.getYaw();
        player.teleport((new PL_Location(x, y, z, yaw, 0)).location);
    }
}
