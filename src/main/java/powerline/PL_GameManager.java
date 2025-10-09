//PL_GameManager.java
//Auth Liam McKenna, 2025

package powerline;

import java.util.Vector;
import java.util.HashMap;
import java.util.Random;

import static net.kyori.adventure.text.Component.text;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Entity;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.EventHandler;

public class PL_GameManager implements Listener
{    
    final Plugin plugin;
    PL_Lobby lobby;
    PL_TutorialSequence tutorial_sequence;
    PL_Match match;
    String map_name;
    Vector<Player> team_a_members;
    Vector<Player> team_b_members;
    Vector<Player> spectator_members;
    Vector<Material> dynamic_blocks;
    public HashMap<Player, PL_Player> player_map;
    public HashMap<Player, PL_Spectator> spectator_map;
    PL_Player most_recent_conductor;
    Random random;
    int team_a_wins;
    int team_b_wins;

    public static PL_GameManager gm;

    public PL_GameManager(Plugin plugin)
    {
        this.plugin = plugin;
        team_a_wins = 0;
        team_b_wins = 0;
        match = new PL_Match();
        team_a_members = new Vector<>();
        team_b_members = new Vector<>();
        spectator_members = new Vector<>();
        dynamic_blocks = new Vector<>();
        map_name = "factory";
        player_map = new HashMap<>();
        spectator_map = new HashMap<>();
        most_recent_conductor = null;
        random = new Random();
        lobby = PL_LobbyManager.LoadLobby();
        CacheResources();
        SetDynamicBlocks();
        World world = Bukkit.getWorld("powerline");
        world.setGameRule(GameRule.KEEP_INVENTORY, false);
        world.setDifficulty(Difficulty.PEACEFUL);
        for (Entity entity : world.getEntities()) if (!(entity instanceof Player) && !(entity instanceof ItemFrame)) entity.remove();   
        for(Player player : Bukkit.getOnlinePlayers()) PL_LobbyManager.GiveBooks(player);
    }

    public void SetDynamicBlocks()
    {
        dynamic_blocks.add(Material.REDSTONE_WIRE);
        dynamic_blocks.add(Material.REPEATER);
        dynamic_blocks.add(Material.OAK_PLANKS);
        dynamic_blocks.add(Material.BLUE_WOOL);
        dynamic_blocks.add(Material.LIME_WOOL);
        dynamic_blocks.add(Material.TNT);
        dynamic_blocks.add(Material.IRON_BLOCK);
        dynamic_blocks.add(Material.GOLD_BLOCK);
        dynamic_blocks.add(Material.SAND);
        dynamic_blocks.add(Material.COBWEB);
        dynamic_blocks.add(Material.STRING);
        dynamic_blocks.add(Material.OAK_FENCE_GATE);
        dynamic_blocks.add(Material.OAK_FENCE);
        dynamic_blocks.add(Material.OAK_DOOR);
        dynamic_blocks.add(Material.OAK_SLAB);
        dynamic_blocks.add(Material.OAK_FENCE_GATE);
        dynamic_blocks.add(Material.OAK_STAIRS);
        dynamic_blocks.add(Material.OAK_TRAPDOOR);
    }

    public void AddMember(Vector<Player> team, Player player) 
    {
        if (team_a_members.contains(player)) team_a_members.remove(player);
        if (team_b_members.contains(player)) team_b_members.remove(player);
        if (spectator_members.contains(player)) spectator_members.remove(player);
        team.add(player);
        String player_name = player.getName();
        if (team == team_a_members) Bukkit.broadcast(text(player_name + " has joined Team Emerald!"));
        else if (team == team_b_members) Bukkit.broadcast(text(player_name + " has joined Team Lapis!"));
        else if (team == spectator_members) Bukkit.broadcast(text(player_name + " is now spectating!"));
        PL_ScoreboardManager.PostLobbyScoreboard();
    }

    public void SetMapName(String new_name)
    { 
        switch (new_name.toLowerCase())
        {
            case "factory":
                map_name = "factory";
                Bukkit.broadcast(text("Map set: Factory"));
                break;
            default:
                Bukkit.broadcast(text("Invalid map name. Try again."));
                Bukkit.broadcast(text("Set map: " + map_name));
            break;
        }
    }


    
    public void StartMatch() 
    {
        if (match.status == PL_MatchStatus.WAITING && (team_a_members.isEmpty() || team_b_members.isEmpty())) 
            Bukkit.broadcast(text("Both teams must have at least one member to start!"));
        else if (match.status == PL_MatchStatus.DEBUG && team_a_members.isEmpty() && team_b_members.isEmpty())
            Bukkit.broadcast(text("At least one team must have a member to start"));
        else 
        {
            player_map = new HashMap<>();
            spectator_map = new HashMap<>();
            match.Start(map_name, team_a_members, team_b_members, spectator_members);
            World world = Bukkit.getWorld("powerline");
            world.setDifficulty(Difficulty.NORMAL);
        }
    }

