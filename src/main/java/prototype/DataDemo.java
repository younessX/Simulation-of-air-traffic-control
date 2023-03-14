package prototype;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class DataDemo {
    private HashMap<String, Node> nodes;
    private static HashMap<String, Integer[]> positions;
    private  static HashMap<String, Airport> airports;

    private DataDemo(){
        positions= new HashMap<>();
        positions.put("Brasilia", new Integer[]{273, 370});
        positions.put("La cap", new Integer[]{395, 412});
        positions.put("Washington", new Integer[]{207, 259});
        positions.put("Rabat", new Integer[]{345, 278});
        positions.put("Caire", new Integer[]{413, 287});
        positions.put("Niamey", new Integer[]{375, 315});
        positions.put("Kinshasa", new Integer[]{400, 355});
        positions.put("Madrid", new Integer[]{353, 257});
        positions.put("Mexico", new Integer[]{160, 300});
        positions.put("Caracas", new Integer[]{227, 330});
        positions.put("Bogota", new Integer[]{211, 344});
        positions.put("Lina", new Integer[]{208, 365});
        positions.put("Buenos Aires", new Integer[]{240, 422});

        //nodes
        Node brasilia = new Node("Brasilia");
        Node laCap = new Node("La cap");
        Node washington = new Node("Washington");
        Node rabat = new Node("Rabat");
        Node caire = new Node("Caire");
        Node niamey = new Node("Niamey");
        Node kinshasa = new Node("Kinshasa");
        Node madrid = new Node("Madrid");
        Node mexico = new Node("Mexico");
        Node caracas = new Node("Caracas");
        Node bogota = new Node("Bogota");
        Node lina = new Node("Lina");
        Node buenosAires = new Node("Buenos Aires");


        //brasilia
        brasilia.addAdjacentNode(lina, getDistance(positions.get("Brasilia"), positions.get("La cap")));
        brasilia.addAdjacentNode(kinshasa, getDistance(positions.get("Brasilia"), positions.get("Kinshasa")));
        brasilia.addAdjacentNode(niamey, getDistance(positions.get("Brasilia"), positions.get("Niamey")));
        brasilia.addAdjacentNode(rabat, getDistance(positions.get("Brasilia"), positions.get("Rabat")));
        brasilia.addAdjacentNode(caracas, getDistance(positions.get("Brasilia"), positions.get("Caracas")));
        brasilia.addAdjacentNode(bogota, getDistance(positions.get("Brasilia"), positions.get("Bogota")));
        brasilia.addAdjacentNode(lina, getDistance(positions.get("Brasilia"), positions.get("Lina")));
        brasilia.addAdjacentNode(buenosAires, getDistance(positions.get("Brasilia"), positions.get("Buenos Aires")));

        //la cap
        laCap.addAdjacentNode(kinshasa, getDistance(positions.get("La cap"), positions.get("Kinshasa")));
        laCap.addAdjacentNode(brasilia, getDistance(positions.get("La cap"), positions.get("Brasilia")));
        laCap.addAdjacentNode(buenosAires, getDistance(positions.get("La cap"), positions.get("Buenos Aires")));

        //washington
        washington.addAdjacentNode(mexico, getDistance(positions.get("Washington"), positions.get("Mexico")));
        washington.addAdjacentNode(caracas, getDistance(positions.get("Washington"), positions.get("Caracas")));
        washington.addAdjacentNode(madrid, getDistance(positions.get("Washington"), positions.get("Madrid")));
        washington.addAdjacentNode(rabat, getDistance(positions.get("Washington"), positions.get("Rabat")));

        //rabat
        rabat.addAdjacentNode(madrid, getDistance(positions.get("Rabat"), positions.get("Madrid")));
        rabat.addAdjacentNode(caire, getDistance(positions.get("Rabat"), positions.get("Caire")));
        rabat.addAdjacentNode(niamey, getDistance(positions.get("Rabat"), positions.get("Niamey")));
        rabat.addAdjacentNode(washington, getDistance(positions.get("Rabat"), positions.get("Washington")));
        rabat.addAdjacentNode(caracas, getDistance(positions.get("Rabat"), positions.get("Caracas")));

        //caire
        caire.addAdjacentNode(rabat, getDistance(positions.get("Caire"), positions.get("Rabat")));
        caire.addAdjacentNode(madrid, getDistance(positions.get("Caire"), positions.get("Madrid")));
        caire.addAdjacentNode(niamey, getDistance(positions.get("Caire"), positions.get("Niamey")));
        caire.addAdjacentNode(kinshasa, getDistance(positions.get("Caire"), positions.get("Kinshasa")));

        //niamey
        niamey.addAdjacentNode(caire, getDistance(positions.get("Niamey"), positions.get("Caire")));
        niamey.addAdjacentNode(rabat, getDistance(positions.get("Niamey"), positions.get("Rabat")));
        niamey.addAdjacentNode(kinshasa, getDistance(positions.get("Niamey"), positions.get("Kinshasa")));
        niamey.addAdjacentNode(laCap, getDistance(positions.get("Niamey"), positions.get("La cap")));

        //kinshasa
        kinshasa.addAdjacentNode(niamey, getDistance(positions.get("Kinshasa"), positions.get("Niamey")));
        kinshasa.addAdjacentNode(caire, getDistance(positions.get("Kinshasa"), positions.get("Caire")));
        kinshasa.addAdjacentNode(laCap, getDistance(positions.get("Kinshasa"), positions.get("La cap")));
        kinshasa.addAdjacentNode(brasilia, getDistance(positions.get("Kinshasa"), positions.get("Brasilia")));

        //madrid
        madrid.addAdjacentNode(rabat, getDistance(positions.get("Madrid"), positions.get("Rabat")));
        madrid.addAdjacentNode(caire, getDistance(positions.get("Madrid"), positions.get("Caire")));
        madrid.addAdjacentNode(washington, getDistance(positions.get("Madrid"), positions.get("Washington")));
        madrid.addAdjacentNode(niamey, getDistance(positions.get("Madrid"), positions.get("Niamey")));

        //mexico
        mexico.addAdjacentNode(caracas, getDistance(positions.get("Mexico"), positions.get("Caracas")));
        mexico.addAdjacentNode(washington, getDistance(positions.get("Mexico"), positions.get("Washington")));
        mexico.addAdjacentNode(bogota, getDistance(positions.get("Mexico"), positions.get("Bogota")));
        mexico.addAdjacentNode(lina, getDistance(positions.get("Mexico"), positions.get("Lina")));
        mexico.addAdjacentNode(rabat, getDistance(positions.get("Mexico"), positions.get("Rabat")));

        //Caracas
        caracas.addAdjacentNode(washington, getDistance(positions.get("Caracas"), positions.get("Washington")));
        caracas.addAdjacentNode(bogota, getDistance(positions.get("Caracas"), positions.get("Bogota")));
        caracas.addAdjacentNode(brasilia, getDistance(positions.get("Caracas"), positions.get("Brasilia")));
        caracas.addAdjacentNode(buenosAires, getDistance(positions.get("Caracas"), positions.get("Buenos Aires")));
        caracas.addAdjacentNode(rabat, getDistance(positions.get("Caracas"), positions.get("Rabat")));
        caracas.addAdjacentNode(mexico, getDistance(positions.get("Caracas"), positions.get("Mexico")));

        //Bogota
        bogota.addAdjacentNode(mexico, getDistance(positions.get("Bogota"), positions.get("Mexico")));
        bogota.addAdjacentNode(lina, getDistance(positions.get("Bogota"), positions.get("Lina")));
        bogota.addAdjacentNode(caracas, getDistance(positions.get("Bogota"), positions.get("Caracas")));


        //Lina
        lina.addAdjacentNode(bogota, getDistance(positions.get("Lina"), positions.get("Bogota")));
        lina.addAdjacentNode(buenosAires, getDistance(positions.get("Lina"), positions.get("Buenos Aires")));
        lina.addAdjacentNode(brasilia, getDistance(positions.get("Lina"), positions.get("Brasilia")));
        lina.addAdjacentNode(mexico, getDistance(positions.get("Lina"), positions.get("Mexico")));

        //buenosAires
        buenosAires.addAdjacentNode(laCap, getDistance(positions.get("Buenos Aires"), positions.get("La cap")));
        buenosAires.addAdjacentNode(brasilia, getDistance(positions.get("Buenos Aires"), positions.get("Brasilia")));
        buenosAires.addAdjacentNode(lina, getDistance(positions.get("Buenos Aires"), positions.get("Lina")));
        buenosAires.addAdjacentNode(caracas, getDistance(positions.get("Buenos Aires"), positions.get("Caracas")));

        nodes = new HashMap<>();
        nodes.put("Brasilia", brasilia);
        nodes.put("Mexico", mexico);
        nodes.put("Caracas", caracas);
        nodes.put("Bogota", bogota);
        nodes.put("Washington", washington);
        nodes.put("Rabat", rabat);
        nodes.put("Kinshasa", kinshasa);
        nodes.put("La cap", laCap);
        nodes.put("Buenos Aires", buenosAires);
        nodes.put("Lina", lina);
        nodes.put("Niamey", niamey);
        nodes.put("Madrid", madrid);
        nodes.put("Caire", caire);
    }

    public static DataDemo getInstance() {
        return new DataDemo();
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    public HashMap<String, Integer[]> getPositions() {
        return positions;
    }

    //singleton
    public synchronized static HashMap<String, Airport> getAirports() {
        if(airports==null){
            //airports
            airports = new HashMap<>();
            int nbrPistes, nbrStation, delaiStation, delaiPistes, timeAttDec, delaiAnticollision, boucleAtt;
            Random random = new Random();

            JSONObject jsonObject = new JSONObject();

            for(String name: positions.keySet()){
                nbrPistes = random.nextInt(4) + 1;
                nbrStation = random.nextInt(4) + 1;
                delaiStation = random.nextInt(7) +1;
                delaiPistes = random.nextInt(2) + 1;
                timeAttDec = random.nextInt(1) + 1;
                delaiAnticollision  =  1;
                boucleAtt = random.nextInt(3) + 1;

                Airport airport = new Airport(name, nbrPistes, nbrStation, delaiStation,
                        delaiPistes, timeAttDec, delaiAnticollision, boucleAtt);
                airports.put(name, airport);

                jsonObject.put(name, getAirportJson(airport));
            }

            try
            {
                FileWriter file = new FileWriter("C:/javaEE/eclipse-workspace/Simulator/src/main/webapp/json/airport.json");
                file.write (jsonObject.toJSONString ());
                file.close ();
            }
            catch (IOException e)
            {
                e.printStackTrace ();
            }

        }
        
        return airports;
    }

    private static double getDistance(Integer[] point1, Integer[] point2){
        int x1 = point1[0]; int y1= point1[1];
        int x2 = point2[0]; int y2= point2[1];
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    private static JSONObject getAirportJson(Airport airport){
        JSONObject airportJson = new JSONObject();

        airportJson.put("pistes", airport.getPistes());

        airportJson.put("places_au_sol", airport.getStations());

        airportJson.put("attente_au_sol", airport.getDelaiStation());

        airportJson.put("tmps_acce_piste", airport.getDelaiPiste());

        airportJson.put("duree_att_deco", airport.getTimeAttDec());

        airportJson.put("anticollision", airport.getDelaiAnticollision());

        airportJson.put("boucle_att", airport.getBoucleAtt());

        return airportJson;
    }
}


