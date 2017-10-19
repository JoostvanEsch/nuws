var tagCount = 0;
            
            function convertTagListToString(){
                var tagstring = "";
                for (i = 1; i <= tagCount; i++){
                    var tagid = "tag"+i;
                    var taglabelid = "taglabel"+i;
                    if (document.getElementById(tagid).checked){
                        tagstring = tagstring.concat(document.getElementById(tagid).name+" ");
                    }
                }
                console.log(tagstring);
                return tagstring;
            }
            
            function getNewsItemList(){
                var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function() {
                        if (this.readyState == 4 && this.status == 200) {
                            var data = JSON.parse(this.responseText);
                            for (i = 0; i < data.length; i++){
                                    var titelvak =  document.createElement("div");
                                    titelvak.innerHTML = "<b>"+data[i].title+"</b>";
                                    var tagvak =  document.createElement("div");
                                    tagvak.innerHTML = "<i>Tags: "+data[i].tags+"</i>";
                                    var htmlbreak =  document.createElement("br");
                                    document.getElementById("newsItemInDatabaseList").appendChild(titelvak);
                                    document.getElementById("newsItemInDatabaseList").appendChild(htmlbreak);
                                    document.getElementById("newsItemInDatabaseList").appendChild(tagvak);
                                    document.getElementById("newsItemInDatabaseList").appendChild(htmlbreak);
                               }
                                
                        }
                    };
                xhttp.open("GET", "http://localhost:8082/nuwstitles");
                xhttp.setRequestHeader("Content-type", "application/json");
                xhttp.send();
            }
            
            function newNieuwsItem(){
                var newUrl= document.getElementById("nieuwsurlinput").value;
                var newTags = convertTagListToString();
                var nieuwsitem = '{"tags":"'+newTags+'" ,"url":"'+newUrl+'"}'; 
                console.log(nieuwsitem);
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 202) {
                        document.getElementById("outputserver").innerHTML = this.responseText;
                    }
                };
                xhttp.open("POST", "http://localhost:8082/nuwspost", true);
                xhttp.setRequestHeader("Content-type", "application/json");
                xhttp.send(nieuwsitem);
            }
             
            function kopieerURL(value){
                document.getElementById("nieuwsurlinput").value = value;
            }
            
            function getNUNLlinks(){
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function(){
                    if (this.readyState == 4 && this.status == 200){
                        document.getElementById("artikellijst").innerHTML = "";
                        var data = JSON.parse(this.responseText);
                        for (i = 0; i < data.length; i++){
                            var lijst = data[i];
                            var indexA = lijst.indexOf("https");
                            var indexB = lijst.indexOf("\" target=");
                            var lijstUitgekleed = lijst.substring(indexA, indexB); 
                            var lijstfinal = lijst + " -> <button id='button' type='button' onclick='kopieerURL(\""+ lijstUitgekleed + "\")'>copy URL</button><br>";
                            document.getElementById("artikellijst").innerHTML += lijstfinal; 
                        }
                    } 
                };
                xhttp.open("GET", "http://localhost:8082/nunllinks", true);
                xhttp.setRequestHeader("Content-type", "application/json");
                xhttp.send();  
             } 
            
            function getTags(){
                document.getElementById("taglist").innerHTML = "";
                sendGetRequest("nuwstags", procesTags)
            }   

            function procesTags(data){
                tagCount = 0;
                for (i = 0; i < data.length; i++) {
                    tagCount++;
                    var j = parseInt(i);
                    var textaccumulation = "<label class=container><input id='tag"+tagCount+"' name='"+data[j].tag+"' type='checkbox'>"+data[j].tag+"<span class='checkmark'></span></label><br>"
                    document.getElementById("taglist").innerHTML += textaccumulation;             
                }
            }
            
            function deleteAll(){
               //console.log("ik doe het"); 
               var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                       console.log(this.responseText);
                    }
                };
                xhttp.open("GET", "http://localhost:8082/nuwsdelete", true);
                xhttp.setRequestHeader("Content-type", "application/json");
                xhttp.send();
            }
            
            function newTag(){
            	var newtag= document.getElementById("newTagInput").value;
                var tag = '{"tag":"'+newtag+'"}'; 
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        console.log(this.responseText);
                        getTags();
                        document.getElementById("newTagInput").value = "";
                    }
                };
                xhttp.open("POST", "http://localhost:8082/newtag", true);
                xhttp.setRequestHeader("Content-type", "application/json");
                xhttp.send(tag); 
            }
            
            /**
             * GENERIC METHODEN VOOR REQUESTS NAAR BACKEND
             */
            
            function sendGetRequest(endpoint, callbackFunction){
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        var callbackItem = JSON.parse(this.responseText)
                        callbackFunction(callbackItem);
                    };
                }    
                    xhttp.open("get", "http://localhost:8080/"+endpoint, true);
                    xhttp.setRequestHeader("Content-type", "application/json");
                    xhttp.send();
            }
                
   