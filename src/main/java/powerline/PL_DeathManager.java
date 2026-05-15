//PL_DeathManager.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static net.kyori.adventure.text.Component.text;

public class PL_DeathManager implements Listener {
    private final Plugin plugin;

    public PL_DeathManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDroppedExp(0);
        switch (PL_GameManager.gm.match.status) {
            case WAITING:
                event.getDrops().removeIf(item -> item.getType() != Material.REDSTONE &&
                        item.getType() != Material.REPEATER);
                break;
            case STARTING:
                event.setCancelled(true);
                break;
            case PLAYING:
                playerDeath(event);
                break;
            case ENDING:
                event.setCancelled(true);
                break;
            case DEBUG:
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        switch (PL_GameManager.gm.match.status) {
            case WAITING:
                PL_LobbyManager.GiveBooks(player);
                break;
            case STARTING:
                break;
            case PLAYING:
                PL_Player pl_player = PL_GameManager.gm.player_map.get(player);
                HandleRespawn(pl_player);
                break;
            case ENDING:
                break;
            case DEBUG:
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        switch (PL_GameManager.gm.match.status) {
            case WAITING:
                break;
            case STARTING:
            case PLAYING:
                if (event.getEntity() instanceof Player player) {
                    PL_Player plp = PL_GameManager.gm.player_map.get(player);
                    if (plp != null && plp.HasInvincibility())
                        event.setCancelled(true);
                }
                break;
            case ENDING:
                break;
            case DEBUG:
                break;
            default:
                break;
        }
    }

    public void playerDeath(PlayerDeathEvent event) {
        event.getDrops().removeIf(item -> item.getType() != Material.REDSTONE &&
                item.getType() != Material.REPEATER);

        Player deceased = event.getEntity();
        Player killer = deceased.getKiller();

        PL_Player pl_deceased = PL_GameManager.gm.player_map.get(deceased);
        if (killer != null) {
            PL_Player pl_killer = PL_GameManager.gm.player_map.get(killer);
            if (pl_killer != null) {
                if (pl_deceased.team.status != PL_TeamStatus.DISADVANTAGE)
                    HandleKill(pl_killer);
                else
                    HandleFinalKill(pl_killer);
            }
        }
        if (pl_deceased.team.status != PL_TeamStatus.DISADVANTAGE)
            HandleDeath(pl_deceased);
        else {
            HandleFinalDeath(pl_deceased);
            if (!PL_Helpers.ValidateWithout(pl_deceased)) {
                // Bukkit.broadcast(text("team has failed to be validated. Match ending!"));
                PL_GameManager.gm.EndMatch(pl_deceased.team);
            }

        }
    }

    public void HandleDeath(PL_Player player) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            ClearStats(player.player);
            ClearInventory(player.player);
            player.AddDeath();
            player.player.setGameMode(GameMode.SPECTATOR);
            player.player.setBedSpawnLocation(PL_GameManager.gm.match.map.spectator_spawn.location, true);
            player.player.spigot().respawn();
            RespawnCountdown(player);
            DelayedSpawn(player);
        }, 1L);
    }

    public void HandleFinalDeath(PL_Player player) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            ClearStats(player.player);
            ClearInventory(player.player);
            player.AddDeath();
            player.Eliminate();
            player.player.setGameMode(GameMode.SPECTATOR);
        }, 1L);
    }

    public static void HandleKill(PL_Player player) {
        player.AddKill();
        player.player.setLevel(player.GetKills());
        player.player.playSound(player.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        PL_ScoreboardManager.PostGameScoreboard();
    }

    public static void HandleFinalKill(PL_Player player) {
        player.AddKill();
        player.AddFinalElim();
        player.player.setLevel(player.GetKills());
        player.player.playSound(player.player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
        PL_ScoreboardManager.PostGameScoreboard();
    }

    public void HandleRespawn(PL_Player player) {
        SetDefaultStats(player.player);
        SetDefaultInventory(player.player);
        player.EnableInvincibility();
        long delay_ticks = PL_Settings.settings.spawn_invincibility_time * 20L;
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.DisableInvincibility();
        }, delay_ticks);
    }

    public static void ClearStats(Player player) {
        player.setLevel(0);
        player.setExp(0f);
    }

    public static void ClearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItemInOffHand(null);
    }

    public static void ClearEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }

    public static void SetDefaultStats(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20);
        PL_Player plp = PL_GameManager.gm.player_map.get(player);
        if (plp != null)
            player.setLevel(plp.GetKills());
        else
            player.setLevel(0);
    }

    public static void SetDefaultInventory(Player player) {
        ItemStack sword = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemStack hoe = new ItemStack(Material.WOODEN_HOE, 1);
        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, hoe);
    }

    public void RespawnCountdown(PL_Player player) {
        new BukkitRunnable() {
            int time_left = PL_Settings.settings.respawn_time;

            @Override
            public void run() {
                if (time_left <= 0) {
                    cancel();
                    return;
                }
                player.player.sendTitle("You died!", "Respawning in " + Integer.toString(time_left), 0, 20, 0);
                time_left--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void DelayedSpawn(PL_Player player) {
        long delay_ticks = PL_Settings.settings.respawn_time * 20L;
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.player.setGameMode(GameMode.SURVIVAL);
            player.TeleportToSpawn();
            HandleRespawn(player);
        }, delay_ticks);
    }
}
