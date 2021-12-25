import Communicator.APICommunicator;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        APICommunicator apiCommunicator = new APICommunicator(myScanner);

        UIStart(myScanner, apiCommunicator);
    }

    //Starts the Interface
    private static void UIStart(Scanner myScanner, APICommunicator myCommunicator) {

        System.out.println("\nWelcome to the Valorant Info Client");

        boolean leaveProgram = false;
        while (!leaveProgram) {
            System.out.println("\nMenu");
            System.out.println("----------");
            System.out.println("1. Characters");
            System.out.println("2. Weapons");
            System.out.println("3. Maps");
            System.out.println("4. Modes");
            System.out.println("5. Exit ->");
            System.out.print("\n:: ");

            String userChoice = myScanner.nextLine();

            switch (userChoice) {

                case "1":                 //Characters
                    CharactersMenu(myScanner, myCommunicator);
                    continue;

                case "2": // Weapons
                    WeaponMenu(myScanner, myCommunicator);
                    continue;

                case "3": // Maps, start here next
                    MapsMenu(myScanner, myCommunicator);
                    continue;

                case "4": // Modes

                    continue;

                case "5": // Exits the program
                    leaveProgram = true;

            }

        }



    }

                                            //Retrieves List<Map<>> and handles the character selection process
    private static void CharactersMenu(Scanner myScanner, APICommunicator myCommunicator) {

        List<Map<String,String>> allAgentsInformation = null;
        while (allAgentsInformation == null) {
            allAgentsInformation = myCommunicator.pingAgentInfo();
        }

        while (true) {

            System.out.println("\nWhich character would you like to learn more about (Enter -1 to exit): ");
            for(int i = 0; i < allAgentsInformation.size(); i++) {
                System.out.print(i + " - ");
                System.out.println(allAgentsInformation.get(i).get("name"));
            }

            int userChoice;
            while (true) {
                try {
                    System.out.print(":: ");
                    userChoice = Integer.parseInt(myScanner.nextLine());
                    if (userChoice < -1 || userChoice > (allAgentsInformation.size() - 1)) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid number of one of the characters.");

                }
            }

            if (userChoice == -1) {break;}

            printAgentInformation(allAgentsInformation, userChoice);

            myScanner.nextLine();
        }







    }

                                            //Retrieves List<Map<>> and handles the weapon selection process
    private static void WeaponMenu(Scanner myScanner,APICommunicator myCommunicator) {

        List<Map<String,String>> allWeaponsInformation = null;
        while (allWeaponsInformation == null) {
            allWeaponsInformation = myCommunicator.pingWeaponInfo();

        }

        while (true) {

            System.out.println("\nWhich weapon would you like to learn more about (Enter -1 to exit): ");
            for(int i = 0; i < allWeaponsInformation.size(); i++) {
                System.out.print(i + " - ");
                System.out.println(allWeaponsInformation.get(i).get("name"));
            }

            int userChoice;
            while (true) {
                try {
                    System.out.print(":: ");
                    userChoice = Integer.parseInt(myScanner.nextLine());
                    if (userChoice < -1 || userChoice > (allWeaponsInformation.size() - 1)) {
                        throw new Exception();
                    }
                    break;

                } catch (Exception e) {
                    System.out.println("Please enter a valid number of one of the weapons.");

                }
            }

            if (userChoice == -1) {break;}

            printWeaponInformation(allWeaponsInformation, userChoice);
            myScanner.nextLine();
        }
    }

                                            //Retrieves List<String> and handles the Map display process
    private static void MapsMenu(Scanner myScanner, APICommunicator myCommunicator) {

        List<String> allMapsInformation = null;
        while (allMapsInformation == null) {
            allMapsInformation = myCommunicator.pingMapInfo();

        }

        printMapInformation(allMapsInformation);
        myScanner.nextLine();

    }



                                                                //prints relevant agents information
    private static void printAgentInformation (List< Map<String, String>> allAgentsInformation, int userChoice) {

        System.out.println("\nName: " + allAgentsInformation.get(userChoice).get("name"));
        System.out.println("Role: " + allAgentsInformation.get(userChoice).get("role"));

        System.out.println("Ability 1: " + allAgentsInformation.get(userChoice).get("abilityName0"));
        System.out.println("Ability 2: " + allAgentsInformation.get(userChoice).get("abilityName1"));
        System.out.println("Ability 3: " + allAgentsInformation.get(userChoice).get("abilityName2"));
        System.out.println("Ultimate: " + allAgentsInformation.get(userChoice).get("ultimateName"));

        System.out.print("\n:: Push enter to return");

    }

                                                                    //prints relevant weapons information
    private static void printWeaponInformation (List<Map<String, String>> allWeaponInformation, int userChoice) {

        System.out.println("\nWeapon Name: " + allWeaponInformation.get(userChoice).get("name"));
        System.out.println("Fire Rate: " + allWeaponInformation.get(userChoice).get("fireRate"));
        System.out.println("Magazine Size: " + allWeaponInformation.get(userChoice).get("magazineSize"));
        System.out.println("Reload Time: " + allWeaponInformation.get(userChoice).get("reloadTimeSeconds") + " seconds");
        System.out.println("Equip Time: " + allWeaponInformation.get(userChoice).get("equipTimeSeconds") + " seconds");
        System.out.println("Cost - " + allWeaponInformation.get(userChoice).get("cost") + " credits");

        System.out.print("\n:: Push enter to return ");

    }

                                                                    //prints map information
    private static void printMapInformation (List<String> allMapInformation) {
        System.out.println("\n");

        int i = 0;
        while (i < allMapInformation.size()) {
            System.out.println("Map: " + allMapInformation.get(i));
            i++;
        }

        System.out.print("\n:: Push enter to return");
    }
}
