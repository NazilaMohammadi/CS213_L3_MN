import java.util.Random;
import java.util.Scanner;

public class Main
{
    enum MenuOptions
    {
        INVALID,
        BATTLE,
        RESET,
        QUIT
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        Creature creatureOne = new Creature();
        Creature creatureTwo = new Creature();
        MenuOptions menuChoice = MenuOptions.INVALID;

        do
        {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();

            menuChoice = switch (choice) {
                case 1 -> MenuOptions.BATTLE;
                case 2 -> MenuOptions.RESET;
                case 3 -> MenuOptions.QUIT;
                default -> MenuOptions.INVALID;
            };

            switch (menuChoice)
            {
                case BATTLE:
                    battleCreatures(
                            scanner,
                            rand,
                            creatureOne,
                            creatureTwo);
                    break;

                case RESET:
                    resetCreatures(creatureOne, creatureTwo);
                    break;

                case QUIT:
                    displayQuitMessage();
                    break;

                default:
                    displayInvalidChoice();
                    break;
            }
        }
        while (menuChoice != MenuOptions.QUIT);

        scanner.close();
    }

    public static void displayMenu()
    {
        System.out.println();
        System.out.println("Creature Battle Menu");
        System.out.println("1. Battle two creatures");
        System.out.println("2. Reset creatures");
        System.out.println("3. Quit");
        System.out.print("Enter your choice: ");
    }

    public static void battleCreatures(
            Scanner scanner,
            Random rand,
            Creature creatureOne,
            Creature creatureTwo)
    {
        setUpCreature(scanner, rand, creatureOne, 1);
        setUpCreature(scanner, rand, creatureTwo, 2);

        System.out.println();
        System.out.println("Creatures Before the Battle");
        displayCreatureTable(creatureOne, creatureTwo);

        Creature attacker;
        Creature defender;

        int firstAttacker = rand.nextInt(2);

        if (firstAttacker == 0)
        {
            attacker = creatureOne;
            defender = creatureTwo;
        }
        else
        {
            attacker = creatureTwo;
            defender = creatureOne;
        }

        System.out.println();
        System.out.println("Battle Details");

        System.out.println(String.format(
                "%-8s %-25s %10s %-25s %17s",
                "Round",
                "Attacker",
                "Damage",
                "Defender",
                "Defender Health"));

        System.out.println(String.format(
                "%-8s %-25s %10s %-25s %17s",
                "--------",
                "-------------------------",
                "----------",
                "-------------------------",
                "-----------------"));

        int round = 0;

        while (creatureOne.getHealth() > 0
                && creatureTwo.getHealth() > 0)
        {
            round++;

            int damage = attacker.getDamage();
            int newHealth = defender.getHealth() - damage;

            defender.setHealth(newHealth);

            System.out.println(String.format(
                    "%-8d %-25s %10d %-25s %17d",
                    round,
                    attacker.getNameAndType(),
                    damage,
                    defender.getNameAndType(),
                    defender.getHealth()));

            if (defender.getHealth() > 0)
            {
                Creature temporaryCreature = attacker;
                attacker = defender;
                defender = temporaryCreature;
            }
        }

        System.out.println();

        System.out.println(
                attacker.getNameAndType()
                        + " defeated "
                        + defender.getNameAndType()
                        + " in "
                        + round
                        + " rounds.");

        System.out.println();
        System.out.println("Creatures After the Battle");

        displayCreatureTable(creatureOne, creatureTwo);
    }

    public static void setUpCreature(
            Scanner scanner,
            Random rand,
            Creature creature,
            int creatureNumber)
    {
        System.out.println();

        System.out.print(
                "Enter creature "
                        + creatureNumber
                        + " name: ");

        String name = scanner.nextLine();

        System.out.print(
                "Enter creature "
                        + creatureNumber
                        + " type: ");

        String type = scanner.nextLine();

        int healthRange =
                Creature.MAX_HEALTH - Creature.MIN_HEALTH + 1;

        int health =
                rand.nextInt(healthRange) + Creature.MIN_HEALTH;

        int strengthRange =
                Creature.MAX_STRENGTH - Creature.MIN_STRENGTH + 1;

        int strength =
                rand.nextInt(strengthRange) + Creature.MIN_STRENGTH;

        creature.setCreature(
                name,
                type,
                health,
                strength);
    }

    public static void displayCreatureTable(
            Creature creatureOne,
            Creature creatureTwo)
    {
        System.out.println(String.format(
                "%-25s %10s %10s",
                "Creature",
                "Health",
                "Strength"));

        System.out.println(String.format(
                "%-25s %10s %10s",
                "-------------------------",
                "----------",
                "----------"));

        System.out.println(creatureOne.to_String());
        System.out.println(creatureTwo.to_String());
    }

    public static void resetCreatures(
            Creature creatureOne,
            Creature creatureTwo)
    {
        creatureOne.setCreature(
                Creature.DEFAULT_NAME,
                Creature.DEFAULT_TYPE,
                Creature.DEFAULT_HEALTH,
                Creature.DEFAULT_STRENGTH);

        creatureTwo.setCreature(
                Creature.DEFAULT_NAME,
                Creature.DEFAULT_TYPE,
                Creature.DEFAULT_HEALTH,
                Creature.DEFAULT_STRENGTH);

        System.out.println();

        System.out.println(
                "Both creatures were reset "
                        + "to their default values.");

        displayCreatureTable(creatureOne, creatureTwo);
    }

    public static void displayQuitMessage()
    {
        System.out.println();

        System.out.println(
                "Thank you for using Creature Battle.");
    }

    public static void displayInvalidChoice()
    {
        System.out.println();

        System.out.println(
                "Invalid menu choice. "
                        + "Please enter 1, 2, or 3.");
    }
}

