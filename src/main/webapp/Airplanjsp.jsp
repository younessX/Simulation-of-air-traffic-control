<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Simulator</title>
<link href="style.css" rel="stylesheet">
<script src="/script.js" type="text/javascript"></script>
</head>
<body>
<canvas id="canvas"></canvas>
<img id="mapImage" src="images/map_res.jpg">
<img id="avionImage" src="images/avion.png">
<button id='reload'>Reload</button>
 <script type="text/javascript">
 const reload = document.getElementById('reload');
 
 reload.addEventListener('click', ()=>{
	 window.location.reload();
 });
 
 const canvas = document.getElementById('canvas');
 const ctx = canvas.getContext('2d');
 const mapImage = document.getElementById('mapImage');
 canvas.width = 750;
 canvas.height = 480;
 let airportSize = 3;
 var airplans = [];
 let colorIndex = 0;
 var colors = ["#FFDAB9", "#FF7F50", "#000080", "#4169E1", "#2F2F2F"];

 class Position{
  constructor(x, y, name) {
   this.x = x;
   this.y = y;
   this.name = name;
  }
 }


 //setPositions
 var positionArray = [];

 //brasilia
 positionArray.push(new Position(273, 370,"Brasilia"));
 //la cap
 positionArray.push(new Position(395, 412, "La cap"));
 //washington
 positionArray.push(new Position(207, 259, "Washington"));
 //rabat
 positionArray.push(new Position(345, 278, "Rabat"));
 //Le Caire
 positionArray.push(new Position(413, 287, "Caire"));
 //Niamey
 positionArray.push(new Position(375, 315, "Niamey"));
 //Kinshasa
 positionArray.push(new Position(400, 355, "Kinshasa"));
 //madrid
 positionArray.push(new Position(353, 257, "Madrid"));
 //mexico
 positionArray.push(new Position(160, 300, "Mexico"));
 //caracas
 positionArray.push(new Position(227, 330, "Caracas"));
 //bogota
 positionArray.push(new Position(211, 344, "Bogota"));
 //Lina
 positionArray.push(new Position(208, 365, "Lina"));
 //Buenos Aires
 positionArray.push(new Position(240, 422, "Buenos Aires"));

 function drawCircle(x, y){
   ctx.fillStyle = "#FF6347";
   ctx.beginPath();
   ctx.arc(x, y, 16, 0, 2 * Math.PI);
   ctx.fill();
 }

 class AirPlan{
   constructor(airports, airportTimes, landingTimes){
   this.width= 15;
   this.height= 15;
   this.airports = airports;
   this.speed = 0.3;
   this.index = 1;
   this.start = 0;
   this.x = airports[this.index-1].position[0];
   this.y = airports[this.index-1].position[1];
   this.translateX = airports[this.index].position[0]-this.x -this.width/2;
   this.translateY = airports[this.index].position[1]-this.y -this.height/2;
   //speed
   this.translateX <0 ? this.speedX=-this.speed : this.speedX = this.speed;
   this.translateY <0 ? this.speedY=-this.speed : this.speedY = this.speed;
   this.timer = 0;
   this.avionImg = new Image();
   this.avionImg.src = 'avion.png';
   //waiting in airports
   this.airportTimes = airportTimes;
   this.airportTime = airportTimes[this.index-1]*1000;
   //time before landing
   this.landingTimes = landingTimes;
   this.landingTime = this.landingTimes[this.index-1];
   this.degrees=0;
   this.color = colors[colorIndex];
   colorIndex++;
   }

   draw(){
     ctx.drawImage(this.avionImg, this.x, this.y, this.width, this.height);
   }

   createLigne(p1, p2){
     ctx.beginPath();
     ctx.lineWidth = 2;
     ctx.moveTo(p2.x+airportSize/2, p2.y+airportSize/2);
     ctx.lineTo(p1.x, p1.y);
     ctx.strokeStyle = this.color;
     ctx.stroke(); 
    }
   
   drawPath(){
     for(let i=0; i<this.airports.length-1; i++){
       var x1, y1, x2, y2;
       x1 = this.airports[i].position[0];  
       y1 = this.airports[i].position[1];

       x2 = this.airports[i+1].position[0];  
       y2 = this.airports[i+1].position[1];
       this.createLigne(new Position(x1,y1), new Position(x2,y2));
     } 
   }

   update(deltaTime){
     this.drawPath();

    //in the movement
    if(Math.abs(this.translateX)-(this.start+airportSize/2)<=0 &&
       this.degrees<this.landingTime*360){
       ctx.save();
       ctx.translate(this.x, this.y);
       ctx.rotate(this.degrees*Math.PI/180);
       ctx.drawImage(this.avionImg, 0, 0, this.width-airportSize/2, this.height-airportSize/2);
       //drawCicle(0 ,0);
       ctx.restore();
       this.degrees+=2;
     
    }else if(this.start <Math.abs(this.translateX) &&
     this.index<this.airports.length){
       this.draw();
       var rayon = 16;
       var okk=true;
       pertPositions.forEach(p=>{
         if (Math.sqrt(Math.pow(this.x+this.width - p.x, 2) + 
             Math.pow(this.y+this.height - p.y, 2)) <= rayon){
             this.start += Math.abs(this.speedX/1000);
             this.x += this.speedX/1000;
             this.y += this.speedY*Math.abs(this.translateY)
             /Math.abs(this.translateX)/1000;
             okk=false;
         }
       });
       
       if(okk){
         this.start += Math.abs(this.speedX);
         this.x += this.speedX;
         this.y += this.speedY*Math.abs(this.translateY)
             /Math.abs(this.translateX);
       }
       
     //in stop  
    }else{
     //if the airplan finich it waiting time in airport
     if(this.timer>= this.airportTime){
         this.index++;
         this.timer = 0;
         this.draw();
            //if we still have a position
           if(this.index<this.airports.length){ 
             this.start=0;
             this.airportTime = this.airportTimes[this.index-1]*1000;
             this.translateX = this.airports[this.index].position[0] - this.x-this.width/2;
             this.translateY = this.airports[this.index].position[1] - this.y-this.height/2;
             this.translateX <0 ? this.speedX=-this.speed : this.speedX = this.speed;
             this.translateY <0 ? this.speedY=-this.speed : this.speedY = this.speed;
             this.landingTime = this.landingTimes[this.index-1];
             this.degrees=0;
           } 
     }else{//sinon count the time
       this.draw();
       this.timer+=deltaTime;
     }
   }
   }

 }

 function displayText(x, y, city){
   ctx.font = "bold 9px tahoma";
   ctx.fillStyle = "#1C1C1C";
   ctx.fillText(city, x, y);
 }

 /****airport info*/
 function displayText2(x, y, text){
   ctx.font = "11px tahoma";
   ctx.fillStyle = "#000";
   ctx.fillText(text, x, y);
 }

 var data = {"duree_att_deco":"",
 "pistes":"","attente_au_sol":"","places_au_sol":"",
 "tmps_acce_piste":"","boucle_att":"","anticollision":""};
 var airportName = "Airport";

 function drawAiroportInfo(){
     ctx.strokeStyle = 'red';
     ctx.strokeRect(5, 347, 170, 112);
     displayText2(55, 360, airportName);
     displayText2(10, 375, "Pistes: "+data.pistes);
     displayText2(10, 388, "Places au sol: "+data.places_au_sol);
     displayText2(10, 401, "Attente au sol: "+data.attente_au_sol);
     displayText2(10, 414, "Accès aux pistes: "+data.tmps_acce_piste);
     displayText2(10, 427, "Durée atter/décol: "+data.duree_att_deco);
     displayText2(10, 439, "Anti-collision(inter att./décol.): "+data.anticollision);
     displayText2(10, 452, "Boucle d'attente: "+data.boucle_att);
 }

 function airportInfo(name){
   fetch('json/airport.json').then(res=>{
     res.json().then(d=>{
        data = d[name];
        airportName = name
        drawAiroportInfo(data[name], name);
    })
 });
 }

 //mousemove
 canvas.addEventListener('click',(e)=>{
  positionArray.forEach(p=>{
   point = new Position(e.offsetX, e.offsetY); 
   if((point.x>=p.x-3 && point.x<=p.x+3) && (point.y>=p.y-3 && point.y<=p.y+3)){
      airportInfo(p.name);
     return;
   }
   
  });
 })

 /***************************/
 function drawAiroports(){
   drawAiroportInfo();
   for(let i=0; i<positionArray.length; i++){
     ctx.fillStyle='#00008B';
     ctx.beginPath();
     ctx.arc(positionArray[i].x, positionArray[i].y, airportSize, 0, Math.PI*2);
     ctx.fill();
     displayText(positionArray[i].x, positionArray[i].y, positionArray[i].name);
   } 
 }

 function displayAirplans(deltaTime){
  airplans.forEach(airPlan=>{
   //airPlan.draw();
   airPlan.update(deltaTime);
  }); 
 }


 //perturbation
 let perturbation = false;
 let pertPositions = [];
 function createPerturbation(){
   if(perturbation){
             let a = 70;
             drawCircle(positionArray[2].x+a, positionArray[2].y+10);
             pertPositions.push(new Position(positionArray[2].x+a, positionArray[2].y+10));
             
             drawCircle(positionArray[0].x-10, positionArray[0].y+30);
             pertPositions.push(new Position(positionArray[0].x-10, positionArray[0].y+30));
             
             //drawCircle(positionArray[5].x+5, positionArray[5].y+40);
             //pertPositions.push(new Position(positionArray[5].x+5, positionArray[5].y+40));
     }
 }



 let lastTime=0;
 function animate(timeStamp){
     let deltaTime = timeStamp -lastTime;
     lastTime = timeStamp;
     ctx.clearRect(0, 0, canvas.width, canvas.height);
     ctx.drawImage(mapImage, 0, 0);      
     drawAiroports();
     createPerturbation();  
     displayAirplans(deltaTime);
     requestAnimationFrame(animate);    
 }

 let airPlanIndex=0;

 function getPositions(){
   fetch('http://localhost:8080/Simulator/json/output1.json').then(res=>{
         res.json().then(data=>{
           console.log(data);	 
           perturbation = data[0].perturbation;
           for(let i=1; i<data.length; i++){
               var airplan = new AirPlan(data[i].airports, data[i].AirportTimes, 
                 data[i].landingTimes);
                 airplans.push(airplan);
                 airPlanIndex++;
           }
         
        })
        animate(0); 
     });
 }

 getPositions();
 
 </script>
</body>
</html>