var adt = {
		contextPath : "http://localhost:9553/"
}

var common = {

	validateDataResponse : function(result) {
		var status = false;
		if (result != null && result.hasMessages) {
			if (result.messages[0].code == "SUCCESS") {
				status = true;
			} else {
				this.displayMessages(result.messages);
			}
		} else {
			var messages = [ {
				code : globalMessage['anvizent.message.error.code'],
				text : globalMessage['anvizent.package.label.unableToProcessYourRequest']
			} ];
			this.displayMessages(messages);
		}
		return status;
	},

	loadAjaxCall : function(url, type, data) {
		var header = this.getcsrfHeader();
		return this.loadAjaxCall(url, type, data, header);
	},

	loadAjaxCall : function(url, type, data, header) {
		if (!header) {
			header = this.getcsrfHeader();
		}
		var contextPath = adt.contextPath;

		var myAjax = $.ajax({
			url : contextPath + url,
			headers : header,
			type : type,
			data : (type.toLowerCase() == "get" ? "" : JSON.stringify(data)),
			cache : false,
			contentType : "application/json; charset=utf-8",
			error : function(jqXHR, exception) {
				common.errorMessages(jqXHR, exception);
			}
		});
		return myAjax;
	},

	postAjaxCall : function(url, type, data) {
		var header = this.getcsrfHeader();
		return this.postAjaxCall(url, type, data, header);
	},

	postAjaxCall : function(url, type, data, header) {
		if (!header) {
			header = this.getcsrfHeader();
		}
		var contextPath = adt.contextPath;

		var myAjax = $.ajax({
			url : contextPath + url,
			type : type,
			headers : header,
			data : (type.toLowerCase() == "get" ? "" : JSON.stringify(data)),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			error : function(jqXHR, exception) {
				common.errorMessages(jqXHR, exception);
			}
		});
		return myAjax;
	},

	postAjaxCallObject : function(url, type, data) {
		var header = this.getcsrfHeader();
		return this.postAjaxCallObject(url, type, data, header);
	},

	postAjaxCallObject : function(url, type, data, header) {
		if (!header) {
			header = this.getcsrfHeader();
		}
		var myAjax = $.ajax({
			url : adt.contextPath + url,
			type : type,
			headers : header,
			data : (type.toLowerCase() == "get" ? "" : data),
			dataType : "json",
			error : function(jqXHR, exception) {
				common.errorMessages(jqXHR, exception);
			}
		});

		return myAjax;
	},

	postAjaxCallForFileUpload : function(url, type, data) {
		var header = this.getcsrfHeader();
		return this.postAjaxCallForFileUpload(url, type, data, header);
	},

	postAjaxCallForFileUpload : function(url, type, data, header) {

		var contextPath = adt.contextPath;

		var myAjax = $.ajax({
			url : contextPath + url,
			type : type,
			headers : header,
			data : data,
			async : true,
			cache : false,
			contentType : false,
			processData : false,
			error : function(jqXHR, exception) {
				common.errorMessages(jqXHR, exception);
			}
		});
		return myAjax;
	},

	errorMessages : function(jqXHR, exception) {
		//showAjaxLoader(false); 
		
		console.log("jqXHR", jqXHR, exception);
		var msg = '';
		if (jqXHR.status === 0) {
			console.log("jqXHR", jqXHR);
			msg = "Unable to verify the network";
		} else if (jqXHR.status == 401 || jqXHR.status == 403) {
			msg = "session expired";
			window.location.reload();
		} else if (jqXHR.status == 404) {
			window.location = adt.contextPath + "/errors/404";
			return false;
			msg = 'Requested page not found. [404]';
		} else if (jqXHR.status == 405) {
			msg = 'Request method not allowed [405]';
		} else if (jqXHR.status == 500) {
			alert(jqXHR.statusText);
			console.log(jqXHR.responseText);
			//window.location = adt.appContextPath+"/errors/500";
			return false;
		} else if (exception === 'parsererror') {
			msg = "Parse error";
		} else if (exception === 'timeout') {
			msg = 'Time out error.';
		} else if (exception === 'abort') {
			msg = "Request Aborted";
		} else {
			msg = 'Uncaught error '
					+ jqXHR.responseText;
		}
		alert(msg);
	},
	displayMessages : function(messages) {
		$('#pageErrors').empty();
		$.each(messages, function(key, message) {
			if (message.property) {
				var field = '#' + message.property;
				var errorField = '#' + message.property + '.errors';
				$(field).addClass('errorField');
				$(field).after(
						'<span id="' + message.property
								+ '.errors" class="error">' + message.text
								+ " " + message.code + '</span>');
			} else {

				$('#pageErrors').append(
						'<div class="alert '
								+ (message.code === 'SUCCESS' ? 'alert-success'
										: 'alert-danger') + '">' + message.text
								+ '</div>');
				$('#pageErrors').show();
			}
		});
		setTimeout(function() {
			$("#pageErrors").hide().empty();
		}, 20000);
	},

	showcustommsg : function(sel, msg, append, isBold) {
		if (sel) {
			var s = $(sel), parent = s.parent();

			parent.removeClass('has-error');
			parent.find('span.help-block').remove();

			parent.addClass('has-error');
			if (!isBold) {
				if (!append)
					$(
							'<span class="help-block" style="font-size:12px;clear: both;">'
									+ (msg ? msg : 'field is required')
									+ '</span>').insertAfter(s);
				else
					$(
							'<span class="help-block" style="font-size:12px;clear: both;">'
									+ (msg ? msg : 'field is required')
									+ '</span>').appendTo(parent);
			} else {
				$(
						'<span class="help-block" style="font-size:20px;clear: both;font-weight:bold;">'
								+ (msg ? msg : 'field is required') + '</span>')
						.appendTo(parent);
			}

		}
	},
	clearcustomsg : function(sel) {
		if (sel) {
			var s = $(sel), parent = s.parent();
			parent.removeClass('has-error');
			parent.find('span.help-block').remove();
		}
	},

	isInteger : function(val) {
		if (!val) {
			return false;
		}

		return /^-?[\d]+(?:e-?\d+)?$/i.test(val);
	},
	isNumeric : function(val) {
		if (!val) {
			return false;
		}
		return /^-?[\d.]+(?:e-?\d+)?$/i.test(val);
	},
	showErrorAlert : function(msg) {
		var message = '<div class="alert alert-danger">' + msg + '</div>';
		showAlert(message);
	},
	showSuccessAlert : function(msg) {
		var message = '<div class="alert alert-success">' + msg + '</div>';
		showAlert(message);
	},
	refreshSessionURL : function() {
		var userid = adt.session_userID;
		var url = adt.appContextPath + '/refresh';
		$.get(url);
	},
	refreshSessionURLDummy : function() {
		var sessionInterval = setInterval(function() {
			common.refreshSessionURL()
		}, 4 * 1000);
	},
	refreshSessionURLLoop : function() {
		var sessionInterval = setInterval(function() {
			common.refreshSessionURL()
		}, 15 * 60 * 1000);
	},
	validURL : function(url) {
		var r = new RegExp('^(?:[a-z]+:)?//', 'i');
		return r.test(url);
	},
	addToSelectBox : function(value, insertAfterElement) {
		$("<option>", {
			text : value,
			value : value
		}).insertBefore($(insertAfterElement));
	},
	getTimezoneName : function() {
		timezone = jstz.determine()
		return timezone.name();
	},
	transform : function(value, limit) {
		console.log(value);
		if (value && value.length > limit) {
			return value.substr(0, limit) + '...';
		} else {
			return value;
		}
	},
	getcsrfHeader : function() {
		var token = $("meta[name='_csrf']").attr("content");
		var headerToken = $("meta[name='_csrf_header']").attr("content");
		var headers = {};
		//headers[headerToken] = token;
		return headers;
	},
	populateComboBox : function(ele, value, text) {
		ele.append($("<option>", {
			value : value,
			text : text
		}));
	}

};