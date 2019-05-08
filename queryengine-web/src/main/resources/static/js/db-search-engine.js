/**
 * 
 */

var database_info_path = "database-info";
var searchDataInfo = {};
var lastFetchCount = 0;
var loadInterval;
fetchDatabaseInfoList();
function fetchDatabaseInfoList() {
	var dbSelectBox$ = $("#databaseInfo");
	var myAjax = common.loadAjaxCall(database_info_path,'get');
	myAjax.done(function(response){
		if (response.message && response.message.code == "ERROR") {
			alert(response.message.text);
			return false;
		}
		var data = response.object;
		var option$ = $("<option>",{value:'0', text:'Select'})
		if (data && data.length > 0 ) {
			$.each(data,function(i, dbInfo){
				var option$ = $("<option>",{value:dbInfo.id, text:dbInfo.name})
				dbSelectBox$.append(option$);
			});
		} else {
			alert("Database not configured. Please configure and return to search page");
			location.href = "/databaseInfo"
		}
		setFromLocalStorage();
	});
}

function setFromLocalStorage() {
	$("#databaseInfo").val(queryLocalStorage.getItem('databaseInfoId'));
	$("#searchWord").val(queryLocalStorage.getItem('searchWord'));
	$("#searchId").val(queryLocalStorage.getItem('searchId'));
	if ($("#searchId").val() != '') {
		fetchExecutionResults();
	}
	
}


$("#getDataBtn").click(function(){
	var myAjax = common.loadAjaxCall('processdata?searchWord='+$("#searchWord").val() + '&dbInfoId='+$("#databaseInfo").val(),'get');
	myAjax.done(function(response){
		if (response.message && response.message.code == "ERROR") {
			alert(response.message.text);
			return false;
		}
		var searchId = response.object;
		$("#searchId").val(searchId);
		queryLocalStorage.setItem('databaseInfoId',$("#databaseInfo").val());
		queryLocalStorage.setItem('searchWord',$("#searchWord").val());
		queryLocalStorage.setItem('searchId',searchId);
		loadInterval = setInterval(myTimer, 2000);
		$("#resultInfo").removeClass("hidden");
	});
});

$("#refreshSearchInfoBtn").click(function(){
	fetchExecutionResults();
});



function fetchExecutionResults() {
	if (!loadInterval) {
		loadInterval = setInterval(myTimer, 2000);
	}
	var searchId = $("#searchId").val();
	$("#resultInfo").removeClass("hidden");
	var myAjax = common.loadAjaxCall('processedData?searchId='+searchId ,'get')
	myAjax.done(function(response){
		if (response.message && response.message.code == "NOTFOUND") {
			queryLocalStorage.removeItem("searchId");
			alert(response.message.text);
			location.reload();
			return false;
		}
		
		if (response.message && response.message.code == "ERROR") {
			alert(response.message.text);
			return false;
		}
		
		console.log("response -> ", response);
		searchDataInfo = response.object;
		
		$(".no-of-tables-processed").text(searchDataInfo.completedTables.length)
		$(".no-of-total-table").text(searchDataInfo.totalTables.length)
		$(".search-key-info").text(searchDataInfo.searchWord)
		$(".no-of-key-found-processed").text(searchDataInfo.keyFoundTables.length)
		
		if ( searchDataInfo.searchCompleted ) {
			$("#stopLookupBtn, #refreshSearchInfoBtn").hide();
			loadIntervalStop();
		} else {
			$("#stopLookupBtn, #refreshSearchInfoBtn").show();
		}
		var data = searchDataInfo.keyFoundTables;
		var lastFetchCountTemp = data.length;
		data = data.slice(lastFetchCount)
		lastFetchCount = lastFetchCountTemp;
		if (data && data.length > 0 ) {
			var clonedObj = $("#searchResultTbl tfoot tr").clone().removeClass("hidden");
			var tbody$ = $("#searchResultTbl tbody");
			$.each(data,function(i, tblName){
				var newObj = clonedObj.clone();
				newObj.find('.tblNameSno').html(i+1);
				newObj.find('.tblName').html(tblName);
				newObj.find('.tblCheckBox,.display-one-on-one-data').val(tblName);
				tbody$.append(newObj);
			});
		}
	});
} 



