package Communicator;



import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class APICommunicator {

                                                                //Tests API connection, calls getAgentJSON if successful
    public static List<Map<String, String>> connectAgentInfo() {

        Scanner myScanner = new Scanner(System.in);

        try {
            URL myURL = new URL("https://valorant-api.com/v1/agents");
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();
            myConn.setRequestMethod("GET");
            myConn.connect();

            int responseCode = myConn.getResponseCode();
            if (responseCode == 200) {

                return getAgentJSON(myURL);

            } else {
                System.out.println("\nFailed to connect\n" +
                        "Push enter to retry connection");
                myScanner.nextLine();
                return null;

            }


        } catch (MalformedURLException e) {
            System.out.println("\nError with URL\n" +
                                "Push enter to retry connection");
            myScanner.nextLine();
            return null;

        } catch (IOException e) {
            System.out.println("\nError with HTTP conversion\n" +
                                "Push enter to retry connection");
            myScanner.nextLine();
            return null;
        }
    }




    public static boolean connectWeaponInfo() {

        try {
            URL myURL = new URL("https://valorant-api.com/v1/weapons");
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();
            myConn.setRequestMethod("GET");
            myConn.connect();

            int responseCode = myConn.getResponseCode();
            if (responseCode == 200) {
                getAgentJSON(myURL);
                return true;
            } else {
                System.out.println("Failed to connect");
                return false;
            }


        } catch (MalformedURLException e) {
            System.out.println("Error with URL");
            return false;
        } catch (IOException e) {
            System.out.println("Error with HTTP conversion");
            return false;
        }
    }


    public static boolean connectMapInfo() {

        try {
            URL myURL = new URL("https://valorant-api.com/v1/maps");
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();
            myConn.setRequestMethod("GET");
            myConn.connect();

            int responseCode = myConn.getResponseCode();
            if (responseCode == 200) {
                getAgentJSON(myURL);
                return true;
            } else {
                System.out.println("Failed to connect");
                return false;
            }


        } catch (MalformedURLException e) {
            System.out.println("Error with URL");
            return false;
        } catch (IOException e) {
            System.out.println("Error with HTTP conversion");
            return false;
        }
    }

    public static boolean pingModesInfo() {

        try {
            URL myURL = new URL("https://valorant-api.com/v1/gamemodes");
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();
            myConn.setRequestMethod("GET");
            myConn.connect();

            int responseCode = myConn.getResponseCode();
            if (responseCode == 200) {
//                getAgentJSON(myURL);
                return true;

            } else {
                System.out.println("Failed to connect");
                return false;

            }


        } catch (MalformedURLException e) {
            System.out.println("Error with URL");
            return false;

        } catch (IOException e) {
            System.out.println("Error with HTTP conversion");
            return false;

        }
    }






                                                                        // Grabs Valorant agent info from API
    private static List<Map<String, String>> getAgentJSON(URL myUrl) {

        List< Map<String,String>> allAgentInformation = new ArrayList<>();

        JSONObject temp = null;
        try {

            String content = "";
            Scanner myScanner = new Scanner(myUrl.openStream());

            //Write JSON data into string
            while(myScanner.hasNext()) {
                content += myScanner.nextLine();
            }

            myScanner.close();

            //Creates parser, and parses the content into a JSONOBject
            JSONParser myParser = new JSONParser();
            JSONObject myDataObject = (JSONObject) myParser.parse(content);

            //Keys into general data, returns array/list
            JSONArray myArrayObject = (JSONArray) myDataObject.get("data");

            //Loops through each agent entry in the array, pulls necessary information
            int posCounter = 0;
            while (true) {
                try {

                    //Keys into the first agent
                    JSONObject data = (JSONObject) myArrayObject.get(posCounter);

                    //Keys into the different entries of that agent
                    String name = data.get("displayName").toString();
                    String description = data.get("description").toString();

                    //For the edge case of Sova, there is an entry in the JSON that is empty at position 6, skips it
                    String role;
                    if (name.equals("Sova") && posCounter == 6) {
                        posCounter += 1;
                        continue;

                    } else {
                        JSONObject roleData = (JSONObject) data.get("role");
                        role = roleData.get("displayName").toString();
                    }

                    JSONArray abilities = (JSONArray) data.get("abilities");

                    JSONObject abilityInfo0 = (JSONObject) abilities.get(0);
                    String abilityName0 = abilityInfo0.get("displayName").toString();

                    JSONObject abilityInfo1 = (JSONObject) abilities.get(1);
                    String abilityName1 = abilityInfo1.get("displayName").toString();

                    JSONObject abilityInfo2 = (JSONObject) abilities.get(2);
                    String abilityName2 = abilityInfo2.get("displayName").toString();

                    JSONObject ultimate = (JSONObject) abilities.get(3);
                    String ultimateName = ultimate.get("displayName").toString();


                    //Create map/dictionary and adds this agent map to the bigger list of all characters
                    Map<String, String> agentInformation = new HashMap<>();
                    agentInformation.put("name", name);
                    agentInformation.put("role", role);

                    agentInformation.put("abilityName0", abilityName0);
                    agentInformation.put("abilityName1", abilityName1);
                    agentInformation.put("abilityName2", abilityName2);
                    agentInformation.put("ultimateName", ultimateName);


                    allAgentInformation.add(agentInformation);

                    posCounter += 1;

                } catch (Exception e) {
                    break;
                }
            }

            return allAgentInformation;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    private static void getWeaponJSON(URL myUrl) {

        JSONObject temp = null;
        try {
            String content = "";
            Scanner myScanner = new Scanner(myUrl.openStream());

            //Write JSON data into string
            while(myScanner.hasNext()) {
                content += myScanner.nextLine();
            }

            myScanner.close();

            //Creates parser, and parses the content into a JSONOBject
            JSONParser myParser = new JSONParser();
            JSONObject myDataObject = (JSONObject) myParser.parse(content);

            //Keys into general data, returns array/list
            JSONArray myArrayObject = (JSONArray) myDataObject.get("data");

            //Keys into the first agent
            JSONObject data = (JSONObject) myArrayObject.get(0);

            //Keys into the display name of that agent
            String name = data.get("description").toString();

            System.out.println(name);
            //Pick up here next time



        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

}
