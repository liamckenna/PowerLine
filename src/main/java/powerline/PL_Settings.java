package powerline;

public class PL_Settings {
    public int default_generator_speed;
    public int redstone_muliplier;
    public int respawn_time;
    public int countdown_time;
    public int spawn_invincibility_time;
    public int generator_speed_increase;

    public PL_Settings()
    {
        default_generator_speed = 15;
        redstone_muliplier = 2;
        respawn_time = 5;
        countdown_time = 10;
        spawn_invincibility_time = 2;
        generator_speed_increase = 2;
    }

    public static PL_Settings settings;
}
