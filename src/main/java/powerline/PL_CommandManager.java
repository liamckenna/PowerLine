//PL_CommandManager.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;

public class PL_CommandManager implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
        Player player = (Player) sender;
        String arg0 = "";
        if (args.length > 0) arg0 = args[0];
        switch (command.getName().toLowerCase()) 
        {
            case "jointeama":
                if (arg0 != "") 
                {
                    Player target = Bukkit.getPlayerExact(arg0);
                    if (target == null) break;
                    else PL_GameManager.gm.AddMember(PL_GameManager.gm.team_a_members, target);
                } else PL_GameManager.gm.AddMember(PL_GameManager.gm.team_a_members, player);
                break;
            case "jointeamb":
                if (arg0 != "") 
                {
                    Player target = Bukkit.getPlayerExact(arg0);
                    if (target == null) break;
                    else PL_GameManager.gm.AddMember(PL_GameManager.gm.team_b_members, target);
                } else PL_GameManager.gm.AddMember(PL_GameManager.gm.team_b_members, player);
                break;
            case "joinspectators":
                if (arg0 != "") 
                {
                    Player target = Bukkit.getPlayerExact(arg0);
                    if (target == null) break;
                    else PL_GameManager.gm.AddMember(PL_GameManager.gm.spectator_members, target);
                } else PL_GameManager.gm.AddMember(PL_GameManager.gm.spectator_members, player);
                break;
            case "startmatch":
                PL_GameManager.gm.StartMatch();
                break;
            case "setmap":
                PL_GameManager.gm.SetMapName(arg0);
                break;
            case "openmenu":
                break;
            case "resetmap":
                PL_GameManager.gm.match.ResetMap();
                break;
            case "setgeneratorspeed":
                PL_Settings.settings.default_generator_speed = Integer.parseInt(arg0, 10);
                break;
            case "setredstonemultiplier":
                PL_Settings.settings.redstone_muliplier = Integer.parseInt(arg0, 10);
                break;
            case "setrespawntime":
                PL_Settings.settings.respawn_time = Integer.parseInt(arg0, 10);
                break;
            case "setcountdowntime":
                PL_Settings.settings.countdown_time = Integer.parseInt(arg0, 10);
                break;
            case "setspawninvincibilitytime":
                PL_Settings.settings.spawn_invincibility_time = Integer.parseInt(arg0, 10);
                break;
            case "setgeneratorspeedincrease":
                PL_Settings.settings.generator_speed_increase = Integer.parseInt(arg0, 10);
                break;
            case "debugmode":
                PL_GameManager.gm.match.status = PL_MatchStatus.DEBUG;
                break;
            case "disabledebug":
                PL_GameManager.gm.match.status = PL_MatchStatus.WAITING;
                break;
            case "endmatch":
                PL_GameManager.gm.EndMatch(null);
                break;
            case "tplobby":
                if (arg0 != "") 
                {
                    Player target = Bukkit.getPlayerExact(arg0);
                    if (target == null) break;
                    else target.teleport(PL_GameManager.gm.lobby.spawn.location);
                } else player.teleport(PL_GameManager.gm.lobby.spawn.location);
                break;
            case "tpfactory":
                if (arg0 != "") 
                {
                    Player target = Bukkit.getPlayerExact(arg0);
                    if (target == null) break;
                    else target.teleport((new PL_Location(-1092, -28, -185)).location);
                } else player.teleport((new PL_Location(-1092, -28, -185)).location);
                break;
            default:
                return false;
        }
        return true;
    }
}
