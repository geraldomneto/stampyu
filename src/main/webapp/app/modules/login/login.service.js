(function() { 'use strict';

    angular
    .module('loginModule')
    .factory('LoginService', ['$resource','AppService', function($resource,AppService) {

    	var _sUrlBase = AppService.getUrl();

        var _rs = $resource(_sUrlBase + 'rest/login/:action', {}, {
            fazerLogin : {
            	params  : {action: 'login-admin'},
            	method  : 'POST'
            },
            recuperarSenha : {
            	method  : 'POST'
            },
            isAutenticado : {
                method  : 'GET',
                params  : {action: 'isAutenticado'},
                isArray : false
        	}
        });

        return {
        	fazerLogin : function (_oUsuario) {
            	return _rs.fazerLogin(_oUsuario).$promise;
            },
            recuperarSenha : function (_oUsuario) {
            	return _rs.recuperarSenha(_oUsuario).$promise;
            },
            isAutenticado : function () {
            	return _rs.isAutenticado().$promise;
            }
		};
    }]);
})();