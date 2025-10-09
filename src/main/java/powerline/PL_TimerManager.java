//PL_TimerManager.java
//Auth Liam McKenna, 2025

package powerline;

import static net.kyori.adventure.text.Component.text;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PL_TimerManager implements Listener
{
    private final Plugin plugin;

    public PL_TimerManager(Plugin plugin) { this.plugin = plugin; }

    public void StartTimer()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                switch (PL_GameManager.gm.match.status)
                {
                    case WAITING:
                    break;
                    case STARTING:
                    break;
                    case PLAYING:
                    IterateTimer();
                    break;
                    case ENDING:
                    break;
                    case DEBUG:
                    break;
                    default:
                    break;
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public static void IterateTimer()
    {
        PL_GameManager.gm.match.time_elapsed++;
        for (PL_Generator generator : PL_GameManager.gm.match.map.team_a.generators)
        {
            if (generator.active)
            {
                generator.timer++;
                if (generator.timer * generator.resource.speed_multiplier >= PL_GameManager.gm.match.generator_speed)
                {
                    generator.Generate();
                }
            }
        }
        for (PL_Generator generator : PL_GameManager.gm.match.map.team_b.generators)
        {
            if (generator.active) 
            {
                generator.timer++;
                if (generator.timer * generator.resource.speed_multiplier >= PL_GameManager.gm.match.generator_speed)
                {
                    generator.Generate();
                }
            }
        }
        PL_GameManager.gm.match.map.redstone_gen.timer++;
        
        if (PL_GameManager.gm.match.map.redstone_gen.timer >= (PL_GameManager.gm.match.generator_speed / PL_Settings.settings.redstone_muliplier))
        {
            PL_GameManager.gm.match.map.redstone_gen.Generate();
        }

        switch (PL_GameManager.gm.match.time_elapsed)
        {
            case 300:
            PL_GameManager.gm.match.generator_speed -= PL_Settings.settings.generator_speed_increase;
            Bukkit.broadcast(text("5 minutes have passed: generator speed increased!"));
            break;
            case 600:
            PL_GameManager.gm.match.generator_speed -= PL_Settings.settings.generator_speed_increase;
            Bukkit.broadcast(text("10 minutes have passed: generator speed increased!"));
            break;
            case 900:
            PL_GameManager.gm.match.generator_speed -= PL_Settings.settings.generator_speed_increase;
            Bukkit.broadcast(text("15 minutes have passed: generator speed increased!"));
            break;
            case 1200:
            PL_GameManager.gm.match.generator_speed -= PL_Settings.settings.generator_speed_increase;
            Bukkit.broadcast(text("20 minutes have passed: generator speed increased!"));
            break;
            default:
            break;
        }
    }
}
