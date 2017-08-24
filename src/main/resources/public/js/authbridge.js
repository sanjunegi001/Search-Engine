var solr_error_heading = "Is your solr Connected!!" ;
var solr_error_message = "Seems your solr has some issues.Check the connectivity or restart the server again.";
var name_weightage = "Name_Weigtage";
var name_weight_thres = "Name_Weigtage_Threshold";
var addr_weightage = "Address_Weigtage";
var addr_weight_thres = "Address_Weigtage_Threshold";
var father_weightage = "Father_Weigtage";
var father_weight_thres = "Father_Weigtage_Threshold";
var state_weightage = "State_Weigtage";
var district_weightage = "District_Weigtage";

function getActiveTab() {
    var loc = window.location;
    var path =  loc.pathname.substring(loc.pathname.lastIndexOf('/') + 1, loc.pathname.length);
    console.info(path);
    if(path =="searchQuery")
    	path = "search";
    if(path == "addeditschedular")
    	path = "schedular";
    $(".navigation li").removeClass("active");
    $("#"+path+"-page").addClass("active");
}

function redirectTO(path){
	window.location.href = "./"+path;
}

function getServerStaus(){
	console.info("Getting server status");
	var color = ["rgba(255,255,255,0.5)" , "#5C6BC0" , "#FF7043" , "#26A69A" , "#5C6BC0"];
	$.ajax({
		url: "./rest/server/status",
		success: function(result){
			if(result =="")
				 throw new Error("solr down");
			if(result.code = 200){
				var status = result.data;
				var counter =1;
				for(i in status){
					$(".url-"+(counter)).html(status[i].baseURl);
					$("#server-load-"+(counter)).empty();
					if(status[i].state=="active"){
						$("#server-state-"+counter).html("Online");
						$("#server-state-"+counter).removeClass("offline");
						$("#server-state-"+counter).addClass("online");
						$("#server-status-"+counter).removeClass("hidden");
						sparkline("#server-load-"+(counter), "area", 30, 50, "basis", 750, 2000, color[counter]);
					}else{
						$("#server-state-"+counter).html("Offline");
						$("#server-state-"+counter).removeClass("online");
						$("#server-state-"+counter).addClass("offline");
						$("#server-status-"+counter).removeClass("hidden");
					}
					counter++;
				}
			}else{
				for(var i=1;i<=5;i++){
					$("#server-state-"+i).html("Offline");
					$("#server-state-"+i).removeClass("online");
					$("#server-state-"+i).addClass("offline");
				}
			}
		},
		error : function(){
			for(var i=1;i<=5;i++){
				$("#server-state-"+i).html("Offline");
				$("#server-state-"+i).removeClass("online");
				$("#server-state-"+i).addClass("offline");
			}
		}
	});
}

function getSchedualar(){
	$.ajax({
		url: "./rest/scheculing",
		success: function(result){
			if(result.code = 200){
				paintFullImport(result.data["Full_Import"]);
				paintDeltaImport(result.data["Delta_Import"]);
			}
		},
		error : function(){
				
		}
	});
}


function geModifications(){
	$.ajax({
		url: "./rest/modifications",
		success: function(result){
			if(result.code = 200){
				paintModifications(result.data)
			}
		},
		error : function(){
				
		}
	});
}

function paintModifications(data){
	console.info(data);
	for(i in data){
		switch(data[i].screen) {
	    case "Alias":
	    	$("#alias-modif").html("Last Operation <b class='cap'>"+data[i].operation+"</b> was done on "+data[i].modifiedOn);
	        break;
	    case "Abbreviations":
	        $("#abbr-modif").html("Last Operation <b class='cap'>"+data[i].operation+"</b> was done on "+data[i].modifiedOn);
	        break;
	    case "Stopwords" :
	    	$("#stopwords-modif").html("Last Operation <b class='cap'>"+data[i].operation+"</b> was done on "+data[i].modifiedOn);
		     break;
	    default:
	        
	}
	}
}

