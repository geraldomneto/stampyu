(function() { 'use strict';

    angular
    .module('representanteModule')
    .factory('RepresentanteService', ['$resource', 'AppService', function($resource,AppService) {

    	var _rs = $resource( AppService.getUrl() + 'rest/representante/:action/:id',{},{
            'update': { method:'PUT' },
            'trocarSenha' :{
            	method:'POST',
            	params: {action:'trocar-senha-representante' }
            }
        });
    	var _rsCount = $resource( AppService.getUrl() + 'rest/representante/count',{},{
            'update': { method:'PUT' }
        });
    	
    	var _rsWeb = $resource( AppService.getUrl() + 'rest/representante/web',{},{});
    	
    	

        return {
        	buscar : function(_oFiltros) {
        		return _rsWeb.get(_oFiltros).$promise;
        	},
        	buscarRepresentante : function(_id) {
        		return _rs.get({id:_id}).$promise;
        	},
        	buscarLight : function(_oFiltros) {
        		return _rs.get(_oFiltros).$promise;
        	},
        	count: function() {
        		return _rsCount.get().$promise;
        	},
    	    inserir: function(_oObj) {
    	    	return _rs.save(_oObj).$promise;
    	    },
    	    alterar: function(_oObj) {
    	    	return _rs.update(_oObj).$promise;
    	    },
    	    excluir : function(_id) {
    	    	return _rs.delete({id:_id}).$promise;
    	    },
    	    trocarSenha : function(_oUsuario) {
        		return _rs.trocarSenha(_oUsuario).$promise;
        	}
		};
    }]);

})();
