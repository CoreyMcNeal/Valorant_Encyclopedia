import Communicator.APICommunicator;

import java.util.Scanner;

public class ClientApp {

    private static final Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {
        UIStart();
    }

    //Starts the Interface
    private static void UIStart() {

        System.out.println("\nWelcome to the Valorant Info Client");
        System.out.println("Menu");
        System.out.println("----------");
        System.out.println("1. Characters");
        System.out.println("2. Weapons");
        System.out.println("2. Maps");
        System.out.println("3. Modes");
        System.out.print("\n:: ");

        String userChoice = myScanner.nextLine();

        //Info is still in raw dictionary/map format, need to print it neatly with a choice of characters.
        if (userChoice.equals("1")) {
            boolean connectStatus = false;
            while(!connectStatus) {
                connectStatus = APICommunicator.connectAgentInfo();
            }
        }

        //Need other else ifs for other categories from the menu







    }



}
