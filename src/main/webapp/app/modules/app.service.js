(function() { 'use strict';

    angular
    .module('appMain')
    .factory('AppService', ['$resource','$location', function ($resource,$location) {

    	return {
    		getUrl : function () {
    			return $location.absUrl().substr(0, $location.absUrl().lastIndexOf("#"));
    		}
    	}; 
    }]);

})();
