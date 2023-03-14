package prototype;

public class Airport {
    private String name;
    private int pistes;
    private int stations;
    private int delaiStation;
    private int delaiPiste;
    private int timeAttDec; //(atterissage/decolage)
    private int delaiAnticollision;
    private int boucleAtt;
    private int  pistesDisponible;
    private int stationsDisponible;
    public static final int OUT_AIRPORT=1;
    public static final int IN_AIRPORT=2;

    //airplan will use piste after will go to station after will back to piste and go

    public Airport(String name, int pistes, int stations, int delaiStation, int delaiPiste, int timeAttDec,
                   int delaiAnticollision, int boucleAtt) {
        this.name = name;
        this.pistes = pistes;
        this.stations = stations;
        this.delaiStation = delaiStation;//temps de charger est décharger les passagies
        this.delaiPiste = delaiPiste;//(station-->piste) ou (piste--> station)
        this.timeAttDec = timeAttDec;//(piste --> out) ou (out-->piste)
        this.delaiAnticollision = delaiAnticollision;//if piste non disponible and the airplan in the station
        this.boucleAtt = boucleAtt;//if piste non disponible and the airplan is out
        this.pistesDisponible = pistes;
        this.stationsDisponible = stations;
    }

    public String getName() {
        return name;
    }

    public int getPistes() {
        return pistes;
    }

    public int getStations() {
        return stations;
    }

    public int getPistesDisponible() {
        return pistesDisponible;
    }

    public int getStationsDisponible() {
        return stationsDisponible;
    }

    public int getDelaiStation() {
        return delaiStation;
    }

    public int getDelaiPiste() {
        return delaiPiste;
    }

    public int getTimeAttDec() {
        return timeAttDec;
    }

    public int getDelaiAnticollision() {
        return delaiAnticollision;
    }

    public int getBoucleAtt() {
        return boucleAtt;
    }

    //piste
    public  int utiliserPiste(String name, int type) {
        if(pistesDisponible>0){
           pistesDisponible--;
            switch (type){
                case OUT_AIRPORT://out -->piste
                    System.out.println(name+" arreter au piste N°: "+(pistesDisponible+1)+" de "+this.name);
                    return timeAttDec;
                case IN_AIRPORT ://station-->piste
                     stationsDisponible++;
                     System.out.println(name+" est quiter station vers piste N°: "+(pistesDisponible+1)+" de "+this.name);
                    return delaiPiste;
            }
        }else
            switch (type){
                case OUT_AIRPORT:
                    System.out.println(name+" la piste n'est pas disponible attendre: "+boucleAtt+" UT dans "+this.name);
                    return boucleAtt;
                case IN_AIRPORT:
                    System.out.println(name+" la piste n'est pas disponible attendre: "+delaiAnticollision+" UT dans "+this.name);
                    return delaiAnticollision;
            }

     return 0;}

    public  int quiterPiste(String name){
       pistesDisponible++;
       System.out.println(name+" est quiter la piste de "+this.name+" vers un autre airoport");
       return timeAttDec;
    }

    public boolean isPisteDisponible(){

    return pistesDisponible > 0;
    }

    //station
    //piste-->station
    public  int utiliserStation(String name){
      if(stationsDisponible>0){
          stationsDisponible--;
          pistesDisponible++;
          System.out.println(name+" utiliser station N°: "+(stationsDisponible+1)+" doit attendre: "+delaiStation+" UT dans "
                  +this.name+" pour charger et décharger passagers");
          return delaiStation;
      }else{
          System.out.println(name+" station de "+this.name+" n'est pas disponible attendre: "+delaiPiste+" UT");
          return delaiPiste;//piste--> station
      }

      }

    public boolean isStationDisponible(){

    return stationsDisponible>0;}

    @Override
    public String toString() {
        return "AirportDemo{" +
                "name='" + name + '\'' +
                ", pistes=" + pistes +
                ", stations=" + stations +
                ", delaiStation=" + delaiStation +
                ", delaiPiste=" + delaiPiste +
                ", timeAttDec=" + timeAttDec +
                ", delaiAnticollision=" + delaiAnticollision +
                ", boucleAtt=" + boucleAtt +
                ", pistesDisponible=" + pistesDisponible +
                ", stationsDisponible=" + stationsDisponible +
                '}';
    }
}