    public void EndMatch(PL_Team loser) 
    {
        match.End(loser);
    }

    public void ResetMatch()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            PL_DeathManager.ClearInventory(player);
            PL_DeathManager.ClearStats(player);
            PL_DeathManager.SetDefaultStats(player);
            PL_DeathManager.ClearEffects(player);
            PL_LobbyManager.GiveBooks(player);
            player.teleport(lobby.spawn.location);
            player.setGameMode(GameMode.SURVIVAL);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            player.setBedSpawnLocation(lobby.spawn.location);
        }
        World world = Bukkit.getWorld("powerline");
        world.setDifficulty(Difficulty.PEACEFUL);
        match.ResetMap();
        PL_Match old_match = match;
        if (old_match.map.team_a.status == PL_TeamStatus.LOST) team_b_wins++;
        else if (old_match.map.team_b.status == PL_TeamStatus.LOST) team_a_wins++;
        PL_LobbyManager.UpdatePostMatchLeaderboard(old_match);
        match = new PL_Match();
        PL_ScoreboardManager.PostLobbyScoreboard();
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) { event.setCancelled(true); }

    public static void CacheResources() 
    {
        //sticks
        PL_Resource.plr_sticks = new PL_Resource();
        PL_Resource.plr_sticks.AddDrop(new PL_Drop(new ItemStack(Material.STICK), 6));
        PL_Resource.plr_sticks.AddDrop(new PL_Drop(new ItemStack(Material.OAK_PLANKS, 2), 1));
        PL_Resource.plr_sticks.AddDrop(new PL_Drop(new ItemStack(Material.APPLE, 1), 3));
        PL_Resource.plr_sticks.icon = new ItemStack(Material.OAK_SAPLING);
        PL_Resource.plr_sticks.speed_multiplier = 1f;
        PL_Resource.plr_sticks.name = "Sticks and Apples";
        PL_Resource.plr_sticks.description = "Generate sticks for crafting and apples for replenishment.";

        //wool_a
        PL_Resource.plr_wool_a = new PL_Resource();
        PL_Resource.plr_wool_a.AddDrop(new PL_Drop(new ItemStack(Material.LIME_WOOL, 4), 10));
        PL_Resource.plr_wool_a.icon = new ItemStack(Material.LIME_WOOL);
        PL_Resource.plr_wool_a.speed_multiplier = 1f;
        PL_Resource.plr_wool_a.name = "Wool";
        PL_Resource.plr_wool_a.description = "Generate wool blocks for building.";

        //wool_b
        PL_Resource.plr_wool_b = new PL_Resource();
        PL_Resource.plr_wool_b.AddDrop(new PL_Drop(new ItemStack(Material.BLUE_WOOL, 4), 10));
        PL_Resource.plr_wool_b.icon = new ItemStack(Material.BLUE_WOOL);
        PL_Resource.plr_wool_b.speed_multiplier = 1f;
        PL_Resource.plr_wool_b.name = "Wool";
        PL_Resource.plr_wool_b.description = "Generate wool blocks for building.";
        
        //string and arrows
        PL_Resource.plr_string_and_arrows = new PL_Resource();
        PL_Resource.plr_string_and_arrows.AddDrop(new PL_Drop(new ItemStack(Material.ARROW, 2), 3));
        PL_Resource.plr_string_and_arrows.AddDrop(new PL_Drop(new ItemStack(Material.STRING), 1));
        PL_Resource.plr_string_and_arrows.icon = new ItemStack(Material.ARROW);
        PL_Resource.plr_string_and_arrows.speed_multiplier = 2f;
        PL_Resource.plr_string_and_arrows.name = "String and Arrows";
        PL_Resource.plr_string_and_arrows.description = "Generate string and arrows for bows.";
        
        //iron
        PL_Resource.plr_iron = new PL_Resource();
        PL_Resource.plr_iron.AddDrop(new PL_Drop(new ItemStack(Material.IRON_INGOT), 9));
        PL_Resource.plr_iron.AddDrop(new PL_Drop(new ItemStack(Material.IRON_BLOCK), 1));
        PL_Resource.plr_iron.icon = new ItemStack(Material.IRON_INGOT);
        PL_Resource.plr_iron.speed_multiplier = 1f;
        PL_Resource.plr_iron.name = "Iron";
        PL_Resource.plr_iron.description = "Generate iron for crafting tools and armor.";

        //baked potatoes
        PL_Resource.plr_potatoes = new PL_Resource();
        PL_Resource.plr_potatoes.AddDrop(new PL_Drop(new ItemStack(Material.BAKED_POTATO), 10));
        PL_Resource.plr_potatoes.icon = new ItemStack(Material.BAKED_POTATO);
        PL_Resource.plr_potatoes.speed_multiplier = 2f;
        PL_Resource.plr_potatoes.name = "Baked Potatoes";
        PL_Resource.plr_potatoes.description = "Generate baked potatoes for restoring hunger.";

        //potions
        PL_Resource.plr_potions = new PL_Resource();        
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.POTION, PotionType.SPEED, false, false), 1));
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.POTION, PotionType.STRENGTH, false, false), 1));
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.POTION, PotionType.INVISIBILITY, false, false), 1));
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.POTION, PotionType.INSTANT_HEAL, false, false), 1));
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.POTION, PotionType.REGEN, false, false), 1));
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.SPLASH_POTION, PotionType.INSTANT_HEAL, false, false), 1));
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.SPLASH_POTION, PotionType.INSTANT_DAMAGE, false, false), 1));
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.SPLASH_POTION, PotionType.POISON, false, false), 1));
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.SPLASH_POTION, PotionType.SLOWNESS, false, false), 1));
        PL_Resource.plr_potions.AddDrop(new PL_Drop(PL_Helpers.CreatePotion(Material.SPLASH_POTION, PotionType.WEAKNESS, false, false), 1));
        PL_Resource.plr_potions.icon = PL_Helpers.CreatePotion(Material.POTION, PotionType.SPEED, false, false);
        PL_Resource.plr_potions.speed_multiplier = 0.5f;
        PL_Resource.plr_potions.name = "Potions";
        PL_Resource.plr_potions.description = "Generate potions for combat.";

        //explosives
        PL_Resource.plr_explosives = new PL_Resource();
        PL_Resource.plr_explosives.AddDrop(new PL_Drop(new ItemStack(Material.GUNPOWDER, 3), 3));
        PL_Resource.plr_explosives.AddDrop(new PL_Drop(new ItemStack(Material.SAND, 2), 1));
        PL_Resource.plr_explosives.AddDrop(new PL_Drop(new ItemStack(Material.COAL, 1), 1));
        PL_Resource.plr_explosives.AddDrop(new PL_Drop(new ItemStack(Material.FLINT, 1), 1));
        PL_Resource.plr_explosives.icon = new ItemStack(Material.GUNPOWDER);
        PL_Resource.plr_explosives.speed_multiplier = 1.5f;
        PL_Resource.plr_explosives.name = "Explosive Materials";
        PL_Resource.plr_explosives.description = "Generate materials for crafting explosives.";

        //diamond
        PL_Resource.plr_diamond = new PL_Resource();
        PL_Resource.plr_diamond.AddDrop(new PL_Drop(new ItemStack(Material.DIAMOND), 10));
        PL_Resource.plr_diamond.icon = new ItemStack(Material.DIAMOND);
        PL_Resource.plr_diamond.speed_multiplier = 1f;
        PL_Resource.plr_diamond.name = "Diamonds";
        PL_Resource.plr_diamond.description = "Generate diamonds for crafting tools and armor.";

        //nether
        PL_Resource.plr_nether = new PL_Resource();
        PL_Resource.plr_nether.AddDrop(new PL_Drop(new ItemStack(Material.ENDER_PEARL), 3));
        PL_Resource.plr_nether.AddDrop(new PL_Drop(new ItemStack(Material.BLAZE_POWDER, 2), 3));
        PL_Resource.plr_nether.AddDrop(new PL_Drop(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1), 1));
        PL_Resource.plr_nether.AddDrop(new PL_Drop(new ItemStack(Material.COOKED_PORKCHOP, 2), 3));
        PL_Resource.plr_nether.AddDrop(new PL_Drop(new ItemStack(Material.GOLD_BLOCK), 3));
        PL_Resource.plr_nether.icon = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        PL_Resource.plr_nether.speed_multiplier = 1;
        PL_Resource.plr_nether.name = "Nether Loot";
        PL_Resource.plr_nether.description = "Generate loot from the Nether.";
    
        //redstone
        PL_Resource.plr_redstone = new PL_Resource();
        PL_Resource.plr_redstone.AddDrop(new PL_Drop(new ItemStack(Material.REDSTONE), 15));
        PL_Resource.plr_redstone.AddDrop(new PL_Drop(new ItemStack(Material.REPEATER), 1));
        PL_Resource.plr_redstone.icon = new ItemStack(Material.REDSTONE);
        PL_Resource.plr_redstone.speed_multiplier = 1.5f;
        PL_Resource.plr_redstone.name = "Redstone";
        PL_Resource.plr_redstone.description = "Generate redstone for conducting power.";
    }
}