function paintFullImport(fullImport){
	if(fullImport == null){
		$("#full-import").html("You hav'nt configured <b>Full data import</b> yet.Click to Add!")
	}else{
		$("#full-import").html("Next Full Import is scheduled at <br/><b> "+fullImport.dateTime + "</b>")
	}
}

function paintDeltaImport(deltaImport){
	if(deltaImport == null){
		$("#delta-import").html("You hav'nt configured <b>Delta import</b> yet.Click to Add!")
	}else{
		$("#delta-import").html("Next Delta Import is scheduled at <br/><b> "+deltaImport.dateTime+ "</b>")
	}
}

function getWeightAge(){
	$.ajax({
		url: "./rest/weightage",
		success: function(result) {
			if(result.code = 200) {
				var weightage = result.data;
				for(i in weightage){
					if(weightage[i].name === name_weightage){
						if( $("#nameWeight").attr('type') != undefined) {
							$("#nameWeight").val(weightage[i].weightage)
						}
						else{
							$("#nameWeight").html(weightage[i].weightage)
						}
					}
					if(weightage[i].name === name_weight_thres){
						if( $("#nameThreshold").attr('type') != undefined) {
							$("#nameThreshold").val(weightage[i].weightage)
						}
						else{
							$("#nameThreshold").html(weightage[i].weightage)
						}
					}
					if(weightage[i].name === addr_weightage){
						 if( $("#addWeight").attr('type') != undefined) {
							$("#addWeight").val(weightage[i].weightage)
						 }
						 else {
							$("#addWeight").html(weightage[i].weightage)
						 }
					}
					if(weightage[i].name === addr_weight_thres){
						 if( $("#addrThreshold").attr('type') != undefined) {
							$("#addrThreshold").val(weightage[i].weightage)
						 }
						 else {
							$("#addrThreshold").html(weightage[i].weightage)
						 }
					}
					if(weightage[i].name === father_weightage) {
						 if( $("#fatherWeight").attr('type') != undefined) {
							$("#fatherWeight").val(weightage[i].weightage)
						 }
						 else {
							$("#fatherWeight").html(weightage[i].weightage)
						 }
					}
					if(weightage[i].name === father_weight_thres) {
						 if( $("#fatherWeightThreshold").attr('type') != undefined) {
							$("#fatherWeightThreshold").val(weightage[i].weightage)
						 }
						 else {
							$("#fatherWeightThreshold").html(weightage[i].weightage)
						 }
					}
					if(weightage[i].name === state_weightage) {
						if( $("#stateWeight").attr('type') != undefined) {
							$("#stateWeight").val(weightage[i].weightage)
						}
						else {
							$("#stateWeight").html(weightage[i].weightage)
						}
					}
					if(weightage[i].name === district_weightage) {
						if( $("#districtWeight").attr('type') != undefined) {
							$("#districtWeight").val(weightage[i].weightage)
						}
						else {
							$("#districtWeight").html(weightage[i].weightage)
						}
					}
				}
			}
		}, error : function(){
			$("#resWeight").html(
				"<span class='error'>Error!! Could not retrieve configured weightages.</span>");
		}
	});
}

