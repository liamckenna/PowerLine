//PL_MapLoader.java
//Auth Liam McKenna, 2025

package powerline;

public class PL_MapLoader
{
    public static PL_Map LoadMap(String name) 
    {
        PL_Map map = new PL_Map(name.toLowerCase());
        if (name.toLowerCase() == "factory")
        {
            map.center = new PL_Location(-1092, -28, -185);
            map.corner_a = new PL_Location(-1010, -49, -126);
            map.corner_b = new PL_Location(-1174, 37, -225);

            map.team_a.spawn_1 = new PL_Location(-1024, -45, -182, 90f, 0f);
            map.team_a.spawn_2 = new PL_Location(-1026, -45, -187, 90f, 0f);

            map.team_b.spawn_1 = new PL_Location(-1160, -45, -182, 270f, 0f);
            map.team_b.spawn_2 = new PL_Location(-1158, -45, -187, 270f, 0f);

            map.spectator_spawn = new PL_Location(-1092, -11, -209);

            map.team_a.team_chest = new PL_Location(-1028, -45, -193);
            map.team_a.ender_chest = new PL_Location(-1026, -45, -193);

            map.team_b.team_chest = new PL_Location(-1156, -45, -193);
            map.team_b.ender_chest = new PL_Location(-1158, -45, -193);
            
            map.team_a.gen_1a.power_location = new PL_Location(-1026, -34, -196);
            map.team_a.gen_1a.spawner_location = new PL_Location(-1023.5f, -44, -189.5f);
            map.team_a.gen_1b.power_location = new PL_Location(-1044, -45, -190);
            map.team_a.gen_1b.spawner_location = new PL_Location(-1045.5f, -44, -189.5f);
            map.team_a.gen_2a.power_location = new PL_Location(-1043, -43, -159);
            map.team_a.gen_2a.spawner_location = new PL_Location(-1042.5f, -44, -164.5f);
            map.team_a.gen_2b.power_location = new PL_Location(-1054, -41,-211);
            map.team_a.gen_2b.spawner_location = new PL_Location(-1053.5f, -42, -203.5f);
            map.team_a.gen_3a.power_location = new PL_Location(-1067, -38, -154);
            map.team_a.gen_3a.spawner_location = new PL_Location(-1066.5f, -44, -165.5f);
            map.team_a.gen_3b.power_location = new PL_Location(-1071, -38, -195);
            map.team_a.gen_3b.spawner_location = new PL_Location(-1058.5f, -44, -194.5f);

            map.team_b.gen_1a.power_location = new PL_Location(-1158, -34, -196);
            map.team_b.gen_1a.spawner_location = new PL_Location(-1159.5f, -44, -189.5f);
            map.team_b.gen_1b.power_location = new PL_Location(-1138, -45, -182);
            map.team_b.gen_1b.spawner_location = new PL_Location(-1141.5f, -44, -178.5f);
            map.team_b.gen_2a.power_location = new PL_Location(-1140, -43, -159);
            map.team_b.gen_2a.spawner_location = new PL_Location(-1139.5f, -44, -165.5f);
            map.team_b.gen_2b.power_location = new PL_Location(-1130, -41, -211);
            map.team_b.gen_2b.spawner_location = new PL_Location(-1129.5f, -42, -204.5f);
            map.team_b.gen_3a.power_location = new PL_Location(-1116, -38, -154);
            map.team_b.gen_3a.spawner_location = new PL_Location(-1115.5f, -44, -165.5f);
            map.team_b.gen_3b.power_location = new PL_Location(-1113, -38, -195);
            map.team_b.gen_3b.spawner_location = new PL_Location(-1124.5f, -44, -194.5f);

            map.tower.location_a = new PL_Location(-1088, -46, -157);
            map.tower.location_b = new PL_Location(-1096, -46, -157);
            map.tower.redstone_center = new PL_Location(-1092, -46, -157);

            map.redstone_gen.spawners.add(new PL_Location(-1100.5f, -44, -184.5f));
            map.redstone_gen.spawners.add(new PL_Location(-1091.5f, -44, -175.5f));
            map.redstone_gen.spawners.add(new PL_Location(-1082.5f, -44, -184.5f));
            map.redstone_gen.spawners.add(new PL_Location(-1091.5f, -44, -193.5f));

            map.easter_egg_chest = new PL_Location(-1092, -19, -184);

            map.void_level = -55;
        }
        return map;
    }
}
