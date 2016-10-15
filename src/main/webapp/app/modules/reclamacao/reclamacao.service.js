(function() { 'use strict';

    angular
    .module('reclamacaoModule')
    .factory('ReclamacaoService', ['$resource', 'AppService', function($resource,AppService) {

    	var _rs = $resource( AppService.getUrl() + 'rest/reclamacao/:id',{},{
            'update': { method:'PUT' }
        });
    	var _rsCount = $resource( AppService.getUrl() + 'rest/reclamacao/count',{},{
            'update': { method:'PUT' }
        });

        return {
        	buscar : function(_oFiltros) {
        		return _rs.get(_oFiltros).$promise;
        	},

        	buscarReclamacao : function(_id) {
        		return _rs.get({id:_id}).$promise;
        	},
        	count : function() {
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
    	    }
		};
    }]);

})();
