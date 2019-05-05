/**
 * 
 */

var database_info_path = "database-info"
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
		}
	});
}




$("#getDataBtn").click(function(){
	$("#tableNamesDiv").empty();
	$("#tableNamesDiv").append("<h1>Tables List</h1>");
	var myAjax = common.loadAjaxCall('processdata?searchWord='+$("#dataId").val() + '&dbInfoId='+$("#databaseInfo").val(),'get')
	myAjax.done(function(response){
		if (response.message && response.message.code == "ERROR") {
			alert(response.message.text);
			return false;
		}
		var searchId = response.object;
		$("#searchId").val(searchId);
		$("#resultInfo").removeClass("hidden");
	});
});

$("#refreshSearchInfoBtn").click(function(){
	fetchExecutionResults();
});



function fetchExecutionResults() {
	var searchId = $("#searchId").val();
	var myAjax = common.loadAjaxCall('processedData?searchId='+searchId ,'get')
	myAjax.done(function(response){
		if (response.message && response.message.code == "ERROR") {
			alert(response.message.text);
			return false;
		}
		console.log("response -> ", response);
		var searchDataInfo = response.object;
		var data = searchDataInfo.keyFoundTables;
		if (data && data.length > 0 ) {
			var clonedObj = $("#templatePage").clone().removeClass("hidden");
			clonedObj.removeAttr("id");
			$.each(data,function(i, tblName){
				var newObj = clonedObj.clone();
				newObj.find('.tblName').html(tblName);
				newObj.find('.tblCheckBox,.display-one-on-one-data').val(tblName);
				$("#tableNamesDiv").append(newObj);
			});
		}
	});
} 



$("#tableNamesDiv").on('click','.display-one-on-one-data',function(){
	var tableName = this.value;
	// need to improve this
	var dataRowDiv = $(this.closest(".row")).find(".col-xs-12:nth-child(2)")
	// till here
	var inputElements$ = $(this.closest(".col-xs-12")).find("input,button")
	inputElements$.prop("disabled",true);
	
	var header$ = dataRowDiv.find(".tblDataRow thead tr");
	header$.empty()
	var body$ = dataRowDiv.find(".tblDataRow tbody");
	body$.empty();
	var dataRowLoadingDiv = $(this.closest(".row")).find(".col-xs-12:nth-child(3)")
	dataRowLoadingDiv.show();
	var searchId = $("#searchId").val();
	var myAjax = common.loadAjaxCall('tableData?tName='+tableName+'&searchId='+searchId ,'get')
	myAjax.done(function(response){
		dataRowLoadingDiv.hide();
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

