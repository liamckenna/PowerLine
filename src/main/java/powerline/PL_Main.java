//PL_Main.java
//Auth Liam McKenna, 2025

package powerline;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

public class PL_Main extends JavaPlugin
{    
    public static Plugin main_plugin;
    public PL_CommandManager command_manager;
    public PL_TimerManager timer_manager;
    public PL_Void void_manager;

    @Override
    public void onEnable() 
    {
        getLogger().info("PowerLine plugin enabled!");
        main_plugin = this;
        PL_GameManager.gm = new PL_GameManager(this);
        PL_GameManager.gm.tutorial_sequence = new PL_TutorialSequence(this);
        PL_ScoreboardManager.PostLobbyScoreboard();
        PL_LobbyManager.InitializePostMatchLeaderboard();
        PL_Settings.settings = new PL_Settings();
        
        GenerateCommands();
        getServer().getPluginManager().registerEvents(PL_GameManager.gm, this);
        getServer().getPluginManager().registerEvents(new PL_FreezeManager(), this);
        getServer().getPluginManager().registerEvents(new PL_LobbyManager(), this);
        getServer().getPluginManager().registerEvents(new PL_DeathManager(this), this);
        getServer().getPluginManager().registerEvents(new PL_SpecialItems(this), this);
        getServer().getPluginManager().registerEvents(new PL_Match(this), this);
        getServer().getPluginManager().registerEvents(new PL_RedstoneDurability(this), this);
        getServer().getPluginManager().registerEvents(new PL_PowerManager(), this);
        getServer().getPluginManager().registerEvents(new PL_RedstoneManager(), this);
        getServer().getPluginManager().registerEvents(new PL_MenuManager(), this);
        getServer().getPluginManager().registerEvents(PL_GameManager.gm.tutorial_sequence, this);
        void_manager = new PL_Void(this);
        getServer().getPluginManager().registerEvents(void_manager, this);
        void_manager.VoidCheck();
        timer_manager = new PL_TimerManager(this);
        getServer().getPluginManager().registerEvents(timer_manager, this);
        timer_manager.StartTimer();
    }

    public void GenerateCommands()
    {
        command_manager = new PL_CommandManager();
        this.getCommand("openmenu").setExecutor(command_manager);
        this.getCommand("jointeama").setExecutor(command_manager);
        this.getCommand("jointeamb").setExecutor(command_manager);
        this.getCommand("joinspectators").setExecutor(command_manager);
        this.getCommand("setmap").setExecutor(command_manager);
        this.getCommand("startmatch").setExecutor(command_manager);
        this.getCommand("resetmap").setExecutor(command_manager);
        this.getCommand("setgeneratorspeed").setExecutor(command_manager);
        this.getCommand("setredstonemultiplier").setExecutor(command_manager);
        this.getCommand("setrespawntime").setExecutor(command_manager);
        this.getCommand("setcountdowntime").setExecutor(command_manager);
        this.getCommand("setspawninvincibilitytime").setExecutor(command_manager);
        this.getCommand("setgeneratorspeedincrease").setExecutor(command_manager);
        this.getCommand("debugmode").setExecutor(command_manager);
        this.getCommand("disabledebug").setExecutor(command_manager);
        this.getCommand("endmatch").setExecutor(command_manager);
        this.getCommand("tplobby").setExecutor(command_manager);
        this.getCommand("tpfactory").setExecutor(command_manager);
    }
}
