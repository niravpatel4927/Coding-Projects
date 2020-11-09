package TextGame;

import java.util.*;

public class TextGame {
	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		
		// Game variables
		String[] enemies = {"Skeleton", "Zombie", "Warrior", "Assassin"};
		int maxEnemyHealth = 100;
		int enemyAD = 25; 
		
		// Player variables
		int health = 100;
		int playerAD = 50;
		int numHealthPots = 3;
		int potionHeal = 40;
		int healthPotionDropChance = 30; // percentage
		
		boolean run = true;
		
		
		System.out.println("Welcome to the Dungeon!\n");
		
		GAME:
		while(run) {
			System.out.println("--------------------------------------\n");
			
			int enemyHealth = rand.nextInt(maxEnemyHealth);
			String enemy = enemies[rand.nextInt(enemies.length)];
			System.out.println("\t# " + enemy + " has appeared! #\n"); // # Enemy has appeared! #
			
			while (enemyHealth > 0) {
				System.out.println("\tYour HP: " + health);
				System.out.println("\tEnemy's HP: " + enemyHealth);
				System.out.println("\n\tWhat would you like to do?");
				System.out.println("\t1) Attack");
				System.out.println("\t2) Use health potion");
				System.out.println("\t3) RUN AWAY");
				
				int input = scan.nextInt(); 
				if (input == 1) {
					int damageDealt = rand.nextInt(playerAD);
					int damageTaken = rand.nextInt(enemyAD);
					
					enemyHealth -= damageDealt;
					health -= damageTaken;
					
					System.out.println("\t> You strike the " + enemy + " for " + damageDealt + " damage.");
					System.out.println("\t> You take " + damageTaken + "damage in retaliation");
					
					if (health < 1) {
						System.out.println("\t> You have taken too much damage, you are now dead");
						break;
					}
					
				} else if (input == 2) {
					if (numHealthPots != 0) {
						health += potionHeal;
						numHealthPots--;
						System.out.println("\t> You drink a health potion, healing yourself for " + potionHeal + "."
											+ "\n\t> You now have " + health + "health."
											+ "\n\t> You have " + numHealthPots + " health potions left.\n");
					} else {
						System.out.println("\t> You have no health potions left. Defeat enemies for a chance to gain a health potion");
					}
				} else if (input == 3) {
					System.out.println("\t You run away from the " + enemy + "!");
					continue GAME;
				} else {
					System.out.println("\tInvalid command!");
				}	
			}
			
			if (health < 1) {
				System.out.println("You have died. Your body will remain in the dungeon... forever........");
				break;
			}
			
			System.out.println("--------------------------------------\n");
			System.out.println(" # " + enemy + " was defeated! # ");
			System.out.println(" # You have " + health + " HP left. #");
			if (rand.nextInt(100) < healthPotionDropChance) {
				numHealthPots++;
				System.out.println(" # The " + enemy + " dropped a health potion! # ");
				System.out.println(" # You now have " + numHealthPots + " health potions. #");
			}
			System.out.println("--------------------------------------\n");
			System.out.println("What would you like to do now?");
			System.out.println("1. Continue fighting");
			System.out.println("2. Exit dungeon");
			
			int input = scan.nextInt();
			while(input != 1 && input != 2) {
				System.out.println("Invalid command.");
				input = scan.nextInt();
			}
			
			if (input == 1) {
				System.out.println("You continue on your adventure.");
			} else if (input == 2) {
				System.out.println("You exit the dungeon, basking in your glory.");
				break;
			}
			
		}
		
		System.out.println("\n######################");
		System.out.println("# Thanks for playing #");
		System.out.println("######################");
	}
}
