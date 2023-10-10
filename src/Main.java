import java.util.Random;

public class Main {
    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {250, 260, 270, 220, 320, 230, 280, 265};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 12, 0, 25};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic,", "Healer", "Air", " Dodger", "Witcher", " Thor"};
    public static int roundNumber = 0;

        public static void medicHeal() {
            for (int i = 0; i < heroesHealth.length - 1; i++) {
                if (heroesAttackType[i].equals("Healer") && heroesHealth[i] < 100 && heroesHealth[i] > 0 ) {
                    if ( i!= 3) {
                        Random random = new Random();
                        int healAmount = random.nextInt(51) + 50;
                        heroesHealth[i] += healAmount;
                        System.out.println("Medic healed " + heroesAttackType[i] + " for " + healAmount + " health points.");
                        break;
                    }
                }
            }
        }




        public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                int damage = bossDamage;
                if (heroesAttackType[i].equals("Dodger")) {
                    Random random = new Random();
                    int dodgeChance = random.nextInt(2); // 50/50 шанс уклонения
                    if (dodgeChance == 0) {
                        System.out.println("Lucky dodged the boss's attack!");
                        continue;
                    }
                }
                if (heroesAttackType[i].equals("Thor")) {
                    Random random = new Random();
                    boolean isThorSpecial = random.nextBoolean();
                    if (isThorSpecial) {
                        System.out.println("Thor stunned the boss! The boss skips this round.");
                        continue;
                    }
                }
                    if (heroesAttackType[i].equals("Witcher")) {
                        Random random = new Random();
                        int reviveChance = random.nextInt(2);
                        if (reviveChance == 0) {
                            for (int j = 0; j < heroesHealth.length; j++) {
                                if (heroesHealth[j] == 0) {
                                    heroesHealth[j] = 1; // Оживляем первого погибшего героя
                                    System.out.println("Witcher sacrificed himself to revive a fallen hero!");
                                    break;
                                }
                            }
                        }


                }
                if (heroesAttackType[i].equals("Air")) {
                    damage = bossDamage / 5;
                }
                if (heroesHealth[i] - damage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= damage;
//                if (heroesHealth[i] - bossDamage < 0) {
//                    heroesHealth[i] = 0;
//                } else {
//                    heroesHealth[i] -= bossDamage; // heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (heroesAttackType[i].equals("Healer") && i != 3) {
                    medicHeal();
                    damage = 0;
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage; // bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
        if (heroesHealth[4] > 0) {
            System.out.println("Golem takes 1/5 damage for other players.");
        }
        if (heroesHealth[5] > 0) {
            System.out.println("Lucky has a chance to dodge the boss's attacks.");
        }
        if (heroesHealth[6] > 0) {
            System.out.println("Witcher can sacrifice himself to revive a fallen hero.");
        }
        if (heroesHealth[7] > 0) {
            System.out.println("Thor has a chance to stun the boss and skip the boss's attack.");
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length - 1; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}
