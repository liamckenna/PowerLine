//PL_LobbyManager.java
//Auth Liam McKenna, 2025

package powerline;

import java.util.UUID;

import static net.kyori.adventure.text.Component.text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Entity;
import org.bukkit.block.Sign;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.block.Skull;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Rotatable;
import org.bukkit.profile.PlayerProfile;
import java.util.UUID;

import net.kyori.adventure.text.Component;

public class PL_LobbyManager implements Listener
{
    public static PL_Lobby LoadLobby()
    {
        PL_Lobby lobby = new PL_Lobby();

        lobby.spawn = new PL_Location(117, 212, -437, 270f, 0);

        lobby.join_team_a = new PL_Location(141.96875f, 212.5f, -438.5f);
        lobby.join_team_b = new PL_Location(141.96875f, 212.5f, -434.5f);
        lobby.join_spectators = new PL_Location(141.96875f, 212.5f, -437.5f);
        lobby.change_map  = new PL_Location(0, 0, 0);
        lobby.start_match  = new PL_Location(141.96875f, 212.5f, -436.5f);
        lobby.watch_tutorial = new PL_Location(141.96875f, 212.5f, -435.5f);

        lobby.wood_hoe = new PL_Location(119.5f, 212.5f, -412.03125f);
        lobby.iron_hoe = new PL_Location(118.5f, 212.5f, -412.03125f);
        lobby.gold_hoe = new PL_Location(116.5f, 212.5f, -412.03125f);
        lobby.diamond_hoe = new PL_Location(115.5f, 212.5f, -412.03125f);
        lobby.test_hoes = new PL_Location(117, 212, -413);
        lobby.test_redstone_floor = new PL_Location(118, 211, -414);
        lobby.test_repeater_floor = new PL_Location(116, 211, -414);

        lobby.pm_winner = new PL_Location(117, 213, -461);
        lobby.pm_lobby_record = new PL_Location(118, 212, -461);
        lobby.pm_match_length = new PL_Location(116, 212, -461);
        lobby.pm_redstone_placed_sign = new PL_Location(115, 211, -460);
        lobby.pm_kills_sign = new PL_Location(117, 211, -460);
        lobby.pm_redstone_destroyed_sign = new PL_Location(119, 211, -460);
        lobby.pm_redstone_placed_head = new PL_Location(115, 212, -461);
        lobby.pm_kills_head = new PL_Location(117, 212, -461);
        lobby.pm_redstone_destroyed_head = new PL_Location(119, 212, -461);
        
        lobby.void_level = 185;
        World world = lobby.spawn.location.getWorld();
        world.setSpawnLocation(lobby.spawn.location);
        return lobby;
    }

