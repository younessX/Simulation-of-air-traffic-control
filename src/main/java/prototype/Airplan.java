package prototype;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Airplan extends Thread{

    public enum typeAirplan{
        RETITE,
        MOYENNE,
        GRANDE
    }

    private typeAirplan type;
    private int consomation;
    private int capactéCarburant;
    private int vitesse = 500;
    private String depart;
    private String destination;
    private  ArrayList<Integer> landingTimes = new ArrayList();
    private  ArrayList<Integer> airportTimes = new ArrayList();


    public Airplan(String depart, String destination, typeAirplan type) {
     this.depart = depart;
     this.destination = destination;
     this.type = type;
     switch (type){
         case RETITE:
             consomation = 100;//100L in 500 km
             capactéCarburant = 1000;//(10 UT)
             break;

         case MOYENNE:
             consomation = 150;
             capactéCarburant = 2100;//(14 UT)
             break;

         case GRANDE:
             consomation = 200;//(16 UT)
             capactéCarburant = 3200;
             break;
     }
    }

    @Override
    public void run() {
        final boolean[] start = {true};
        Dijkstra<String> dijkstra = new Dijkstra<>();
        DataDemo dataDemo = DataDemo.getInstance();
        dijkstra.calculateShortestPath(dataDemo.getNodes().get(depart));
        final double[] dist = {0};
        String path = "";
        JSONObject jsonObject = new JSONObject();
        JSONArray airports = new JSONArray();


        for(Node node : dataDemo.getNodes().get(destination).getShortestPath()){
            airports.add(getPositionJson(node.getName(), dataDemo.getPositions().get(node.getName())));
            path += node.getName()+"-->";
        }

        path += destination;
        airports.add(getPositionJson(destination, dataDemo.getPositions().get(destination)));
        jsonObject.put("airports", airports);

        //vol info
        System.out.println(getName()+" le vol de "+depart+" au "+destination);

        //PCC
        System.out.println("le PCC de "+getName()+" est: "+path);

        //getShortestPath
        dataDemo.getNodes().get(destination)
                .getShortestPath()
                .forEach(place->{
                    if(start[0]){
                        //info about depart
                        System.out.println(getName()+" le vol est commancé appartir de: "+place.getName());
                        start[0] = false;
                    }else{
                        //get time of journey
                        float time = (float) ((place.getDistance()- dist[0])*100/vitesse);
                        sleepThread((long) time);

                        //get distance
                        dist[0] = place.getDistance();
                        System.out.println(getName()+" est arrivé au: "+place.getName()+" distance parcourie: "+place.getDistance()*100+" Km");

                        //airport
                        Airport airport = DataDemo.getAirports().get(place.getName());

                        outToPiste(airport);
                        airportToOut(airport);
                    }
        });


        float time = (float) (dataDemo.getNodes().get(destination).getDistance()-dist[0])*100/vitesse;

        sleepThread((long) time);
        System.out.println(getName()+" l'avion est arrivé au: "+destination+" distance parcourie: "+dataDemo.getNodes()
                .get(destination).getDistance()*100+" Km");

        //set in destination
        outToPiste(DataDemo.getAirports().get(destination));

        jsonObject.put("landingTimes", getJsonArray(landingTimes));
        jsonObject.put("AirportTimes", getJsonArray(airportTimes));


        try {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader("C:/javaEE/eclipse-workspace/Simulator/src/main/webapp/json/output.json"));
            JSONArray jsonArray = (JSONArray)obj;
            jsonArray.add(jsonObject);
            FileWriter file = new FileWriter ("C:/javaEE/eclipse-workspace/Simulator/src/main/webapp/json/output.json");
            file.write(jsonArray.toJSONString ());
            file.close();
        }
        catch (Exception e){
            e.printStackTrace ();
        }

    }


    private void sleepThread(long millis){
        try {
            sleep(10 * millis);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void outToPiste(Airport airport){
        int landingTime = 0, temp;
        while (!airport.isPisteDisponible()){
            temp = airport.utiliserPiste(getName(), Airport.OUT_AIRPORT);
            landingTime += temp;
            sleepThread(temp);
        }
        temp = airport.utiliserPiste(getName(), Airport.OUT_AIRPORT);
        landingTime+= temp;
        landingTimes.add(landingTime);

    sleepThread(temp);}

    private void airportToOut(Airport airport){
        //piste-->station
        int airportTime = 0, temp;
        while(!airport.isStationDisponible()){
            temp = airport.utiliserStation(getName());
            airportTime += temp;
            sleepThread(temp);
        }
        temp = airport.utiliserStation(getName());
        airportTime += temp;

    sleepThread(temp);

    //piste-->out
        while (!airport.isPisteDisponible()){
            temp = airport.utiliserPiste(getName(), Airport.IN_AIRPORT);
            airportTime+=temp;
            sleepThread(temp);
        }

        temp = airport.utiliserPiste(getName(), Airport.IN_AIRPORT);
        airportTime += temp;
        airportTimes.add(airportTime);
        sleepThread(temp);
        airport.quiterPiste(getName());
    }


    private JSONObject getPositionJson(String name, Integer[] positions){
        JSONObject parent = new JSONObject();
        JSONArray childArray = new JSONArray();
        childArray.addAll(Arrays.asList(positions));
        parent.put("name", name);
        parent.put("position", childArray);
        return parent;
    }

    private JSONArray getJsonArray(Collection c){
      JSONArray jsonArray = new JSONArray();
      jsonArray.addAll(c);
    return jsonArray;}

}