class Creature
{
    public static final int MIN_HEALTH = 60;
    public static final int MAX_HEALTH = 180;

    public static final int MIN_STRENGTH = 50;
    public static final int MAX_STRENGTH = 190;

    public static final int DEFAULT_HEALTH = MIN_HEALTH;
    public static final int DEFAULT_STRENGTH = MIN_STRENGTH;

    public static final String DEFAULT_NAME = "n/a";
    public static final String DEFAULT_TYPE = "n/a";

    private static final Random RAND = new Random();

    private int strength = DEFAULT_STRENGTH;
    private int health = DEFAULT_HEALTH;
    private String name = DEFAULT_NAME;
    private String type = DEFAULT_TYPE;

    public Creature()
    {
        setCreature(
                DEFAULT_NAME,
                DEFAULT_TYPE,
                DEFAULT_HEALTH,
                DEFAULT_STRENGTH);
    }

    public Creature(
            String newName,
            String newType,
            int newHealth,
            int newStrength)
    {
        setCreature(
                newName,
                newType,
                newHealth,
                newStrength);
    }

    public void setCreature(
            String newName,
            String newType,
            int newHealth,
            int newStrength)
    {
        name = newName;
        type = newType;

        health = Math.max(newHealth, 0);

        strength = Math.max(newStrength, 0);
    }

    public void setHealth(int newHealth)
    {
        setCreature(
                name,
                type,
                newHealth,
                strength);
    }

    public int getHealth()
    {
        return health;
    }

    public int getStrength()
    {
        return strength;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public String getNameAndType()
    {
        return name + " the " + type ;
    }

    public int getDamage()
    {
        int damage = 0;

        if (strength > 0)
        {
            damage = RAND.nextInt(strength) + 1;
        }

        return damage;
    }

    public String to_String()
    {
        return String.format(
                "%-25s %10d %10d",
                getNameAndType(),
                health,
                strength);
    }
}

/*
Test Run 1: Invalid menu choice

Input:
9

Result:

Invalid menu choice. Please enter 1, 2, or 3.


Test Run 2: Reset

Input:
2

Result:
Both creatures were reset to their default values.
Creature                      Health   Strength
------------------------- ---------- ----------
n/a the n/a                       60         50
n/a the n/a                       60         50


Test Run 3: Battle

Input:
1

Creature 1:
Yalda
elf

Creature 2:
Ali
daemon

Result:
Creatures Before the Battle
Creature                      Health   Strength
------------------------- ---------- ----------
Yalda the elf                    118        101
Ali the daemon                    95         97

Battle Details
Round    Attacker                      Damage Defender                    Defender Health
-------- ------------------------- ---------- ------------------------- -----------------
1        Ali the daemon                    94 Yalda the elf                            24
2        Yalda the elf                     81 Ali the daemon                           14
3        Ali the daemon                    12 Yalda the elf                            12
4        Yalda the elf                     92 Ali the daemon                            0
Yalda the elf defeated Ali the daemon in 4 rounds.

Creatures After the Battle
Creature                      Health   Strength
------------------------- ---------- ----------
Yalda the elf                     12        101
Ali the daemon                     0         97


Test Run 4: Multiple menu selections

Creature Battle Menu
1. Battle two creatures
2. Reset creatures
3. Quit
Enter your choice: 1

Enter creature 1 name: Lina
Enter creature 1 type: elf

Enter creature 2 name: Jo
Enter creature 2 type: Demon

Creatures Before the Battle
Creature                      Health   Strength
------------------------- ---------- ----------
Lina the elf                     126        157
Jo the Demon                     161         85

Battle Details
Round    Attacker                      Damage Defender                    Defender Health
-------- ------------------------- ---------- ------------------------- -----------------
1        Jo the Demon                       1 Lina the elf                            125
2        Lina the elf                     137 Jo the Demon                             24
3        Jo the Demon                      42 Lina the elf                             83
4        Lina the elf                      65 Jo the Demon                              0

Lina the elf defeated Jo the Demon in 4 rounds.

Creatures After the Battle
Creature                      Health   Strength
------------------------- ---------- ----------
Lina the elf                      83        157
Jo the Demon                       0         85

Creature Battle Menu
1. Battle two creatures
2. Reset creatures
3. Quit
Enter your choice: 2

Both creatures were reset to their default values.
Creature                      Health   Strength
------------------------- ---------- ----------
n/a the n/a                       60         50
n/a the n/a                       60         50

Creature Battle Menu
1. Battle two creatures
2. Reset creatures
3. Quit
Enter your choice: 1

Enter creature 1 name: sara
Enter creature 1 type: dragon

Enter creature 2 name: mike
Enter creature 2 type: wizard

Creatures Before the Battle
Creature                      Health   Strength
------------------------- ---------- ----------
sara the dragon                  169        113
mike the wizard                  142        166

Battle Details
Round    Attacker                      Damage Defender                    Defender Health
-------- ------------------------- ---------- ------------------------- -----------------
1        sara the dragon                   64 mike the wizard                          78
2        mike the wizard                    1 sara the dragon                         168
3        sara the dragon                   46 mike the wizard                          32
4        mike the wizard                   10 sara the dragon                         158
5        sara the dragon                   42 mike the wizard                           0

sara the dragon defeated mike the wizard in 5 rounds.

Creatures After the Battle
Creature                      Health   Strength
------------------------- ---------- ----------
sara the dragon                  158        113
mike the wizard                    0        166

Creature Battle Menu
1. Battle two creatures
2. Reset creatures
3. Quit
Enter your choice: 3

Thank you for using Creature Battle.
*/