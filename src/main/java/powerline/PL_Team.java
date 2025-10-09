//PL_Team.java
//Auth Liam McKenna, 2025

package powerline;

import java.util.Vector;

public class PL_Team
{
    String name;
    PL_Location spawn_1;
    PL_Location spawn_2;
    PL_Location team_chest;
    PL_Location ender_chest;
    PL_TeamStatus status;
    Vector<PL_Player> members;
    Vector<PL_Generator> generators;
    PL_Generator gen_1a;
    PL_Generator gen_1b;
    PL_Generator gen_2a;
    PL_Generator gen_2b;
    PL_Generator gen_3a;
    PL_Generator gen_3b;

    PL_Team(String name) 
    {
        this.name = name;
        status = PL_TeamStatus.NEUTRAL;
        members = new Vector<>();
        gen_1a = new PL_Generator(1, "1-A", this);
        gen_1b = new PL_Generator(1, "1-B", this);
        gen_2a = new PL_Generator(2, "2-A", this);
        gen_2b = new PL_Generator(2, "2-B", this);
        gen_3a = new PL_Generator(3, "3-A", this);
        gen_3b = new PL_Generator(3, "3-B", this);
        generators = new Vector<>();
        generators.add(gen_1a);
        generators.add(gen_1b);
        generators.add(gen_2a);
        generators.add(gen_2b);
        generators.add(gen_3a);
        generators.add(gen_3b);
    }

    public int GeneratorsReached()
    {
        int generators_reached = 1;
        for (PL_Generator generator : generators) if (generator.resource != null) generators_reached++;
        return generators_reached;
    }
}
