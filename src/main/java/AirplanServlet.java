
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import prototype.Airplan;
import prototype.DataDemo;


@WebServlet("/AirplanServlet")
public class AirplanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean perturbation = false;
	private List<Airplan> airplansList = new ArrayList();
       
    public AirplanServlet() {
        super();
    }
    
    

	
	@Override
	public void init() throws ServletException {
		super.init();
		
		 System.out.println("--------------Simulator du contorle aerien--------------------");
		//empty file
		try{   JSONArray jsonArray = new JSONArray();
            FileWriter file = new FileWriter("C:/javaEE/eclipse-workspace/Simulator/src/main/webapp/json/output.json");
            file.write (jsonArray.toJSONString());
            file.close();
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
		
		DataDemo.getInstance();
		Airplan airplan1 = new Airplan("Brasilia","Caire", Airplan.typeAirplan.RETITE);
	    Airplan airplan2 = new Airplan("Madrid","Rabat", Airplan.typeAirplan.RETITE);
	    airplansList.add(airplan1);
	    airplansList.add(airplan2);
	    		
		
        try
        {   JSONArray jsonArray = new JSONArray();
            FileWriter file = new FileWriter("C:/javaEE/eclipse-workspace/Simulator/src/main/webapp/json/output.json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("perturbation", perturbation);
            jsonArray.add(jsonObject);
            file.write (jsonArray.toJSONString());
            file.close();
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
        
        
        for(Airplan airplan : airplansList){
            airplan.start();
        }
		
	}




	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		 //create file
		//DataDemo data = DataDemo.getInstance();
        /**Scanner scanner = new Scanner(System.in);
        Airplan.typeAirplan typeAirplan;
        String depart="", destination="";
        int nbrAvions;
       
        System.out.print("choisir le nombre d'avions: ");
        nbrAvions = scanner.nextInt();

        for(int i=1;i<=nbrAvions; i++){
            System.out.println("avoin numero: "+i);
            System.out.println("saisie son type: ");
            System.out.println("0: pour une prtite avoin");
            System.out.println("1: pour une moyenne avoin");
            System.out.println("2: pour une grande avoin");
            System.out.print("votre choix: ");
            int avionType  = scanner.nextInt();
            switch (avionType){
                case 1:
                    typeAirplan = Airplan.typeAirplan.MOYENNE;
                    break;

                case 2:
                    typeAirplan = Airplan.typeAirplan.GRANDE;
                    break;
                default:
                    typeAirplan = Airplan.typeAirplan.RETITE;
            }

            System.out.println("donnez l'airoport de depart et d'arrivé: ");
            int index = 1;
            for(String name : data.getAirports().keySet()){
                System.out.print(index+") "+name+"\t");
                if(index%3==0) System.out.println();
                index++;
            }
            System.out.println("choix d'airoport de depart: ");
            int departIndex = scanner.nextInt();
            System.out.println("choix d'airoport d'arriver: ");
            int destIndex = scanner.nextInt();

            index=1;
            for(String airport: data.getAirports().keySet()){
                if(index==departIndex) depart = airport;
                else if(index == destIndex) destination = airport;
                index++;
            }

            System.out.println("vous avez choisir depart: "+depart+" destination: "+destination);

           
            Airplan airplan = new Airplan(depart, destination, Airplan.typeAirplan.RETITE);
            airplan.setName("Avion N° "+i);
            airplansList.add(airplan);
        }

        System.out.println("vous voulez lancer des perturbation:");
        System.out.println("1: pour OUI");
        System.out.println("0: pour NON");
        int choix = scanner.nextInt();
        switch (choix){
            case 0:
                perturbation = false;
                break;
            case 1:
                perturbation = true;
                break;
        }

        
        Airplan airplan1 = new Airplan("Brasilia","Caire", Airplan.typeAirplan.RETITE);
        Airplan airplan2 = new Airplan("Madrid","Bogota", Airplan.typeAirplan.RETITE);
        airplansList.add(airplan1);
        airplansList.add(airplan2);
        

        for(Airplan airplan : airplansList){
            airplan.start();
        }
        

        try
        {   JSONArray jsonArray = new JSONArray();
            FileWriter file = new FileWriter("C:/javaEE/eclipse-workspace/Simulator/src/main/webapp/output.json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("perturbation", perturbation);
            jsonArray.add(jsonObject);
            file.write (jsonArray.toJSONString());
            file.close();
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }*/

        
        boolean start = true;
        
        while(start) {
        	start = false;
        	for(Airplan airplan : airplansList) {
            	if(airplan.isAlive()){
            		start = true;
            	}
            }
        	
           if(!start){
        	   request.getRequestDispatcher("Airplanjsp.jsp").forward(request, response);
           }
        }
         
    }

      
	

}
