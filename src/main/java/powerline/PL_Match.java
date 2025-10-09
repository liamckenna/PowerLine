//PL_Match.java
//Auth Liam McKenna, 2025

package powerline;

import static net.kyori.adventure.text.Component.text;

import java.util.Vector;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Material;

public class PL_Match implements Listener
{
    PL_Map map;
    Vector<PL_Player> players;
    Vector<PL_Spectator> spectators;
    long start_time;
    PL_MatchStatus status;
    int generator_speed;
    int time_elapsed;

    private final Plugin plugin;

    public PL_Match(Plugin plugin) { this.plugin = plugin; }

    public PL_Match() 
    { 
        status = PL_MatchStatus.WAITING;
        players = new Vector<>();
        spectators = new Vector<>();
        generator_speed = 30;
        time_elapsed = 0;
        this.plugin = null;
    }

    
    public void Start(String map_name, Vector<Player> team_a_members, Vector<Player> team_b_members, Vector<Player> spectator_members) 
    {
        status = PL_MatchStatus.STARTING;
        generator_speed = PL_Settings.settings.default_generator_speed;
        map = PL_MapLoader.LoadMap(map_name);

        AssignTeams(team_a_members, team_b_members, spectator_members);
        ResetMap();
        SpawnPlayers();

        CountdownThenBegin(PL_Settings.settings.countdown_time);
    }

    public void End(PL_Team loser)
    {
        status = PL_MatchStatus.ENDING;
        Boolean draw = false;
        PL_Team winning_team = null;
        if (map.team_a == loser) { winning_team = map.team_b; map.team_a.status = PL_TeamStatus.LOST; }
        else if (map.team_b == loser) { winning_team = map.team_a;  map.team_b.status = PL_TeamStatus.LOST; }
        else draw = true;
        String win_message = "";
        if (draw) win_message = "Game has ended in a draw!";
        else if (winning_team == map.team_a) win_message = "Team Emerald wins!";
        else if (winning_team == map.team_b) win_message = "Team Lapis wins!";
        for (PL_Player player : players) if (player.player.isOnline()) player.player.playSound(player.player.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 1.0f, 1.0f);
        
        for (PL_Spectator spectator : spectators) if (spectator.player.isOnline())  spectator.player.playSound(spectator.player.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 1.0f, 1.0f);
        final String win_msg = win_message;
        new BukkitRunnable()
        {
            int time_left = 10;
            
            @Override
            public void run()
            {
                if (time_left <= 0)
                {
                    cancel();
                    return;
                }
                for (PL_Player player : players) if (player.player.isOnline()) player.player.sendTitle(win_msg, "Returning to lobby in " + Integer.toString(time_left), 0, 21, 0);
                for (PL_Spectator spectator : spectators) if (spectator.player.isOnline()) spectator.player.sendTitle(win_msg, "Returning to lobby in " + Integer.toString(time_left), 0, 21, 0);
                time_left--;
            }
        }.runTaskTimer(PL_Main.main_plugin, 0L, 20L);


        Bukkit.getScheduler().runTaskLater(PL_Main.main_plugin, () ->
        {
            PL_GameManager.gm.ResetMatch();
        }, 200L);
    }

    public void AssignTeams(Vector<Player> team_a_members, Vector<Player> team_b_members, Vector<Player> spectator_members)
    {
        for (Player p : team_a_members) 
        {
            PL_Player player = new PL_Player(p, map.team_a);
            map.team_a.members.add(player);
            players.add(player);
            PL_GameManager.gm.player_map.put(p, player);
        }
        for (Player p : team_b_members) 
        {
            PL_Player player = new PL_Player(p, map.team_b);
            map.team_b.members.add(player);
            players.add(player);
            PL_GameManager.gm.player_map.put(p, player);
        }
        for (Player p : spectator_members) 
        {
            PL_Spectator spectator = new PL_Spectator(p);
            spectators.add(spectator);
            PL_GameManager.gm.spectator_map.put(p, spectator);
        }
    }

    public void SpawnPlayers() 
    {
        for (PL_Player player : players) 
        {
            Player p = player.player;
            p.setGameMode(GameMode.SURVIVAL);
            PL_FreezeManager.Freeze(p);
            p.setLevel(0);
            PL_DeathManager.ClearInventory(player.player);
            PL_DeathManager.ClearStats(player.player);
            PL_DeathManager.SetDefaultStats(player.player);
            PL_DeathManager.SetDefaultInventory(player.player);
            player.TeleportToSpawn();
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            p.setBedSpawnLocation(player.team.spawn_1.location, true);
        }
        for (PL_Spectator spectator : spectators) 
        {
            Player p = spectator.player;
            p.setGameMode(GameMode.SPECTATOR);
            p.setLevel(0);
            PL_DeathManager.ClearInventory(p);
            PL_DeathManager.ClearStats(p);
            p.teleport(map.spectator_spawn.location);
            p.playSound(spectator.player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            p.setBedSpawnLocation(map.spectator_spawn.location, true);
        }

    }

    public void CountdownThenBegin(int seconds) 
    {
        long delay_ticks = PL_Settings.settings.countdown_time * 20L;
        new BukkitRunnable()
        {
            int time_left = seconds;
            
            @Override
            public void run()
            {
                if (time_left <= 0)
                {
                for (PL_Player player : players) player.player.sendTitle("Start!", "Have fun :)", 0, 20, 20);
                for (PL_Spectator spectator : spectators) spectator.player.sendTitle("Start!", "Have fun :)", 0, 20, 20);
                    cancel();
                    return;
                }
                for (PL_Player player : players) player.player.sendTitle(Integer.toString(time_left), "Match starting!", 0, 21, 0);
                for (PL_Spectator spectator : spectators) spectator.player.sendTitle(Integer.toString(time_left), "Match starting!", 0, 21, 0);
                time_left--;
            }
        }.runTaskTimer(PL_Main.main_plugin, 0L, 20L);

        Bukkit.getScheduler().runTaskLater(PL_Main.main_plugin, () ->
        {
            status = PL_MatchStatus.PLAYING;
            for (PL_Player player : players)
            {
                PL_FreezeManager.Unfreeze(player.player);
                player.player.playSound(player.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                player.DisableInvincibility();
            }
            for (PL_Spectator spectator : spectators)
            {
                spectator.player.playSound(spectator.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }
            start_time = System.currentTimeMillis();
            PL_GameManager.gm.match.map.redstone_gen.Generate();
        }, delay_ticks);        
    }

    public void ResetMap()
    {
        for (Material m : PL_GameManager.gm.dynamic_blocks) PL_Helpers.WipeBlock(m, PL_GameManager.gm.match.map);
        PL_Helpers.ClearChests(PL_GameManager.gm.match.map);
        PL_Helpers.WipeItemEntities();
        PL_PowerManager.TurnOffTower();
        PL_SpecialItems.RefillEEChests();
    }
}