$("#tableNamesDiv").on('click','.display-one-on-one-data',function(){
	var tableName = this.value;
	// need to improve this
	//var dataRowDiv = $(this.closest(".row")).find(".col-xs-12:nth-child(2)")
	var trClassName = "row-" + tableName.replace(".", "");
	var dataRowDiv = $("#dataPopulate").clone().addClass(trClassName);
	// till here
	var currentRow = $(this.closest("tr"));
	var inputElements$ = currentRow.find("input,button")
	inputElements$.prop("disabled",true);
	
	var header$ = dataRowDiv.find(".tblDataRow thead tr");
	header$.empty()
	var body$ = dataRowDiv.find(".tblDataRow tbody");
	body$.empty();
	//var dataRowLoadingDiv = $(this.closest(".row")).find(".col-xs-12:nth-child(3)")
	//dataRowLoadingDiv.show();
	var searchId = $("#searchId").val();
	var myAjax = common.loadAjaxCall('tableData?tName='+tableName+'&searchId='+searchId ,'get')
	myAjax.done(function(response){
		//dataRowLoadingDiv.hide();
		inputElements$.prop("disabled",false);
		if (response.message && response.message.code == "ERROR") {
			alert(response.message.text);
			return false;
		}
		var data = response.object;
		if (data && data.length > 0 ) {
			
			var thElement$ = $("<th>");
			$.each(data[0], function(i,headerVal){
				header$.append(thElement$.clone().text(headerVal));
			});
			
			
			var trElement$ = $("<tr>");
			if (data.length == 1 ) {
				trElement$.clone().append($("<td>", {colspan:data[0].length,class:'text-center'}).text("No records found")).appendTo(body$)
			} else {
				
				var tdElement$ = $("<td>");
				$.each(data.slice(1), function(i,rowValue){
					var tr$ = trElement$.clone();
					tr$.appendTo(body$)
					$.each(rowValue,function(j, colValue){
						tr$.append(tdElement$.clone().text(colValue));
					});
				});
			}
			$("tr."+trClassName).remove();
			dataRowDiv.insertAfter(currentRow);
		}
		
	});
});

$("#displayAllTableDataBtn").click(function(){
	var triggerableTables$ = $("#tableNamesDiv .display-one-on-one-data:not(:disabled)");
	triggerableTables$.click();
	
});

$("#displaySelectedTableDataBtn").click(function(){
	var triggerableTables$ = $("#tableNamesDiv .tblCheckBox:checked");
	triggerableTables$.each(function(i,obj){
		$($(obj).closest("tr")).find(".display-one-on-one-data").click();
	});
	
});
$("#executionInfoBtn").click(function(){
	$("#refreshSearchInfoBtn, #executionInfoBtn, #tableNamesDiv").hide();
	$("#tableInfoBtn, #executionInfoDiv").show();
	displayExecutionInfo();
	
});
$("#tableInfoBtn").click(function(){
	$("#refreshSearchInfoBtn, #executionInfoBtn, #tableNamesDiv").show();
	$("#tableInfoBtn,#executionInfoDiv").hide();
	$("#refreshSearchInfoBtn").click();
});



function displayExecutionInfo() {
	var tbl$ = $("#executionInfoTbl");
	tbl$.find(".no-of-key-found-tables").html(searchDataInfo.keyFoundTables.length);
	tbl$.find(".no-of-total-tables").html(searchDataInfo.totalTables.length);
	tbl$.find(".no-of-complated-tables").html(searchDataInfo.completedTables.length);
	tbl$.find(".no-of-pending-tables").html(searchDataInfo.pendingTables.length);
	tbl$.find(".no-of-processing-tables").html(searchDataInfo.processingTables.length);
	tbl$.find(".no-of-failed-tables").html(searchDataInfo.failedTables.length);
	tbl$.find(".execution-status").html(searchDataInfo.searchCompleted ? "Completed" : "Running");
	tbl$.find(".search-word").html(searchDataInfo.searchWord);
	tbl$.find(".datasource-name").html(searchDataInfo.databaseInfo.name);
	tbl$.find(".messages").html(searchDataInfo.messages);
	tbl$.find(".total-execution-time").html(searchDataInfo.dateDiff);
}

$("#resetDataBtn").click(function(){
	clearSearch();
});

function clearSearch() {
	queryLocalStorage.removeItem("searchId");
	queryLocalStorage.removeItem("searchWord");
	queryLocalStorage.removeItem("databaseInfoId");
	searchDataInfo = {};
	if ( !searchDataInfo.searchCompleted ) {
		$("#stopLookupBtn").click();
	}
	location.reload();
}


function myTimer() {
	fetchExecutionResults();
}

function loadIntervalStop() {
  clearInterval(loadInterval);
}

$("#stopLookupBtn").click(function(){
	var searchId = $("#searchId").val();
	var myAjax = common.loadAjaxCall('terminateSearchProcess?searchId='+searchId ,'get');
	fetchExecutionResults();
	var body$ = dataRowDiv.find(".tblDataRow tbody");
	body$.empty();
});

