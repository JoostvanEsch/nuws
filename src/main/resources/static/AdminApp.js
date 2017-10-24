 //kutfunctie
            function getTagsForItemList(id){
                var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function() {
                        if (this.readyState == 4 && this.status == 200) {
                            document.getElementById("tagListboxOfItem"+id).innerHTML = "<option selected></option>";
                            var data = JSON.parse(this.responseText);
                            for (j = 0; j < data.length; j++) {
                                var tagListbox =  document.createElement("option");
                                tagListbox.value=data[j].id;
                                tagListbox.innerHTML = data[j].tag;
                                document.getElementById("tagListboxOfItem"+id).appendChild(tagListbox);
                            } 
                        }
                    };
                xhttp.open("GET", "http://localhost:8082/nuwstags");
                xhttp.setRequestHeader("Content-type", "application/json");
                xhttp.send();
            }