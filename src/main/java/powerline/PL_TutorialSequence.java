//PL_TutorialSequence.java
//Auth Liam McKenna, 2025

package powerline;

import static net.kyori.adventure.text.Component.text;
import org.bukkit.plugin.Plugin;
import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class PL_TutorialSequence implements Listener
{
    private final Plugin plugin;
    public PL_Location current_spot;
    public PL_TutorialSequence(Plugin plugin) { this.plugin = plugin; current_spot = null; }

    public void PlayTutorial()
    {
        PL_GameManager.gm.match.status = PL_MatchStatus.TUTORIAL;
        World world = Bukkit.getWorld("powerline");
        world.setStorm(false);
        world.setThundering(false);
        world.setTime(1000);
        for (Player player : Bukkit.getOnlinePlayers()) 
        {
            PL_FreezeManager.Freeze(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.sendTitle("Welcome to PowerLine!", "", 0, 80, 20);
        }
        TeleportWithSound((new PL_Location(-1056, -5, -222, 30, 25)));
        float current_delay = 0f;
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "PowerLine is a team-based PVP minigame designed for 2-8 players.");
        }, (long) current_delay * 20L);
        current_delay += 5;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "Players will work together building redstone paths to gain an advantage and win the match.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "The objective is simple: eliminate all players on the other team.");
        }, (long) current_delay * 20L);
        current_delay += 5;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1091.5f, -26, -188));
            PL_PowerManager.TurnOnTower();
            SendActionBar(7, "Players respawn by default, but by powering the main tower you can disable their ability to respawn.");
        }, (long) current_delay * 20L);
        current_delay += 7;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "Both teams can power the tower at once, meaning both teams' respawn can be disabled simultaneously.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            PL_PowerManager.TurnOffTower();
            SendActionBar(6, "By severing the other team's connection to the tower, your team can regain the ability to respawn.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "However, players who were killed while their team's respawn was disabled will stay eliminated.");
        }, (long) current_delay * 20L);
        current_delay += 5;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1046.5f, -45, -168, -34.5f, 3f));
            SendActionBar(7, "When constructing your redstone path towards the main tower, there will be generators you can branch off to power.");
        }, (long) current_delay * 20L);
        current_delay += 7;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "When powered, these generators produce a variety of resources for you and your team.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1028, -33.5f, -190.5f, -145f, 20.5f));
            SendActionBar(6, "The block that powers each generator will always be a redstone lamp.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1025.25f, -44, -185, -160f, 27f));
            SendActionBar(5, "Resources will spawn at the nearby smooth stone platform for you to collect.");
        }, (long) current_delay * 20L);
        current_delay += 5;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "When first powered, you will be given the choice of what resource you want that generator to produce.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "So long as that generator is receiving power, it will produce the chosen resource.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(7, "Also while powered, you can freely change the generator's resource by right-clicking the redstone lamp.");
        }, (long) current_delay * 20L);
        current_delay += 7;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(7, "Each team has 6 generators, and the resources available to produce will vary depending on the generator level.");
        }, (long) current_delay * 20L);
        current_delay += 7;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "There are two level I generators, two level II generators, and two level III generators.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1051, -41f, -190f, 111f, 8.5f));
            SendActionBar(6, "Level III generators are the hardest to reach but will produce the most valuable resources, and vice versa.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(7, "Feel free to read the Generator Resources book for more details on what can spawn from these generators.");
        }, (long) current_delay * 20L);
        current_delay += 7;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "Your enemies are able to steal the resources produced from your team's generators, so keep watch of your side!");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1078, -37f, -172f, 135f, 8f));
            SendActionBar(6, "To gather redstone, there is a central redstone generator in the center of the map with four spawns.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "This generator is always active and vital for victory, so fight hard for control of the center area!");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "All generators, center redstone included, will produce resources more frequently as the game progresses.");
        }, (long) current_delay * 20L);
        current_delay += 5;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "Resource generation rate will increase every 5 minutes, and you will be notified when this occurs.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "Redstone Wire and repeaters take longer to break than in normal Minecraft.");
        }, (long) current_delay * 20L);
        current_delay += 5;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "The main tool for breaking redstone is the hoe. Mining speeds are similar to breaking stone with a pickaxe.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "Use the demo in the spawn lobby to see for yourself how each type of hoe changes the break speed.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(7, "When broken, redstone and repeaters do not drop, so it's best to be careful with your redstone placements.");
        }, (long) current_delay * 20L);
        current_delay += 7;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1024, -44f, -176f, -90, 28f));
            SendActionBar(5, "It's important to note that on death, you will lose everything in your inventory, armor included.");
        }, (long) current_delay * 20L);
        current_delay += 5;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "The only items that will drop for other players are redstone and repeaters.");
        }, (long) current_delay * 20L);
        current_delay += 5;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "There are various items that are unable to be crafted while playing, even with the proper resources.");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1105, -43f, -195f, -15f, 7f));
            SendActionBar(6, "Among other restrictions, there is no way to create power (e.g. redstone torches, pressure plates, etc.).");
        }, (long) current_delay * 20L);
        current_delay += 6;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(4, "For a complete list of restricted items, see the Restricted Items book.");
        }, (long) current_delay * 20L);
        current_delay += 4;
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(4, "That's all! Good luck and have fun :)");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () ->
        {
            for (Player player : Bukkit.getOnlinePlayers()) player.sendActionBar(text("")); 
            EndTutorial();
        }, (long) current_delay * 20L);
        
    }

    public void EndTutorial()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            PL_FreezeManager.Unfreeze(player);
            player.teleport(PL_GameManager.gm.lobby.spawn.location);
            player.setGameMode(GameMode.SURVIVAL);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.0f);
            PL_GameManager.gm.match.status = PL_MatchStatus.WAITING;
        }
        PL_Helpers.WipeItemEntities();
    }

    public void TeleportWithSound(PL_Location location)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.teleport(location.location);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.0f);
        }
        current_spot = location;
    }

    public void SendActionBar(float seconds, String message)
    {
        for (Player player : Bukkit.getOnlinePlayers()) player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.2f, 1.0f);
        new BukkitRunnable() 
        {
            int ticks = 0;
            int duration = (int)(seconds * 20);

            @Override
            public void run() 
            {
                if (ticks >= duration) 
                {
                    cancel();
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()) player.sendActionBar(text(message));        
                ticks++;
            }
        }.runTaskTimer(plugin, 0L, 1L); 
    }
}
