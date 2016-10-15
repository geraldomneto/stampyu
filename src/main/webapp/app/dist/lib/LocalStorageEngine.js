/**
 * 
 */
function LocalStorageEngine() {

	function JSONtoString(object) {
		return JSON.stringify(object);
	}

	this.save = function(name, data) {
		localStorage.setItem(name, JSONtoString(data));
	};

	this.get = function(name) {
		var item = localStorage.getItem(name);
		return JSON.parse(item);
	};

	this.remove = function(name) {
		localStorage.clear(name);
	};
};