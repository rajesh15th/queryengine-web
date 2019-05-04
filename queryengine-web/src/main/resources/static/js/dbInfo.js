/**
 * 
 */


var database_info_path = "database-info"
var dbInfoTbl = $(".db-info-div");
var dbInfoTblEdit = $(".db-info-div-edit");
dbInfoTblEdit.hide();
fetchDatabaseInfoList();
function fetchDatabaseInfoList() {
	var tbody$ = $("#db_info_tbl tbody");
	tbody$.empty();
	var myAjax = common.loadAjaxCall(database_info_path,'get');
	myAjax.done(function(data){
		if (data && data.length > 0 ) {
			var clonedObj = $("#db_info_tbl tfoot tr").clone().removeClass("hidden");
			$.each(data,function(i, dbInfo){
				var newObj = clonedObj.clone();
				newObj.find('.db-name').html(dbInfo.name);
				newObj.find('.db-type').html(getDatabaseTypeName(dbInfo.type));
				newObj.find('.db-driver-name').html(dbInfo.driverName);
				newObj.find('.db-url').html(dbInfo.url);
				newObj.find('.db-username').html(dbInfo.userName);
				newObj.find('.edit,.delete').val(dbInfo.id);
				tbody$.append(newObj);
			});
		} else {
			$("<tr>").append($("<td>",{colspan:6}).text("No data found"))
		}
	});
}

function formCleanup() {
	$(".type-of-operation").text("");
	$(".addUpdate").text("");
	$(".addUpdate").data("action","");
	
	$(".db-info-edit-name").text("");
	$('#dbInfoName').val("");
	$('#dbInfoType').val("");
	$('#dbInfoDriverName').val("");
	$('#dbInfoUrl').val("");
	$('#dbInfoUserName').val("");
	$('#dbInfoPassword').val("");
	$('#dbInfoId').val("");

}


$("#db_info_tbl").on("click",'.edit',function() {
	var dbinfoId = this.value;
	
	$(".type-of-operation").text("Edit")
	var myAjax = common.loadAjaxCall(database_info_path+"/"+dbinfoId,'get');
	myAjax.done(function(dbInfo){
		if (dbInfo) {
			dbInfoTbl.hide();
			dbInfoTblEdit.show();
			
			$(".type-of-operation").text("Edit");
			$(".addUpdate").text("Update");
			$(".addUpdate").data("action","edit");
			
			$(".db-info-edit-name").text(":"+dbInfo.name);
			$('#dbInfoName').val(dbInfo.name);
			$('#dbInfoType').val(dbInfo.type);
			$('#dbInfoDriverName').val(dbInfo.driverName);
			$('#dbInfoUrl').val(dbInfo.url);
			$('#dbInfoUserName').val(dbInfo.userName);
			$('#dbInfoPassword').val(dbInfo.password);
			$('#dbInfoId').val(dbInfo.id);
		} else {
			alert ("Data not found with id " + dbinfoId);
		}
	});
});

$(".addDbInfo").click(function() {
	$(".type-of-operation").text("Add");
	$(".addUpdate").text("Create");
	$(".addUpdate").data("action","add");
	dbInfoTbl.hide();
	dbInfoTblEdit.show();
});

$(".addUpdate").click(function(){
	var dbInfoObj = {
			"name":$('#dbInfoName').val(),
			"type":$('#dbInfoType').val(),
			"driverName":$('#dbInfoDriverName').val(),
			"url":$('#dbInfoUrl').val(),
			"userName":$('#dbInfoUserName').val(),
			"password":$('#dbInfoPassword').val(),
	}
	var url = "";
	var methodType = "POST";
	if ($(".addUpdate").data("action") == "edit") {
		dbInfoObj.id = $('#dbInfoId').val();
		url = "/" + $('#dbInfoId').val();
		methodType = "PUT";
	}
	var myAjax = common.loadAjaxCall(database_info_path + url, methodType, dbInfoObj);
	
	myAjax.done(function(data){
		console.log("hello", data);
		fetchDatabaseInfoList();
		$(".cancelAddUpdate").click();
	});
	
});

$(".cancelAddUpdate").click(function(){
	formCleanup();
	dbInfoTbl.show();
	dbInfoTblEdit.hide();
});

function getDatabaseTypeName(typeId) {
	var typeName = "";
	switch (typeId) {
	case 1:
		typeName = "MSSQL";
		break;
	case 2:
		typeName = "POSTGRESQL";
		break;
	case 3:
		typeName = "ORACLE";
		break;
	case 4:
		typeName = "HANA";
		break;
	default:
		typeName = "Unknown";
		break;
	
	}
	return typeName;
}