function changeWeight(){
	
	var nameWeight = $("#nameWeight").val();
	var nameWeightThres = $("#nameThreshold").val();
	var addWeight = $("#addWeight").val();
	var addrWeightThres = $("#addrThreshold").val();
	var fatherWeight = $("#fatherWeight").val();
	var fatherWtThres = $("#fatherWeightThreshold").val();
	var stateWeight = $("#stateWeight").val();
	var districtWeight = $("#districtWeight").val();
	
	if((Number(nameWeight) + Number(addWeight) + Number(fatherWeight) + Number(stateWeight) + Number(districtWeight)) !== 100) {
		$("#resWeight").html(
			"<span class='error'>Error!! Sum of Name, Address, Father weightage, State and District weightage should be equal to 100.</span>");
		return false;
	}

	var weightageDTO = new Array();
	var weightageName = new Object();
	weightageName.name = name_weightage;
	weightageName.weightage = nameWeight;
	weightageDTO.push(weightageName);

	var weightageNameThres = new Object();
	weightageNameThres.name = name_weight_thres;
	weightageNameThres.weightage = nameWeightThres;
	weightageDTO.push(weightageNameThres);
	
	var weightageAdd = new Object();
	weightageAdd.name = addr_weightage;
	weightageAdd.weightage = addWeight;
	weightageDTO.push(weightageAdd);
	
	var weightageAddrThres = new Object();
	weightageAddrThres.name = addr_weight_thres;
	weightageAddrThres.weightage = addrWeightThres;
	weightageDTO.push(weightageAddrThres);

	var weightageFather = new Object();
	weightageFather.name = father_weightage;
	weightageFather.weightage = fatherWeight;
	weightageDTO.push(weightageFather);
	
	var weightageFatherThreshold = new Object();
	weightageFatherThreshold.name = father_weight_thres;
	weightageFatherThreshold.weightage = fatherWtThres;
	weightageDTO.push(weightageFatherThreshold);

	var weightageStateThreshold = new Object();
	weightageStateThreshold.name = state_weightage;
	weightageStateThreshold.weightage = stateWeight;
	weightageDTO.push(weightageStateThreshold);

	var weightageDistrictThreshold = new Object();
	weightageDistrictThreshold.name = district_weightage;
	weightageDistrictThreshold.weightage = districtWeight;
	weightageDTO.push(weightageDistrictThreshold);

	$.ajax({
		url: "./rest/weightage",
		type : "POST",
		data : JSON.stringify(weightageDTO),
		contentType: "application/json",
		success: function(result){
			if(result.code = 200){
				console.info("success");
				$("#resWeight").html("<span class='success'>Success</span>");
			}else{
				$("#resWeight").html("<span class='error'>Error!!</span>");
			}
			
		}, error : function(){
			$("#resWeight").html("<span>Error!!</span>");
		}
	});
	return false;
}

