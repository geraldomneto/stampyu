(function() { 'use strict';

    angular
    .module('appMain')
    .directive('autoComplete', [ function () {
    	return {
    		scope : {},
    		link : function (scope, element) {
//    			element.select2({
////    				placeholder: "Selecione...",
////    				allowClear: true
//    			});
    		}
    	}
    }]);

})();