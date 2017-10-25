                
    var tagCount = 0;
    var currentAdmin = 0;
    var locked = true;
    var newsitemlist = [];

    function addTagToNewsitem(id){
        var targetTag = document.getElementById("tagListboxOfItem"+id);
        var tagstring = "";
        var greenlight = false;
        for (i = 1; i <= tagCount; i++){                   
            currentOption = targetTag[i];
            if (currentOption.selected == true){
               tagstring = currentOption.innerHTML;
               greenlight = true;
            }
        }
        if (greenlight){
            sendXMLHttpRequest("POST","updatenewsitemadd/"+id, getNewsItemList, tagstring);

        }    
    }
    
    function checkNewsItemList(){
        sendXMLHttpRequest("GET", "getnewsitemlist", newNieuwsItem, null);
    }
    
    function convertTagListToString(){
        var tagstring = "";
        for (i = 1; i <= tagCount; i++){
            var tagid = "tag"+i;
            var taglabelid = "taglabel"+i;
            if (document.getElementById(tagid).checked){
                tagstring = tagstring.concat(document.getElementById(tagid).name+" ");
            }
        }
        return tagstring;
    }

    function deleteAll(){
        sendXMLHttpRequest("DELETE", "nuwsdelete", getNewsItemList, null);
    }

    function deleteOne(id){
        sendXMLHttpRequest("DELETE", "deleteone/"+id, getNewsItemList, null);
    }

    function deleteTag(){
        var targetTag = document.getElementById("taglistbox");
        var tagid = "";
        for (i = 1; i <= tagCount; i++){
            currentOption = targetTag[i];
            if (currentOption.selected == true){
               tagid =currentOption.value;
               var endpoint = "deletetag/"+tagid;

               sendXMLHttpRequest("DELETE", endpoint, getTags, null);
               sendXMLHttpRequest("POST", "deletetagfromallnewsitems", getTags, tagid);
            }
        }
    }

    function getNewsItemList(){
        sendXMLHttpRequest("GET", "nuwstitles", processNewsItemList, null);
    }

    function getNUNLlinks(){
        sendXMLHttpRequest("GET", "nunllinks", processNunlLinksRequest, null);
    }

    function getTags(){
        sendXMLHttpRequest("GET", "nuwstags", processGetTagRequest, null);    
    }

    function getTagsForItemList(){
        sendXMLHttpRequest("GET", "nuwstags", processTagsForItemList, null);
    }

    function hideMainContent(){
        document.getElementById("linkerhelft").style.visibility="hidden";
        document.getElementById("rechterhelft").style.visibility="hidden";
    }

    function kopieerURL(value){
        document.getElementById("nieuwsurlinput").value = value;
    }

    function login(){
        sendXMLHttpRequest("GET", "adminlogin", processLogin, null);
    }

    function logout(){
        hideMainContent();
        document.getElementById("preloginfield").style.visibility="visible";
        currentAdmin = 0;
        document.getElementById("loggedinAdmin").style.visibility="hidden";
        document.getElementById("logoutbutton").style.visibility="hidden";
        document.getElementById("adminregisterfield").style.visibility="hidden";
        document.getElementById("linktousersite").style.visibility="hidden";
        document.getElementById("usernameTextbox").value = "";
        document.getElementById("passwordTextbox").value = "";  
    }
    
    function newNieuwsItem(data){
        var newUrl= document.getElementById("nieuwsurlinput").value;
        var newTags = convertTagListToString();
        var id = currentAdmin;
        var nieuwsitem = '{"tags":"'+newTags+'" ,"url":"'+newUrl+'"}';
        var greenlight = true
        for (i = 0; i < data.length; i++){
            if (data[i].url == newUrl){
                greenlight = false
            }
        }
        if (greenlight){
           sendXMLHttpRequest("POST", "nuwspost/"+id, getNewsItemList, nieuwsitem); 
        }
        else{
            alert("This newsitem is already in the database");
        } 
    }

    function newTag(){
        var newtag= document.getElementById("newTagInput").value;
        var tag = '{"tag":"'+newtag+'"}'; 
        sendXMLHttpRequest("POST","newtag", getTags, tag);
    }

    function processGetTagRequest(data){
        document.getElementById("newTagInput").value = "";
        document.getElementById("taglist").innerHTML = "";
        document.getElementById("taglistbox").innerHTML = "<option selected></option>";
        tagCount = 0;
        for (i = 0; i < data.length; i++) {
            tagCount++;
            var j = parseInt(i);
            var textaccumulation = "<label class=container><input id='tag"+tagCount+"' name='"+data[j].tag+"' type='checkbox'> "+data[j].tag+"<span class='checkmark'></span></label><br>";
            document.getElementById("taglist").innerHTML += textaccumulation;
            var tagListbox =  document.createElement("option");
            tagListbox.value=data[j].id;
            tagListbox.innerHTML = data[j].tag;
            document.getElementById("taglistbox").appendChild(tagListbox); 
        } 
        getNewsItemList();
    }

    function processLogin(data){
        var inputUsername = document.getElementById("usernameTextbox").value;
        var inputPassword = document.getElementById("passwordTextbox").value;
        var loginOK = false;
        var superadmin = false;
        var currentAdminNaam = "";
        for (i = 0; i < data.length; i++) {
           if (inputUsername === data[i].naam && inputPassword === data[i].password){
               loginOK = true;
               currentAdmin = data[i].id;
               currentAdminNaam = data[i].naam;
               if (data[i].admintype == 1){
                   superadmin = true;
               }
           }
        }
        if (loginOK){
            document.getElementById("linkerhelft").style.visibility="visible";
            document.getElementById("rechterhelft").style.visibility="visible";  
            document.getElementById("loggedinAdmin").style.visibility="visible";
            document.getElementById("loggedinAdmin").innerHTML="Logged in as "+currentAdminNaam;
            document.getElementById("logoutbutton").style.visibility="visible";
            document.getElementById("linktousersite").style.visibility="visible";
            document.getElementById("preloginfield").style.visibility="hidden";
            if (superadmin){
                 document.getElementById("adminregisterfield").style.visibility="visible";
            }
        }
        else{
            alert("Combination of Username and Password does not exist");
        }  
    }

    function processNewAdminRequest(data){
        document.getElementById("registerUsernameTextbox").value = "";
        document.getElementById("registerPsswordTextbox").value = "";
        if(data){
            alert("Registration failed! Username allready exists.");
        }
    }

    function processNewsItemList(data){
        document.getElementById(id="newsItemInDatabaseList").innerHTML = "";
        newsitemlist = [];
        for (i = data.length-1; i >= 0; i--){
            var titelvak =  document.createElement("div");
            titelvak.innerHTML = "<b>"+data[i].title+"</b>";
            var tagvak =  document.createElement("div");
            tagvak.innerHTML = "<i>Tags: "+data[i].tags+"</i>";
            var adminvak =  document.createElement("div");
            adminvak.innerHTML = "<i>Added by "+data[i].admin.naam+" on "+data[i].datetime+"</i>" ;
            var deletebutton = document.createElement("button");
            deletebutton.innerHTML = "Delete newsitem";
            deletebutton.addEventListener("click", function(){deleteOne(this.id);});
            var idInt = data[i].id;
            deletebutton.id = idInt;
            var itemtagbox =  document.createElement("select");
            itemtagbox.class = "itemlistlistbox";
            itemtagbox.id = "tagListboxOfItem"+data[i].id;
            itemtagbox.innerHTML = "<option selected></option>";
            var removetagbutton =  document.createElement("button");
            removetagbutton.innerHTML = "Remove tag";
            removetagbutton.id = data[i].id;
            removetagbutton.addEventListener("click", function(){removeTagFromNewsitem(this.id);});
            var addtagbutton =  document.createElement("button");
            addtagbutton.innerHTML = "Add tag";
            addtagbutton.id = data[i].id;
            addtagbutton.addEventListener("click", function(){addTagToNewsitem(this.id);});
            document.getElementById("newsItemInDatabaseList").appendChild(titelvak);                                  
            document.getElementById("newsItemInDatabaseList").appendChild(adminvak);
            document.getElementById("newsItemInDatabaseList").appendChild(tagvak);
            document.getElementById("newsItemInDatabaseList").appendChild(deletebutton);
            document.getElementById("newsItemInDatabaseList").appendChild(itemtagbox);
            document.getElementById("newsItemInDatabaseList").appendChild(addtagbutton);
            document.getElementById("newsItemInDatabaseList").appendChild(removetagbutton);
            document.getElementById("newsItemInDatabaseList").innerHTML += "<br><br>";
            newsitemlist.push(data[i].id);
        }     
        getTagsForItemList();
    }

    function processNunlLinksRequest(data){
        for (i = 0; i < data.length; i++){
            var lijst = data[i];
            var indexA = lijst.indexOf("https");
            var indexB = lijst.indexOf("\" target=");
            var lijstUitgekleed = lijst.substring(indexA, indexB); 
            var lijstfinal = "<button id='button' type='button' onclick='kopieerURL(\""+ lijstUitgekleed + "\")'>copy URL</button>" + lijst + "<br>";
            document.getElementById("artikellijst").innerHTML += lijstfinal;
        }
    } 

    function processTagsForItemList(data){
        for (j = 0; j < data.length; j++) {
            for (k =0; k < newsitemlist.length; k++){
                var tagListbox =  document.createElement("option");
                tagListbox.value=data[j].id;
                tagListbox.innerHTML = data[j].tag;
                document.getElementById("tagListboxOfItem"+newsitemlist[k]).appendChild(tagListbox);
            } 
        }    
    }

    function registerNewAdmin(){
        var newUsername = document.getElementById("registerUsernameTextbox").value;
        var newPassword = document.getElementById("registerPsswordTextbox").value;
        var listboxcontent = document.getElementById("adminlistbox");
        var adminType = 2;
        if (listboxcontent[2].selected == true){
            adminType = 1;
        }
        var admin = '{"naam":"'+newUsername+'" ,"password":"'+newPassword+'" ,"admintype":"'+adminType+'"}';
        sendXMLHttpRequest("POST", "registeradmin", processNewAdminRequest, admin);
    }    

    function removeTagFromNewsitem(id){
        var targetTag = document.getElementById("tagListboxOfItem"+id);
        var tagstring = "x";
        var greenlight = false;
        for (i = 1; i <= tagCount; i++){                   
            currentOption = targetTag[i];
            if (currentOption.selected == true){
               tagstring = currentOption.innerHTML;
               greenlight = true;
            }
        }
        if (greenlight){
            sendXMLHttpRequest("POST", "updatenewsitemremove/"+id, getNewsItemList, tagstring);
        }    
    }

    function startMethods(){
        getNUNLlinks(); 
        getTags(); 
        getNewsItemList(); 
        hideMainContent();
    }

    function Unlock(){
        if (locked){
            document.getElementById("buttonDeleteAll").disabled = false;
            locked = false;
        }
        else{
            document.getElementById("buttonDeleteAll").disabled = true;
            locked = true;
        }
    }

    // GENERIC METHOD FOR SENDING REQUESTS TO SERVER
    function sendXMLHttpRequest(path ,endpoint, callbackFunction, requestitem){     
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                if (path == "GET"){
                    var response = JSON.parse(this.responseText);
                    callbackFunction(response);
                }
                else{
                    callbackFunction();
                } 
            };
        }    
        xhttp.open(path, "http://188.166.3.211:8082/"+endpoint, true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send(requestitem);
    }