function saveStopWord(){
	var stopword = $("#stopword").val();
	if (stopword != null && stopword != '') {  
      //  alert('you entered text ' + stopword)  
   
	$.ajax({
		url: "./rest/stopword/"+stopword,
		type: "POST",
		success: function(result){
			if(result.code = 200){
				getStopWords();
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
	 } else {  
	        alert('Please enter proper stopword to proceed.');
	    }  
}

function getStopWords(){
	$.ajax({
		url: "./rest/stopword",
		beforeSend : function(){
			$("#stopword-container").html(giveLoader());
		},
		success: function(result){
			$("#stopword-container").empty();
		},complete : function(response){
			result = response.responseJSON;
			if(result.code = 200){
				var stopword = $.parseJSON(result.data.wordSet);
				if(stopword!=null){
					paintStopWord(stopword.managedList);
				}else{
					$("#stopword-container").empty();
					$("#noResults").html("No stop words added!!");
				}
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
}

function paintStopWord(stopwordArray){
	
	$("#stopword-container").empty();
	for (i in stopwordArray){
		var stopword = stopwordArray[i];
		var li = $("<li>").attr({"class" : "select2-selection__choice" , "title" : stopword});
		var span = $("<span>").attr({
						"class" : "select2-selection__choice__remove" ,
						"role" : "presentation",
						"onclick" : "removeStopWord('"+stopword+"')"}).html("×");
		li.append(span).append(stopword);
		$("#stopword-container").append(li);
	}
}

function removeStopWord(stopword){
	$.ajax({
		url: "./rest/stopword/"+stopword,
		type: 'DELETE',
		success: function(result){
			var synonyms = result;
			if(synonyms.code == 200){
				console.info("delete synonis  : "+synonyms);
				getStopWords();
			}
			
		}
	});
}

function removeAlias(alias){
	$.ajax({
		url: "./rest/alias",
		type: 'DELETE',
		data:alias,
		success: function(result){
			var synonyms = result;
			if(synonyms.code == 200){
				console.info("delete synonis  : "+synonyms);
				getAlias();
			}
			
		}
	});
}

function size(managedMap){
	var counter = 0;
	
	$.each(managedMap, function (key, value) {
		counter++;
	});
	return counter;
}

function getAlias(){
	$.ajax({
		url: "./rest/alias",
		beforeSend : function(){
			$("#alias-container").html(giveLoader());
		},success: function(result){
			$("#alias-container").empty();
		},complete : function(response){
			result = response.responseJSON;
			if(result.code = 200){
				var synonyms = $.parseJSON(result.data.synonymMappings);
				if(size(synonyms.managedMap)!=0){
					paintAlias(synonyms.managedMap);
					$("#noResults").hide();
				}else{
					$("#alias-container").empty();
					$("#noResults").html("No Alias words added!!").show();
				}
				
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
}

function abbrAliasHeader(){
	
	var thead = $("<thead>");
	var tr = $("<tr>");
	var thSn = $("<th>").html("SNO");
	var thKey = $("<th>").html("Key");
	var thvalue = $("<th>").html("Value");
	var thEdit = $("<th>").html("Edit");
	var thDelete = $("<th>").html("Delete");
	tr.append(thSn).append(thKey).append(thvalue).append(thEdit).append(thDelete);
	return thead.append(tr);
}

function paintAlias(managedMap){
	
	$("#alias-container").empty();
	var counter = 0;
	var table = $("<table>").attr({"class" : "table text-nowrap"});
	var tableHeader = abbrAliasHeader();
	table.append(tableHeader);
	var tbody = $("<tbody>");
	$.each(managedMap, function (key, value) {
		
		var tr = $("<tr>");
		var tdCounter  = $("<td>").html(++counter)
		var tdkey  = $("<td>").html(key)
		var valueString = "";
		for (j in value){
			valueString+= value[j]+",";
			
		}
		var tdvalue  = $("<td>").html(valueString)
		
		var editIcon  = $("<div>").attr("class" , "");
		var editSpan = $("<span>").attr({"class" : "label bg-pink  cursor-pointer dropdown-toggle", "onclick" : "editAbbrAlias('Alias' ,'"+key+"' , '"+value+"')"}).html('EDIT');
		editIcon.append(editSpan);
		var editTD  = $("<td>").html(editIcon);
		
		var deleteIcon  = $("<div>").attr("class" , "");
		var deleteSpan = $("<span>").attr({"class" : "label label-primary cursor-pointer dropdown-toggle", "onclick" : "removeAlias('"+value+"')"}).html('DELETE');
		deleteIcon.append(deleteSpan);
		
		var deleteTD  = $("<td>").html(deleteIcon);
		tr.append(tdCounter).append(tdkey).append(tdvalue).append(editTD).append(deleteTD);
		tbody.append(tr);
	});
	table.append(tbody);
	$("#alias-container").html(table);
}

function abbrCount(){
	$.ajax({
		url: "./rest/synonym",
		beforeSend : function(){
			$("#abbr-count").html(giveLoader());
		},
		success: function(result){
			$("#abbr-count").empty();
		},
		complete : function(response){
			result = response.responseJSON;
			if(result.code = 200){
				var synonyms = $.parseJSON(result.data.synonymMappings);
				var counter = 0;
				mnagMap = synonyms.managedMap;
				for(i in mnagMap){
					counter ++;
				}
				$("#abbr-count").html(counter);
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
}

function stopWordCount(){
	$.ajax({
		url: "./rest/stopword",
		beforeSend:function(){
			$("#stopword-count").html(giveLoader());
		},
		success: function(result){
			$("#stopword-count").empty();
		},complete : function(response){
			result = response.responseJSON;
			if(result.code = 200){
				var stopword = $.parseJSON(result.data.wordSet);
				if(stopword== null){
					$("#stopword-count").html(0);
				}else{
					$("#stopword-count").html(stopword.managedList.length);
				}
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
}

function aliasCount(){
	$.ajax({
		url: "./rest/alias",
		beforeSend : function(){
			$("#alias-count").html(giveLoader());
		},
		success: function(result){
			$("#alias-count").empty();
		},
		complete : function(response){
			result = response.responseJSON;
			if(result.code = 200){
				var synonyms = $.parseJSON(result.data.synonymMappings);
				var counter = 0;
				mnagMap = synonyms.managedMap;
				for(i in mnagMap){
					counter ++;
				}
				$("#alias-count").html(counter);
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
}


function getAbbreviations(){
	$.ajax({
		url: "./rest/synonym",
		beforeSend : function(){
			$("#abbr-container").html(giveLoader());
		},
		success: function(result){
			$("#abbr-container").empty();
		},complete : function(response){
			result = response.responseJSON;
			if(result.code = 200){
				var synonyms = $.parseJSON(result.data.synonymMappings);
				if(size(synonyms.managedMap)!=0){
					paintAbbreviations(synonyms.managedMap);
					$("#noResults").hide();
				}else{
					$("#abbr-container").empty();
					$("#noResults").html("No Abbreviations added!!").show();
				}
				
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
}

function showError(tag,heading ,  message){
	var alertIfo = $("<div>").attr({"class" : "alert alert-info alert-styled-left alert-arrow-left alert-component"});
	var h6 = $("<h6>").addClass("class" , "alert-heading text-semibold").html(heading); 
	var span = $("<span>").html(message);
	
	alertIfo.append(h6);
	alertIfo.append(span);
	$("#"+tag).html(alertIfo);
}


function saveAlias(){
	var synonymMappingDTO = new Object();
	synonymMappingDTO.value = $("#aliasValues").val();
	
	if (synonymMappingDTO.value != null && synonymMappingDTO.value != '') {
	
	$.ajax({
		url: "./rest/alias",
		type : "PUT",
		data : JSON.stringify(synonymMappingDTO),
		contentType: "application/json",
		success: function(result){
			if(result.code = 200){
				getAlias();
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
	}
	else{
		alert('Please enter proper alias to proceed further.');
	}

}

function saveAbbreviations(){
	var value = [];
	var synonymMappingDTO = new Object();
	synonymMappingDTO.key = $("#synonymKey").val();
	var synonymValues = $("#synonymValues").val();
	
	synonymMappingDTO.value = $("#synonymValues").val();
	if (synonymMappingDTO.key != null && synonymMappingDTO.key != '' && synonymMappingDTO.value != null && synonymMappingDTO.value != '') {
	$.ajax({
		url: "./rest/synonym",
		type : "PUT",
		data : JSON.stringify(synonymMappingDTO),
		contentType: "application/json",
		success: function(result){
			if(result.code = 200){
				getAbbreviations();
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
	}
	else{
		alert('Please enter proper key/value to proceed further');
	}
}


function paintAbbreviations(managedMap){
	
	$("#abbr-container").empty();
	var counter = 0;
	var table = $("<table>").attr({"class" : "table text-nowrap"});
	var tableHeader = abbrAliasHeader();
	table.append(tableHeader);
	var tbody = $("<tbody>");
	$.each(managedMap, function (key, value) {
		
		var tr = $("<tr>");
		var tdCounter  = $("<td>").html(++counter)
		var tdkey  = $("<td>").html(key)
		var tdvalue  = $("<td>").html(value)
		
		var editIcon  = $("<div>").attr("class" , "");
		var editSpan = $("<span>").attr({ "class" : "label bg-pink cursor-pointer ", "onclick" : "editAbbrAlias('Abbreviation' , '"+key+"' , '"+value+"')"}).html('EDIT');
		editIcon.append(editSpan);
		var editTD  = $("<td>").html(editIcon);
		
		var deleteIcon  = $("<div>").attr("class" , "");
		var deleteSpan = $("<span>").attr({"class" : "label label-primary cursor-pointer ", "onclick" : "removeAbbr('"+key+"')"}).html('DELETE');
		deleteIcon.append(deleteSpan);
		var deleteTD  = $("<td>").html(deleteIcon);
		
		tr.append(tdCounter).append(tdkey).append(tdvalue).append(editTD).append(deleteTD);
		tbody.append(tr);
	});
	
	table.append(tbody);
	$("#abbr-container").html(table);
	
}

function giveLoader(){
	var i = $("<i>").attr({"class" : "icon-spinner2 spinner"});
	return i;
}

function confirmSchduling(type, forward){
	var messageShow;
	
	if(type == "FullImport"){
		messageShow = "This will erase out your previous index and create new one!!";
	}
	if(type == "DeltaImport"){
		messageShow = "This will update index with new values!!";
	}
	
	BootstrapDialog.show({
        title: 'Are you sure want to run '+type,
        message: messageShow,
        buttons: [{
            label: 'Confirm',
            action: function(dialog) {
          	  redirectTO(forward);
          	 
            }
        }]
    });
}


function editAbbrAlias(type , key , value){
	var divRow = $("<div>").attr({"class" : "dialog-area"});
	var divKey =$("<div>").attr({"class" : "col-md-4"});
	var labelKey = $("<label>").html(type+" Key");
	var inputKey = $("<input>").attr({
					"type" : "text" ,
					"id" : "updateAbbrAlaskey",
					"class" : "form-control",
					"disabled"	: "disabled",
					"value" : key});
	var inputHiddenKey = $("<input>").attr({
		"type" : "hidden" ,
		"id" : "updateAbbrAlasHiddenkey",
		
		"value" : value});
	divKey.append(labelKey).append(inputKey).append(inputHiddenKey);
	var divValue =$("<div>").attr({"class" : "col-md-4"});
	var labelValue = $("<label>").html(type+" Value");
	var inputValue = $("<input>").attr({
		"type" : "text" ,
		"id" : "updateAbbrAliasValue",
		"class" : "form-control",
		"placeholder"	: "Update value here",
		"value" : value});
	divValue.append(labelValue).append(inputValue);
	divRow.append(divKey).append(divValue);
	var loader = $("<div>").attr({"id" : "loader" , "class" : "col-md-2"});
	divRow.append(loader);
	var messageRow = divRow;
	
	  BootstrapDialog.show({
          title: 'Update '+type,
          message: messageRow,
          buttons: [{
              label: 'Update',
              action: function(dialog) {
            	  if(type == "Abbreviation")
            		  saveUpdatedAbbr(dialog);
            	 if(type== "Alias")
            		 saveUpdatedAlias(dialog)
              }
          }]
      });
}



function saveUpdatedAlias(dialog){
	var synonymMappingDTO = new Object();
	synonymMappingDTO.key = $("#updateAbbrAlasHiddenkey").val();
	synonymMappingDTO.value = $("#updateAbbrAliasValue").val();
	
	$.ajax({
		url: "./rest/alias",
		type : "POST",
		data : JSON.stringify(synonymMappingDTO),
		contentType: "application/json",
		beforeSend : function(){
			$("#loader").html(giveLoader())
		},
		success: function(result){
			if(result.code = 200){
				console.info("Here successfully saved Aliases");
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		},complete:function(result){
			if(result.code = 200){
				$("#loader").empty();
				dialog.close();
				getAlias();
			}
		}, error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});

}

function saveUpdatedAbbr(dialog){
	var value = [];
	var synonymMappingDTO = new Object();
	synonymMappingDTO.key = $("#updateAbbrAlaskey").val();
	synonymMappingDTO.value = $("#updateAbbrAliasValue").val();
	
	$.ajax({
		url: "./rest/synonym",
		type : "POST",
		data : JSON.stringify(synonymMappingDTO),
		contentType: "application/json",
		beforeSend : function(){
			$("#loader").html(giveLoader())
		},
		success: function(result){
			if(result.code = 200){
				console.info("Here successfully saved abbreviations");
			}else{
				showError("error",solr_error_heading ,  solr_error_message);
			}
			
		},complete:function(result){
			if(result.code = 200){
				$("#loader").empty();
				dialog.close();
				getAbbreviations();
			}
		},
		error : function(){
			showError("error",solr_error_heading ,  solr_error_message);
		}
	});
}



function removeAbbr(key){
	$.ajax({
		url: "./rest/synonym",
		type: 'DELETE',
		data:key,
		success: function(result){
			var synonyms = result;
			if(synonyms.code == 200){
				console.info("delete synonis  : "+synonyms);
				getAbbreviations();
			}
			
			//paintAbbreviations(synonyms.synonymMappings);
		}
	});
}

function paintTable(caseDetails){
	for(var i in caseDetails){
		var caseDetail = caseDetails[i];
		var tr = $("<tr>");
			if(i%2 ==0){
				tr.addClass("pure-table-odd");
			}else{
				tr.addClass("pure-table");
			}

		var rank = $("<td>").html(caseDetail.rank);
		var pid = $("<td>").html(caseDetail.partyId);
		var pname = $("<td>").html(caseDetail.partyName);
		var pfather = $("<td>").html(caseDetail.partyFather);
		var padd = $("<td>").html(caseDetail.partyAddress);
		var pcaseid = $("<td>").html(caseDetail.caseId);
		//var pscore = $("<td>").html(caseDetail.score);
		var fper = $("<td>").html(caseDetail.fatherPercentage);
		var pweight = $("<td>").html(caseDetail.weightedPercentage);
		var pnmper = $("<td>").html(caseDetail.nameMatchPercentage);
		var pamper = $("<td>").html(caseDetail.addressMatchPercentage);
		var pnamper = $("<td>").html(caseDetail.bothMatchPercentage);
		
		
		tr.append(rank).append(pid).append(pname).append(pfather).append(padd).append(pcaseid).append(fper)
									.append(pnmper).append(pamper).append(pnamper).append(pweight);
		$('table > tbody').append(tr);
	}
}



function getMoreResult(){
	$("#start").val(parseInt($("#start").val())+1);
	$("#search").submit();
}

function getPrevResult(){
	$("#start").val(parseInt($("#start").val())-1);
	$("#search").submit();
}

function getFirstResult(){
	$("#start").val(0);
	$("#search").submit();
}


function setPagination(totalPages, showMore){
	var start = parseInt($("#start").val());
	
	if(start>=1){
		var li = $("<li>").attr({"page" : "first" ,"class" : "paginate_button" });
		var span = $("<span>").html("First");
		li.append(span);
		$("#pagination").append(li);
		
		var li = $("<li>").attr({"page" : "prev" , "class" : "paginate_button" });
		var span = $("<span>").html("Previous");
		li.append(span);
		$("#pagination").append(li);
	}
	
	for(var i=1;i<=totalPages;i++){
		var li = $("<li>").attr({"page" : i , "class" : "paginate_button" });
		var span = $("<span>").html(i+start*10);
		li.append(span);
		if(i==1)
			li.addClass("current");
		$("#pagination").append(li);
	}
	if(showMore){
		var li = $("<li>").attr({"page" : "more", "class" : "paginate_button" });
		var span = $("<span>").html("More");
		li.append(span);
		$("#pagination").append(li);
	}
	$("#pagin").addClass("dataTables_paginate paging_simple_numbers");
	$("#pagination").addClass("pagination");
	

}


function searchQuery(){
	var searchQuery = new Object();
	searchQuery.name = $("#name").val();
	searchQuery.address = $("#address").val();
	searchQuery.start = $("#start").val();
	searchQuery.count = $("#count").val();
	
	
	
}