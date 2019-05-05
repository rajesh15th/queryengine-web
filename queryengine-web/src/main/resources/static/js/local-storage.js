var queryLocalStorage = {
		getItem : function(key) {
			return localStorage[key];
		},
		setItem: function(key, value) {
			localStorage.setItem(key,value);
		},
		removeItem : function(key) {
			return localStorage.removeItem(key);
		}, 
}


localStorage.setItem("lastname", "Smith123");


if (typeof(Storage) !== "undefined") {
	
} else {
  alert("Browser not supporting local storage");
}