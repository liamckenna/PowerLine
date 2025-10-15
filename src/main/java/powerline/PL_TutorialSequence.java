//PL_TutorialSequence.java
//Auth Liam McKenna, 2025

package powerline;

import static net.kyori.adventure.text.Component.text;
import org.bukkit.plugin.Plugin;
import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.FireworkEffect;
import org.bukkit.Color;

public class PL_TutorialSequence implements Listener {
    private final Plugin plugin;
    public PL_Location current_spot;

    public PL_TutorialSequence(Plugin plugin) {
        this.plugin = plugin;
        current_spot = null;
    }

    public void PlayTutorial() {
        PL_GameManager.gm.match.status = PL_MatchStatus.TUTORIAL;
        World world = Bukkit.getWorlds().get(0);
        world.setStorm(false);
        world.setThundering(false);
        world.setTime(1000);
        for (Player player : Bukkit.getOnlinePlayers()) {
            PL_FreezeManager.Freeze(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.sendTitle("Welcome to PowerLine!", "", 0, 80, 20);
        }
        TeleportWithSound((new PL_Location(-1056, -5, -222, 30, 25)));
        float current_delay = 0f;
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "PowerLine is a team-based PVP minigame designed for 2-8 players.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(7,
                    "Players will work together building redstone paths to gain an advantage and win the match.");
        }, (long) current_delay * 20L);
        current_delay += 7;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "The objective is simple: eliminate all players on the other team.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1091.5f, -26, -188));
            PL_PowerManager.TurnOnTower();
            SendActionBar(6, "By powering the main tower you can disable the other team's ability to respawn.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(7, "That means if both teams are powering the tower, everyone's respawn is disabled.");
        }, (long) current_delay * 20L);
        current_delay += 7;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            PL_PowerManager.TurnOffTower();
            SendActionBar(7,
                    "If your connection to the tower is severed, your opponent's ability to respawn will return.");
        }, (long) current_delay * 20L);
        current_delay += 7;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "However, players who were killed while their respawn was disabled will stay eliminated.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1046.5f, -45, -168, -34.5f, 3f));
            SendActionBar(6, "When building your redstone path towards the main tower, you may encounter generators.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "When powered, these generators produce a variety of resources for you and your team.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1028, -33.5f, -190.5f, -145f, 20.5f));
            world.getBlockAt(-1026, -34, -197).setType(Material.REDSTONE_BLOCK);
            SendActionBar(4, "The block that powers each generator will always be a redstone lamp.");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            world.getBlockAt(-1026, -34, -197).setType(Material.AIR);
            TeleportWithSound(new PL_Location(-1025.25f, -44, -185, -160f, 27f));
            SendActionBar(5, "Resources will spawn at the nearby smooth stone platform for you to collect.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers())
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.2f, 1.0f);
            Item item = world.dropItem((new PL_Location(-1023.5f, -44, -189.5f)).location,
                    new ItemStack(Material.APPLE));
            item.setVelocity(new org.bukkit.util.Vector(0, 0, 0));
        }, (long) (current_delay - 2) * 20L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6,
                    "When first powered, you have the option to choose what resource that generator will produce.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "So long as that generator is receiving power, it will produce the chosen resource.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6,
                    "Also when powered, you can right click the generator's lamp to change its produced resource.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "The resources a generator can produce depends on its level.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1025, 15, -184.5f, 90f, 69f));
            SendActionBar(5, "Each team has 6 generators that can be powered on their side of the map.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(4, "There are two level I generators:");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            Firework fw = world.spawn((new PL_Location(-1025.5f, -30, -194.5f)).location, Firework.class);
            FireworkMeta meta = fw.getFireworkMeta();

            FireworkEffect effect = FireworkEffect.builder()
                    .flicker(true)
                    .withColor(Color.AQUA)
                    .withFade(Color.WHITE)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .trail(true)
                    .build();

            meta.addEffect(effect);
            meta.setPower(0);
            fw.setFireworkMeta(meta);

        }, (long) (current_delay - 2) * 20L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            Firework fw2 = world.spawn((new PL_Location(-1045.5f, -41, -189.5f)).location, Firework.class);
            FireworkMeta meta2 = fw2.getFireworkMeta();

            FireworkEffect effect2 = FireworkEffect.builder()
                    .flicker(true)
                    .withColor(Color.AQUA)
                    .withFade(Color.WHITE)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .trail(true)
                    .build();

            meta2.addEffect(effect2);
            meta2.setPower(0);
            fw2.setFireworkMeta(meta2);
        }, (long) (current_delay - 1.75f) * 20L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(4, "Two level II generators:");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            Firework fw = world.spawn((new PL_Location(-1042.5f, -30, -156.5f)).location, Firework.class);
            FireworkMeta meta = fw.getFireworkMeta();

            FireworkEffect effect = FireworkEffect.builder()
                    .flicker(true)
                    .withColor(Color.AQUA)
                    .withFade(Color.WHITE)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .trail(true)
                    .build();

            meta.addEffect(effect);
            meta.setPower(0);
            fw.setFireworkMeta(meta);

        }, (long) (current_delay - 2) * 20L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            Firework fw2 = world.spawn((new PL_Location(-1053.5f, -28, -212.5f)).location, Firework.class);
            FireworkMeta meta2 = fw2.getFireworkMeta();

            FireworkEffect effect2 = FireworkEffect.builder()
                    .flicker(true)
                    .withColor(Color.AQUA)
                    .withFade(Color.WHITE)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .trail(true)
                    .build();

            meta2.addEffect(effect2);
            meta2.setPower(0);
            fw2.setFireworkMeta(meta2);
        }, (long) (current_delay - 1.75f) * 20L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(4, "And two level III generators:");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            Firework fw = world.spawn((new PL_Location(-1066.5f, -29, -156.5f)).location, Firework.class);
            FireworkMeta meta = fw.getFireworkMeta();

            FireworkEffect effect = FireworkEffect.builder()
                    .flicker(true)
                    .withColor(Color.AQUA)
                    .withFade(Color.WHITE)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .trail(true)
                    .build();

            meta.addEffect(effect);
            meta.setPower(0);
            fw.setFireworkMeta(meta);

        }, (long) (current_delay - 2) * 20L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            Firework fw2 = world.spawn((new PL_Location(-1067.5f, -27, -194.5f)).location, Firework.class);
            FireworkMeta meta2 = fw2.getFireworkMeta();

            FireworkEffect effect2 = FireworkEffect.builder()
                    .flicker(true)
                    .withColor(Color.AQUA)
                    .withFade(Color.WHITE)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .trail(true)
                    .build();

            meta2.addEffect(effect2);
            meta2.setPower(0);
            fw2.setFireworkMeta(meta2);
        }, (long) (current_delay - 1.75f) * 20L);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6,
                    "Level III generators are the hardest to reach but will produce the most valuable resources.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5,
                    "Level I generators are closest to your power source, and level II generators sit in-between.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6, "Feel free to read the Generator Resources book for more info on these resource options.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "Your enemies are also able to steal the resources produced from your team's generators.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "All that said, you may be wondering where you can get redstone to begin with.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1078, -37f, -172f, 135f, 8f));
            SendActionBar(5, "In the center of the map lies a redstone generator with four spawns.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(4, "Unlike all other generators, this one is always active.");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5,
                    "Redstone is the most valuable resource of them all, so fight hard for control of the center!");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6,
                    "All generators, redstone included, produce resources more frequently as the game goes on.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(6,
                    "Resource generation rate increases every 5 minutes, upping the ante throughout the match.");
        }, (long) current_delay * 20L);
        current_delay += 6;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "Redstone Wire and repeaters take longer to break than in normal Minecraft.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "The hoe is the main tool for breaking redstone. Stronger hoes destroy redstone quicker.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "Use the demo in the spawn lobby to test how each type of hoe changes the break speed.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(4, "When broken, redstone and repeaters do not drop.");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5,
                    "Combined with the increased mining time, it's best to be careful when placing your redstone.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1024, -44f, -176f, -90, 28f));
            SendActionBar(4, "Upon death, you will lose everything in your inventory, armor included.");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5, "However, the only items that will drop for other players are redstone and repeaters.");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(4, "As a thief, you may be able to use this to your advantage.");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportWithSound(new PL_Location(-1105, -43f, -195f, -15f, 7f));
            SendActionBar(4, "There are various items whose crafting recipes have been disabled.");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(5,
                    "Among other restrictions, there is no way to create power (e.g. redstone torches, etc.).");
        }, (long) current_delay * 20L);
        current_delay += 5;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(4, "For a complete list of restricted items, see the Restricted Items book.");
        }, (long) current_delay * 20L);
        current_delay += 4;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            SendActionBar(3, "That's all! Good luck and have fun :)");
        }, (long) current_delay * 20L);
        current_delay += 3;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers())
                player.sendActionBar(text(""));
            EndTutorial();
        }, (long) current_delay * 20L);

    }

    public void EndTutorial() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PL_FreezeManager.Unfreeze(player);
            player.teleport(PL_GameManager.gm.lobby.spawn.location);
            player.setGameMode(GameMode.SURVIVAL);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.0f);
            PL_GameManager.gm.match.status = PL_MatchStatus.WAITING;
        }
        PL_Helpers.WipeItemEntities();
    }

    public void TeleportWithSound(PL_Location location) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(location.location);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.0f);
        }
        current_spot = location;
    }

    public void SendActionBar(float seconds, String message) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.2f, 1.0f);
        new BukkitRunnable() {
            int ticks = 0;
            int duration = (int) (seconds * 20);

            @Override
            public void run() {
                if (ticks >= duration) {
                    cancel();
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers())
                    player.sendActionBar(text(message));
                ticks++;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
}