    public static void GiveBooks(Player player) 
    {
        PL_DeathManager.ClearInventory(player);
        ItemStack tutorial_book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta tutorial_book_meta = (BookMeta) tutorial_book.getItemMeta();

        tutorial_book_meta.setTitle("PowerLine Tutorial");
        tutorial_book_meta.setAuthor("Leeyum");
        tutorial_book_meta.addPages(
            Component.text("Welcome to PowerLine!\n\n" +
            "Feel free to watch the tutorial sequence or read this book - both deliver the same information.\n\n" +
            "PowerLine is a team-based PVP minigame designed for 2-8 players."),

            Component.text("Players will work together building redstone paths to gain an advantage and win the match.\n\n" +
            "The objective is simple: eliminate all players on the other team."),

            Component.text("Players respawn by default, but by powering the main tower you can disable their ability to respawn.\n\n" +
            "Both teams can power the tower at once, meaning both teams' respawn can be disabled simultaneously."),

            Component.text("By severing the other team's connection to the tower, your team can regain the ability to respawn.\n\n" +
            "However, players who were killed while their team's respawn was disabled will stay eliminated."),

            Component.text("When constructing your redstone path towards the main tower, there will be generators you can branch off to power.\n\n" +
            "When powered, these generators produce a variety of resources for you and your team."),

            Component.text("The block that powers each generator will always be a redstone lamp.\n\n" +
            "Resources will spawn at the nearby smooth stone platform for you to collect."),

            Component.text("When first powered, you will be given the choice of what resource you want that generator to produce.\n\n" +
            "So long as that generator is receiving power, it will produce the chosen resource."),

            Component.text("Also while powered, you can freely change the generator's resource by right-clicking the redstone lamp.\n\n" +
            "Each team has 6 generators, and the resources available to produce will vary depending on the generator level."),

            Component.text("There are two level I generators, two level II generators, and two level III generators.\n\n" +
            "Level III generators are the hardest to reach but will produce the most valuable resources, and vice versa."),

            Component.text("Feel free to read the Generator Resources book for more details on what can spawn from these generators.\n\n" +
            "Your enemies are able to steal the resources produced from your team's generators, so keep watch of your side!"),

            Component.text("To gather redstone, there is a central redstone generator in the center of the map with four spawns.\n\n" +
            "This generator is always active and vital for victory, so fight hard for control of the center area!"),

            Component.text("All generators, center redstone included, will produce resources more frequently as the game progresses.\n\n" +
            "Resource generation rate will increase every 5 minutes, and you will be notified when this occurs."),

            Component.text("Redstone Wire and repeaters take longer to break than in normal Minecraft.\n\n" +
            "The main tool for breaking redstone is the hoe. Mining speeds are similar to breaking stone with a pickaxe."),

            Component.text("Use the demo in the spawn lobby to see for yourself how each type of hoe changes the break speed.\n\n" +
            "When broken, redstone and repeaters do not drop, so it's best to be careful with your redstone placements."),

            Component.text("It's important to note that on death, you will lose everything in your inventory, armor included.\n\n" +
            "The only items that will drop for other players are redstone and repeaters."),

            Component.text("There are various items that are unable to be crafted while playing, even with the proper resources.\n\n" +
            "Among other restrictions, there is no way to create power (e.g. redstone torches, pressure plates, etc.)."),

            Component.text("For a complete list of restricted items, see the Restricted Items book.\n\n" +
            "That's all! Good luck and have fun :)")
        );

        tutorial_book.setItemMeta(tutorial_book_meta);
        player.getInventory().setItem(1, tutorial_book);

        ItemStack generator_resources_book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta generator_resources_book_meta = (BookMeta) generator_resources_book.getItemMeta();

        generator_resources_book_meta.setTitle("Generator Resources");
        generator_resources_book_meta.setAuthor("Leeyum");

        generator_resources_book_meta.addPages(
            Component.text
            (
                "Level I Resources:\n\n" +
                "- Sticks and Apples\n" + 
                "- String and Arrows\n" +
                "- Wool"
            ),
            Component.text
            (
                "Level II Resources:\n\n" +
                "- Baked Potatoes\n" + 
                "- Explosive Materials\n" +
                "- Iron\n" +
                "- Redstone" 
            ),
            Component.text
            (
                "Level III Resources:\n\n" +
                "- Diamonds\n" + 
                "- Nether Loot\n" +
                "- Potions"
            ),
            Component.text
            (
                "Sticks and Apples\n\n" +
                "Speed Multiplier: 1\n\n" +
                "- Apple: 3/10\n" + 
                "- Oak Planks (2): 1/10\n" +
                "- Stick: 6/10\n"
            ),
            Component.text
            (
                "String and Arrows\n\n" +
                "Speed Multiplier: 2\n\n" +
                "- Arrow (2): 3/4\n" + 
                "- String: 1/4\n"
            ),
            Component.text
            (
                "Wool\n\n" +
                "Speed Multiplier: 1\n\n" +
                "- Wool (4): 1/1\n"
            ),
            Component.text
            (
                "Baked Potatoes\n\n" +
                "Speed Multiplier: 2\n\n" +
                "- Baked Potato: 1/1\n"
            ),
            Component.text
            (
                "Explosive Materials\n\n" +
                "Speed Multiplier: 1.5\n\n" +
                "- Coal: 1/6\n" +
                "- Flint: 1/6\n" +
                "- Gunpowder (3): 3/6\n" +
                "- Sand (2): 1/6\n"
            ),
            Component.text
            (
                "Iron\n\n" +
                "Speed Multiplier: 1\n\n" +
                "- Iron Block: 1/10\n" +
                "- Iron Ingot: 9/10\n"
            ),
            Component.text
            (
                "Redstone\n\n" +
                "Speed Multiplier: 1.5\n\n" +
                "- Redstone: 15/16\n" +
                "- Repeater: 1/16\n"
            ),
            Component.text
            (
                "Diamonds\n\n" +
                "Speed Multiplier: 1\n\n" +
                "- Diamond: 1/1\n"
            ),
            Component.text
            (
                "Nether Loot\n\n" +
                "Speed Multiplier: 1\n\n" +
                "- Blaze Powder (2): 3/13\n" +
                "- Cooked Porkchop (2): 3/13\n" +
                "- Enchanted Golden Apple: 1/13\n" +
                "- Ender Pearl: 3/13\n" +
                "- Gold Block: 3/13\n"
            ),
            Component.text
            (
                "Potions\n\n" +
                "Speed Multiplier: 0.5\n" +
                "(1/10 chance for all)\n" +
                "- Healing\n" +
                "- Invisibility\n" +
                "- Regeneration\n" +
                "- Splash Damage\n" +
                "- Splash Healing\n" +
                "- Splash Poison\n" +
                "- Splash Slowness\n" +
                "- Splash Weakness\n" +
                "- Strength\n" +
                "- Swiftness\n"
            )
        );

        generator_resources_book.setItemMeta(generator_resources_book_meta);
        player.getInventory().setItem(3, generator_resources_book);


        ItemStack restricted_items_book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta restricted_items_book_meta = (BookMeta) restricted_items_book.getItemMeta();

        restricted_items_book_meta.setTitle("Restricted Items");
        restricted_items_book_meta.setAuthor("Leeyum");

        
        restricted_items_book_meta.addPages(
            Component.text(
                "Restricted Items:\n\n" +
                "- Buttons\n" +
                "- Barrels\n" +
                "- Beds\n" +
                "- Boats\n" +
                "- Buckets\n" +
                "- Calibrated Skulk Sensors\n" +
                "- Chests\n" +
                "- Chiseled Bookshelves\n" +
                "- Crafting Tables\n" +
                "- Daylight Detectors"
            ),
            Component.text
            (
                "- Detector Rails\n" +
                "- Fletching Tables\n" +
                "- Jukeboxes\n" +
                "- Lecterns\n" +
                "- Levers\n" +
                "- Lightning Rods\n" +
                "- Looms\n" +
                "- Minecarts\n" +
                "- Note Blocks\n" +
                "- Observers\n" +
                "- Pistons\n" +
                "- Pressure Plates\n" +
                "- Signs\n" +
                "- Redstone Blocks"
            ),
            Component.text
            (
                "- Redstone Torches\n" +
                "- Repeaters\n" +
                "- Smithing Tables\n" +
                "- Targets\n" +
                "- Tripwire Hooks"
            )
        );
        restricted_items_book.setItemMeta(restricted_items_book_meta);
        player.getInventory().setItem(5, restricted_items_book);

        ItemStack gameplay_tips_book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta gameplay_tips_book_meta = (BookMeta) gameplay_tips_book.getItemMeta();

        gameplay_tips_book_meta.setTitle("Gameplay Tips");
        gameplay_tips_book_meta.setAuthor("Leeyum");

        gameplay_tips_book_meta.addPages(
            Component.text
            (
                "Gameplay Tips\n\n" +
                "- To maintain power, a repeater is needed once for every 15 redstone wire.\n\n" +
                "- Repeaters must point in the direction of outgoing current to work."
            ),
            Component.text
            (
                "- When crafted, fire charges can be thrown like fireballs.\n\n" +
                "- This is post 1.9 combat, so craft shields and time your swings.\n\n" +
                "- Team chests are locked, so use them to store items and extra redstone for your team."
            ),
            Component.text
            (
                "- Don't be afraid to push towards the tower early.\n\n" +
                "- Repeaters are significantly rarer than redstone and are the most effective blocks to destroy."
            ),
            Component.text
            (
                "- Cutting off your opponent's redstone path closer to the source may affect multiple generators.\n\n" +
                "- Stealth flanks can be extremely effective, especially with large teams."
            ),
            Component.text
            (
                "- If your side of the tower is powered, your opponent's spawn will be disabled... regardless of whose source is powering it."
            )
        );
        gameplay_tips_book.setItemMeta(gameplay_tips_book_meta);
        player.getInventory().setItem(7, gameplay_tips_book);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            if (PL_GameManager.gm.team_a_members.contains(player) ||
                PL_GameManager.gm.team_b_members.contains(player) ||
                PL_GameManager.gm.spectator_members.contains(player)){ PL_ScoreboardManager.PostLobbyScoreboard(); break; }
            else if (PL_GameManager.gm.team_a_members.size() <= PL_GameManager.gm.team_b_members.size())
                PL_GameManager.gm.AddMember(PL_GameManager.gm.team_a_members, player);
            else PL_GameManager.gm.AddMember(PL_GameManager.gm.team_b_members, player);
            PL_ScoreboardManager.PostLobbyScoreboard();
            GiveBooks(player);
            player.teleport(PL_GameManager.gm.lobby.spawn.location);
            player.setGameMode(GameMode.SURVIVAL);
            break;
            case STARTING:
            case PLAYING:
            case ENDING:
            if (PL_GameManager.gm.player_map.containsKey(player)) 
            {
                return;
            } else if (PL_GameManager.gm.spectator_map.containsKey(player))
            {
                return;
            } else 
            {
                PL_Spectator spectator = new PL_Spectator(player);
                PL_GameManager.gm.match.spectators.add(spectator);
                PL_GameManager.gm.spectator_map.put(player, spectator);
                player.setGameMode(GameMode.SPECTATOR);
                player.setLevel(0);
                PL_DeathManager.ClearInventory(player);
                PL_DeathManager.ClearStats(player);
                player.teleport(PL_GameManager.gm.match.map.spectator_spawn.location);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                player.setBedSpawnLocation(PL_GameManager.gm.match.map.spectator_spawn.location, true);
            }
            break;
            case TUTORIAL:
            PL_FreezeManager.Freeze(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(PL_GameManager.gm.tutorial_sequence.current_spot.location);
            case DEBUG:
            break;
            default:
            break;
        }
        
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            if (PL_GameManager.gm.team_a_members.contains(player)) PL_GameManager.gm.team_a_members.remove(player);
            else if (PL_GameManager.gm.team_b_members.contains(player)) PL_GameManager.gm.team_b_members.remove(player);
            else if (PL_GameManager.gm.spectator_members.contains(player)) PL_GameManager.gm.spectator_members.remove(player);
            break;
            case STARTING:
            break;
            case PLAYING:
            if (PL_GameManager.gm.player_map.containsKey(player)) if ((!PL_Helpers.ValidateWithout(PL_GameManager.gm.player_map.get(player)) && PL_GameManager.gm.player_map.get(player).team.status == PL_TeamStatus.DISADVANTAGE)) PL_GameManager.gm.EndMatch(PL_GameManager.gm.player_map.get(player).team);
            case ENDING:
            case TUTORIAL:
            case DEBUG:
            break;
            default:
            break;
        }

    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            RightClickItemFrame(event);
            break;
            case STARTING:
            break;
            case PLAYING:
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
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            LeftClickItemFrame(event);
            break;
            case STARTING:
            break;
            case PLAYING:
            break;
            case ENDING:
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }

    public static void UpdatePostMatchLeaderboard(PL_Match match)
    {
        Block winner = PL_GameManager.gm.lobby.pm_winner.location.getBlock();
        if (winner.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            String win_text = "N/A (DRAW)";
            if (match.map.team_a.status == PL_TeamStatus.LOST) win_text = "TEAM LAPIS";
            else if (match.map.team_b.status == PL_TeamStatus.LOST) win_text = "TEAM EMERALD";
            sign.setLine(0, "WINNER");
            sign.setLine(1, "");
            sign.setLine(2, win_text);
            sign.setLine(3, "");
            sign.update();
        }

        Block match_length = PL_GameManager.gm.lobby.pm_match_length.location.getBlock();
        if (match_length.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            String min = Integer.toString(match.time_elapsed / 60) + " MIN";
            String sec = Integer.toString(match.time_elapsed % 60) + " SEC";
            sign.setLine(0, "MATCH LENGTH");
            sign.setLine(1, "");
            sign.setLine(2, min);
            sign.setLine(3, sec);
            sign.update();
        }

        Block lobby_record = PL_GameManager.gm.lobby.pm_lobby_record.location.getBlock();
        if (lobby_record.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);

            String record = Integer.toString(PL_GameManager.gm.team_a_wins) + " - " + Integer.toString(PL_GameManager.gm.team_b_wins);
            String leader = "EVEN";
            if (PL_GameManager.gm.team_a_wins > PL_GameManager.gm.team_b_wins) leader = "TEAM EMERALD";
            else if (PL_GameManager.gm.team_b_wins > PL_GameManager.gm.team_a_wins) leader = "TEAM LAPIS";
            sign.setLine(0, "LOBBY RECORD");
            sign.setLine(1, "");
            sign.setLine(2, record);
            sign.setLine(3, leader);
            sign.update();
        }

        Block redstone_placed_sign = PL_GameManager.gm.lobby.pm_redstone_placed_sign.location.getBlock();
        if (redstone_placed_sign.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            String leader = "";
            PL_Player pl_leader = null;
            int placed = -1;
            for (PL_Player p : match.players) if (p.GetRedstonePlaced() > placed) 
            {
                pl_leader = p;
                leader = p.player.getName();
                placed = p.GetRedstonePlaced();
            }

            sign.setLine(0, "REDSTONE PLACED");
            sign.setLine(1, "");
            sign.setLine(2, leader);
            sign.setLine(3, Integer.toString(placed));
            sign.update();

            Block head = PL_GameManager.gm.lobby.pm_redstone_placed_head.location.getBlock();
            head.setType(Material.PLAYER_HEAD);
            Rotatable rot = (Rotatable) head.getBlockData();
            rot.setRotation(BlockFace.NORTH_WEST);
            head.setBlockData(rot);
            if (head.getState() instanceof Skull skull)
            {
                PlayerProfile profile = Bukkit.createProfile(pl_leader.player.getUniqueId());
                skull.setOwnerProfile(profile);
                skull.update(true, false);
            }
        }

        Block redstone_destroyed_sign = PL_GameManager.gm.lobby.pm_redstone_destroyed_sign.location.getBlock();
        if (redstone_destroyed_sign.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            String leader = "";
            PL_Player pl_leader = null;
            int destroyed = -1;
            for (PL_Player p : match.players)
            {
                if (p.GetRedstoneDestroyed() > destroyed) 
                {
                    pl_leader = p;
                    leader = p.player.getName();
                    destroyed = p.GetRedstoneDestroyed();
                }
            } 

            sign.setLine(0, "REDSTONE");
            sign.setLine(1, "DESTROYED");
            sign.setLine(2, leader);
            sign.setLine(3, Integer.toString(destroyed));
            sign.update();

            Block head = PL_GameManager.gm.lobby.pm_redstone_destroyed_head.location.getBlock();
            head.setType(Material.PLAYER_HEAD);
            Rotatable rot = (Rotatable) head.getBlockData();
            rot.setRotation(BlockFace.NORTH_EAST);
            head.setBlockData(rot);
            if (head.getState() instanceof Skull skull)
            {
                PlayerProfile profile = Bukkit.createProfile(pl_leader.player.getUniqueId());
                skull.setOwnerProfile(profile);
                skull.update(true, false);
            }
        }

        Block kills_sign = PL_GameManager.gm.lobby.pm_kills_sign.location.getBlock();
        if (kills_sign.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            String leader = "";
            PL_Player pl_leader = null;
            int kills = -1;
            for (PL_Player p : match.players) if (p.GetKills() >= kills) 
            {
                if (p.GetKills() == kills) 
                {
                    if (p.GetFinalElims() > pl_leader.GetFinalElims()) 
                    {
                        pl_leader = p;
                        leader = p.player.getName();
                        kills = p.GetKills();
                    } 
                } else
                {
                    pl_leader = p;
                    leader = p.player.getName();
                    kills = p.GetKills();
                }
            }

            sign.setLine(0, "KILLS");
            sign.setLine(1, "");
            sign.setLine(2, leader);
            sign.setLine(3, Integer.toString(kills));
            sign.update();

            Block head = PL_GameManager.gm.lobby.pm_kills_head.location.getBlock();
            head.setType(Material.PLAYER_HEAD);
            Rotatable rot = (Rotatable) head.getBlockData();
            rot.setRotation(BlockFace.NORTH);
            head.setBlockData(rot);
            if (head.getState() instanceof Skull skull)
            {
                PlayerProfile profile = Bukkit.createProfile(pl_leader.player.getUniqueId());
                skull.setOwnerProfile(profile);
                skull.update(true, false);
            }
        }
    }

    
    public static void InitializePostMatchLeaderboard()
    {
        Block winner = PL_GameManager.gm.lobby.pm_winner.location.getBlock();
        if (winner.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            
            sign.setLine(0, "");
            sign.setLine(1, "POST-MATCH");
            sign.setLine(2, "LEADERBOARD");
            sign.setLine(3, "");
            sign.update();
        }

        Block match_length = PL_GameManager.gm.lobby.pm_match_length.location.getBlock();
        if (match_length.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            sign.setLine(0, "MATCH LENGTH");
            sign.setLine(1, "");
            sign.setLine(2, "0 MIN");
            sign.setLine(3, "0 SEC");
            sign.update();
        }

        Block lobby_record = PL_GameManager.gm.lobby.pm_lobby_record.location.getBlock();
        if (lobby_record.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            sign.setLine(0, "LOBBY RECORD");
            sign.setLine(1, "");
            sign.setLine(2, "0 - 0");
            sign.setLine(3, "");
            sign.update();
        }

        Block redstone_placed_sign = PL_GameManager.gm.lobby.pm_redstone_placed_sign.location.getBlock();
        if (redstone_placed_sign.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            sign.setLine(0, "REDSTONE PLACED");
            sign.setLine(1, "");
            sign.setLine(2, "STEVE");
            sign.setLine(3, "0");
            sign.update();

            Block head = PL_GameManager.gm.lobby.pm_redstone_placed_head.location.getBlock();
            head.setType(Material.PLAYER_HEAD);
            Rotatable rot = (Rotatable) head.getBlockData();
            rot.setRotation(BlockFace.NORTH_WEST);
            head.setBlockData(rot);
            Skull skull = (Skull) head.getState();
            skull.setOwnerProfile(null);
            skull.update(true, false);
        }

        Block redstone_destroyed_sign = PL_GameManager.gm.lobby.pm_redstone_destroyed_sign.location.getBlock();
        if (redstone_destroyed_sign.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            sign.setLine(0, "REDSTONE");
            sign.setLine(1, "DESTROYED");
            sign.setLine(2, "STEVE");
            sign.setLine(3, "0");
            sign.update();

            Block head = PL_GameManager.gm.lobby.pm_redstone_destroyed_head.location.getBlock();
            head.setType(Material.PLAYER_HEAD);
            Rotatable rot = (Rotatable) head.getBlockData();
            rot.setRotation(BlockFace.NORTH_EAST);
            head.setBlockData(rot);
            Skull skull = (Skull) head.getState();
            skull.setOwnerProfile(null);
            skull.update(true, false);
        }

        Block kills_sign = PL_GameManager.gm.lobby.pm_kills_sign.location.getBlock();
        if (kills_sign.getState() instanceof Sign sign)
        {
            SignSide front = sign.getSide(Side.FRONT);
            front.setGlowingText(true);
            
            sign.setLine(0, "KILLS");
            sign.setLine(1, "");
            sign.setLine(2, "STEVE");
            sign.setLine(3, "0");
            sign.update();

            Block head = PL_GameManager.gm.lobby.pm_kills_head.location.getBlock();
            head.setType(Material.PLAYER_HEAD);
            Rotatable rot = (Rotatable) head.getBlockData();
            rot.setRotation(BlockFace.NORTH);
            head.setBlockData(rot);
            Skull skull = (Skull) head.getState();
            skull.setOwnerProfile(null);
            skull.update(true, false);
        }
    }

    public void LeftClickItemFrame(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof ItemFrame frame)) return;
        
        Location loc = frame.getLocation();
        ItemStack in_hand = player.getInventory().getItemInMainHand();
        if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.wood_hoe.location))
        {
            if (in_hand.getType() == Material.WOODEN_HOE) player.getInventory().setItemInMainHand(null);
            else
            {
                ItemStack hoe = new ItemStack(Material.WOODEN_HOE, 1);
                player.getInventory().setItem(0, hoe);
            }
        }
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.iron_hoe.location))
        {
            if (in_hand.getType() == Material.IRON_HOE) player.getInventory().setItemInMainHand(null);
            else
            {
                ItemStack hoe = new ItemStack(Material.IRON_HOE, 1);
                player.getInventory().setItem(0, hoe);
            }
        }
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.gold_hoe.location))
        {
            if (in_hand.getType() == Material.GOLDEN_HOE) player.getInventory().setItemInMainHand(null);
            else
            {
                ItemStack hoe = new ItemStack(Material.GOLDEN_HOE, 1);
                player.getInventory().setItem(0, hoe);
            }
        }
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.diamond_hoe.location))
        {
            if (in_hand.getType() == Material.DIAMOND_HOE) player.getInventory().setItemInMainHand(null);
            else
            {
                ItemStack hoe = new ItemStack(Material.DIAMOND_HOE, 1);
                player.getInventory().setItem(0, hoe);
            }
        }
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.join_team_a.location))
        {
            PL_GameManager.gm.AddMember(PL_GameManager.gm.team_a_members, player);
        }
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.join_team_b.location))
        {
            PL_GameManager.gm.AddMember(PL_GameManager.gm.team_b_members, player);
        }
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.join_spectators.location))
        {
            PL_GameManager.gm.AddMember(PL_GameManager.gm.spectator_members, player);
        }
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.watch_tutorial.location))
        {
            PL_GameManager.gm.tutorial_sequence.PlayTutorial();
        }
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.start_match.location))
        {
            PL_GameManager.gm.StartMatch();
        }
        event.setCancelled(true);
    }

    public void RightClickItemFrame(PlayerInteractEntityEvent event)
    {
        Entity entity = event.getRightClicked();
        if (entity instanceof ItemFrame frame)
        {
            Location loc = frame.getLocation();
            Player player = event.getPlayer();
            ItemStack in_hand = player.getInventory().getItemInMainHand();
            if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.wood_hoe.location))
            {
                if (in_hand.getType() == Material.WOODEN_HOE) player.getInventory().setItemInMainHand(null);
                else
                {
                    ItemStack hoe = new ItemStack(Material.WOODEN_HOE, 1);
                    player.getInventory().setItem(0, hoe);
                }
            }
            else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.iron_hoe.location))
            {
                if (in_hand.getType() == Material.IRON_HOE) player.getInventory().setItemInMainHand(null);
                else
                {
                    ItemStack hoe = new ItemStack(Material.IRON_HOE, 1);
                    player.getInventory().setItem(0, hoe);
                }
            }
            else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.gold_hoe.location))
            {
                if (in_hand.getType() == Material.GOLDEN_HOE) player.getInventory().setItemInMainHand(null);
                else
                {
                    ItemStack hoe = new ItemStack(Material.GOLDEN_HOE, 1);
                    player.getInventory().setItem(0, hoe);
                }
            }
            else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.diamond_hoe.location))
            {
                if (in_hand.getType() == Material.DIAMOND_HOE) player.getInventory().setItemInMainHand(null);
                else
                {
                    ItemStack hoe = new ItemStack(Material.DIAMOND_HOE, 1);
                    player.getInventory().setItem(0, hoe);
                }
            }
            else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.join_team_a.location))
            {
                PL_GameManager.gm.AddMember(PL_GameManager.gm.team_a_members, player);
            }
            else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.join_team_b.location))
            {
                PL_GameManager.gm.AddMember(PL_GameManager.gm.team_b_members, player);
            }
            else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.join_spectators.location))
            {
                PL_GameManager.gm.AddMember(PL_GameManager.gm.spectator_members, player);
            }
            else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.watch_tutorial.location))
            {
                PL_GameManager.gm.tutorial_sequence.PlayTutorial();
            }
            else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.start_match.location))
            {
                PL_GameManager.gm.StartMatch();
            }
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerClickSign(PlayerInteractEvent event)
    {
        switch (PL_GameManager.gm.match.status)
        {
            case WAITING:
            clickSign(event);
            break;
            case STARTING:
            break;
            case PLAYING:
            break;
            case ENDING:
            break;
            case DEBUG:
            break;
            default:
            break;
        }
    }

    public void clickSign(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        Block clicked = event.getClickedBlock();
        if (clicked == null) return;
        Material type = clicked.getType();
        if (!type.name().endsWith("_SIGN")) return;

        Location loc = clicked.getLocation();
        Player player = event.getPlayer();
        if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.join_team_a.location))
        {
            PL_GameManager.gm.AddMember(PL_GameManager.gm.team_a_members, player);
            event.setCancelled(true);
        } 
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.join_team_b.location))
        {
            PL_GameManager.gm.AddMember(PL_GameManager.gm.team_b_members, player);
            event.setCancelled(true);
        } 
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.join_spectators.location))
        {
            PL_GameManager.gm.AddMember(PL_GameManager.gm.spectator_members, player);
            event.setCancelled(true);
        } 
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.change_map.location))
        {
            event.setCancelled(true);
        } 
        else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.start_match.location))
        {
            PL_GameManager.gm.StartMatch();
            event.setCancelled(true);
        } else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.watch_tutorial.location)) 
        {
            PL_GameManager.gm.tutorial_sequence.PlayTutorial();
            event.setCancelled(true);
        } else if (PL_Helpers.CompareLocations(loc, PL_GameManager.gm.lobby.test_hoes.location))
        {
            SpawnTestRedstone();
            event.setCancelled(true);
        }
        event.setCancelled(true);
    }

    public void SpawnTestRedstone()
    {
        Block wire = PL_GameManager.gm.lobby.test_redstone_floor.location.getBlock();
        wire.setType(Material.REDSTONE_WIRE);

        Block repeater = PL_GameManager.gm.lobby.test_repeater_floor.location.getBlock();
        repeater.setType(Material.REPEATER);
    }
}
