//PL_ScoreboardManager.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;

public class PL_ScoreboardManager 
{
    public static void PostLobbyScoreboard()
    {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Team emerald = board.registerNewTeam("emerald");
        emerald.setDisplayName(ChatColor.GREEN + "Team Emerald");
        emerald.setColor(ChatColor.GREEN);

        Team lapis = board.registerNewTeam("lapis");
        lapis.setDisplayName(ChatColor.BLUE + "Team Lapis");
        lapis.setColor(ChatColor.BLUE);

        Team spectators = board.registerNewTeam("spectators");
        spectators.setDisplayName(ChatColor.GRAY + "Spectators");
        spectators.setColor(ChatColor.GRAY);

        if (!PL_GameManager.gm.team_a_members.isEmpty()) for (Player p : PL_GameManager.gm.team_a_members) emerald.addEntry(p.getName());
        if (!PL_GameManager.gm.team_b_members.isEmpty()) for (Player p : PL_GameManager.gm.team_b_members) lapis.addEntry(p.getName());
        if (!PL_GameManager.gm.spectator_members.isEmpty()) for (Player p : PL_GameManager.gm.spectator_members) spectators.addEntry(p.getName());

        for (Player p : Bukkit.getOnlinePlayers()) p.setScoreboard(board);
    }

    public static void PostGameScoreboard()
    {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("teams", "dummy", ChatColor.GOLD + "PowerLine Teams");
        obj.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        Team emerald = board.registerNewTeam("emerald");
        emerald.setDisplayName(ChatColor.GREEN + "Team Emerald");
        emerald.setColor(ChatColor.GREEN);

        Team lapis = board.registerNewTeam("lapis");
        lapis.setDisplayName(ChatColor.BLUE + "Team Lapis");
        lapis.setColor(ChatColor.BLUE);

        for (PL_Player p : PL_GameManager.gm.match.map.team_a.members) 
        {
            emerald.addEntry(p.player.getName());
            obj.getScore(p.player.getName()).setScore(p.GetKills());
        } 
        for (PL_Player p : PL_GameManager.gm.match.map.team_b.members) 
        {
            lapis.addEntry(p.player.getName());
            obj.getScore(p.player.getName()).setScore(p.GetKills());
        }

        for (Player p : Bukkit.getOnlinePlayers()) p.setScoreboard(board);
    }
}
