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

    private final Scanner myScanner;

    public APICommunicator(Scanner passedScanner) {
        this.myScanner = passedScanner;
    }

                                                                //Tests API connection, calls getAgentJSON if successful
    public List<Map<String, String>> pingAgentInfo() {

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
                this.myScanner.nextLine();
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

                                                            //Tests API connection, calls getWeaponJSON if successful
    public List<Map<String, String>> pingWeaponInfo() {

        try {
            URL myURL = new URL("https://valorant-api.com/v1/weapons");
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();
            myConn.setRequestMethod("GET");
            myConn.connect();

            int responseCode = myConn.getResponseCode();
            if (responseCode == 200) {

                return getWeaponJSON(myURL);

            } else {
                System.out.println("\nFailed to connect\n" +
                        "Push enter to retry connection");
                this.myScanner.nextLine();
                return null;
            }


        } catch (MalformedURLException e) {
            System.out.println("Error with URL");
            return null;

        } catch (IOException e) {
            System.out.println("Error with HTTP conversion");
            return null;

        }
    }


    public List<String> pingMapInfo() {

        try {
            URL myURL = new URL("https://valorant-api.com/v1/maps");
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();
            myConn.setRequestMethod("GET");
            myConn.connect();

            int responseCode = myConn.getResponseCode();
            if (responseCode == 200) {

                return getMapJSON(myURL);

            } else {
                System.out.println("\nFailed to connect\n" +
                        "Push enter to retry connection");
                this.myScanner.nextLine();
                return null;

            }


        } catch (MalformedURLException e) {
            System.out.println("Error with URL");
            return null;
        } catch (IOException e) {
            System.out.println("Error with HTTP conversion");
            return null;
        }
    }

    public List<String> pingGamemodesInfo() {

        try {
            URL myURL = new URL("https://valorant-api.com/v1/gamemodes");
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();
            myConn.setRequestMethod("GET");
            myConn.connect();

            int responseCode = myConn.getResponseCode();
            if (responseCode == 200) {
                return getGamemodesJSON(myURL);

            } else {
                System.out.println("\nFailed to connect\n" +
                        "Push enter to retry connection");
                this.myScanner.nextLine();

                return null;

            }


        } catch (MalformedURLException e) {
            System.out.println("Error with URL");
            return null;

        } catch (IOException e) {
            System.out.println("Error with HTTP conversion");
            return null;

        }
    }






                                                                        // Grabs Valorant agent info from API
    private List<Map<String, String>> getAgentJSON(URL myUrl) {

        List< Map<String,String>> allAgentInformation = new ArrayList<>();

        try {

            StringBuilder content = new StringBuilder();
            Scanner myScanner = new Scanner(myUrl.openStream());

            //Write JSON data into string
            while(myScanner.hasNext()) {
                content.append(myScanner.nextLine());
            }

            myScanner.close();

            //Creates parser, and parses the content into a JSONOBject
            JSONParser myParser = new JSONParser();
            JSONObject myDataObject = (JSONObject) myParser.parse(content.toString());

            //Keys into general data, returns array/list
            JSONArray myArrayObject = (JSONArray) myDataObject.get("data");

            //Loops through each agent entry in the array, pulls necessary information
            int posCounter = 0;
            while (true) {
                try {

                    //Keys into the agent
                    JSONObject data = (JSONObject) myArrayObject.get(posCounter);

                    String name = data.get("displayName").toString();

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

                                                                        // Grabs Valorant Weapon info from API
    private List<Map<String, String>> getWeaponJSON(URL myUrl) {

        List< Map<String, String>> allWeaponsList = new ArrayList<>();

        try {

            StringBuilder content = new StringBuilder();
            Scanner urlScanner = new Scanner(myUrl.openStream());

            //Write JSON data into string
            while(urlScanner.hasNext()) {
                content.append(urlScanner.nextLine());
            }
            urlScanner.close();

            //Creates parser, and parses the content into a JSONOBject
            JSONParser myParser = new JSONParser();
            JSONObject myDataObject = (JSONObject) myParser.parse(content.toString());

            //Keys into general data, returns array/list
            JSONArray myArrayObject = (JSONArray) myDataObject.get("data");

            //Loops through each agent entry in the array, pulls necessary information
            int posCounter = 0;
            while (true) {
                try {

                    //Keys into the agent
                    JSONObject data = (JSONObject) myArrayObject.get(posCounter);
                    String name = data.get("displayName").toString();

                    JSONObject weaponStats = (JSONObject) data.get("weaponStats");
                    String fireRate = weaponStats.get("fireRate").toString();
                    String magazineSize = weaponStats.get("magazineSize").toString();
                    String reloadTimeSeconds = weaponStats.get("reloadTimeSeconds").toString();
                    String equipTimeSeconds = weaponStats.get("equipTimeSeconds").toString();

                    JSONObject shopData = (JSONObject) data.get("shopData");
                    String cost = shopData.get("cost").toString();

                    //Adding name to the individual map here, need to add map to the big list of weapons. Gather other
                    //info like damage
                    Map<String, String> weaponEntry = new HashMap<>();
                    weaponEntry.put("name", name);
                    weaponEntry.put("fireRate", fireRate);
                    weaponEntry.put("magazineSize", magazineSize);
                    weaponEntry.put("reloadTimeSeconds", reloadTimeSeconds);
                    weaponEntry.put("equipTimeSeconds", equipTimeSeconds);
                    weaponEntry.put("cost", cost);


                    allWeaponsList.add(weaponEntry);
                    posCounter ++;

                } catch (Exception e) {
                    break;
                }
            }


            return allWeaponsList;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

                                                                        // Grabs Valorant Map info from API
    private List<String> getMapJSON (URL myURL) {

        List<String> allMapList = new ArrayList<>();

        try {

            StringBuilder content = new StringBuilder();
            Scanner urlScanner = new Scanner(myURL.openStream());

            while (urlScanner.hasNext()) {
                content.append(urlScanner.nextLine());
            }
            urlScanner.close();

            JSONParser myParser = new JSONParser();
            JSONObject myDataObject = (JSONObject) myParser.parse(content.toString());

            JSONArray myArrayObject = (JSONArray) myDataObject.get("data");

            int posCounter = 0;
            while (true) {

                try {

                    JSONObject data =  (JSONObject) myArrayObject.get(posCounter);
                    String mapName = data.get("displayName").toString();

                    allMapList.add(mapName);
                    posCounter++;
                } catch(Exception e) {
                    break;
                }

            }

            return allMapList;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<String> getGamemodesJSON (URL myURL) {

        List <String> allGamemodesList = new ArrayList<>();

        try {

            StringBuilder content = new StringBuilder();
            Scanner urlScanner = new Scanner(myURL.openStream());

            while (urlScanner.hasNext()) {
                content.append(urlScanner.nextLine());
            }
            urlScanner.close();

            JSONParser myParser = new JSONParser();
            JSONObject myDataObject = (JSONObject) myParser.parse(content.toString());

            JSONArray myArrayObject = (JSONArray) myDataObject.get("data");

            int posCounter = 0;
            while (true) {

                try {

                    JSONObject data =  (JSONObject) myArrayObject.get(posCounter);
                    String mapName = data.get("displayName").toString();

                    allGamemodesList.add(mapName);
                    posCounter++;

                } catch(Exception e) {
                    break;
                }

            }

            return allGamemodesList;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
