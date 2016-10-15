(function() { 'use strict';

    angular
    .module('motivoReclamacaoModule')
    .factory('MotivoReclamacaoService', ['$resource', 'AppService', function($resource,AppService) {

    	var _rs = $resource( AppService.getUrl() + 'rest/motivo-reclamacao/:id',{},{
            'update': { method:'PUT' }
        });

        return {
        	buscar : function(_oFiltros) {
        		return _rs.get(_oFiltros).$promise;
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
