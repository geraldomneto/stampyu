(function() { 'use strict';

    angular
    .module('homeModule')
    .factory('HomeService', ['$resource','AppService', function($resource,AppService) {

    	var _sUrlBase = AppService.getUrl();

        var _rs = $resource(_sUrlBase + 'rest/autenticacao/:action', {}, {
            getMenu : {
                method  : 'GET',
                params  : {action: 'menu'},
                isArray : false
            }
        });

        return {
            getMenu : function () {
            	return _rs.getMenu().$promise;
            }
		};
    }]);
})();