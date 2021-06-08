window.onload = function(){
	var customtags = document.getElementsByTagName("customtag");
	
	for(var i = 0 ; i < customtags.length ; i++){
		
		var id = "", name = "", onchange ="";
		
		if(customtags[i].getAttribute("id") != undefined){
			id = customtags[i].getAttribute("id");
		}
		
		if(customtags[i].getAttribute("name") != undefined){
			id = customtags[i].getAttribute("name");
		}
		
		if(customtags[i].getAttribute("onchange") != undefined){
			id = customtags[i].getAttribute("onchange");
		}
		var displays = customtags[i].getAttribute("display");
		var domains = customtags[i].getAttribute("domain");
		
		var display = displays.split(",");
		var domain = domains.split(",");
		
		var html ="";

		html += "<select>"
		for(var j = 0 ; j < display.length ; j++){
			if(j == 1){
				html += "<option value='" + domain[j] + "' selected>" + display[j] + "</option>";
			}else {
			html += "<option value='" + domain[j] + "' >" + display[j] + "</option>";
			}
		}
		
		html += "</select>";
		customtags[i].innerHTML = html;
	}
}