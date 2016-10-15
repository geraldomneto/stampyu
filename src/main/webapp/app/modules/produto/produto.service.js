(function() { 'use strict';

    angular
    .module('produtoModule')
    .factory('ProdutoService', ['$resource', 'AppService', function($resource,AppService) {

    	var _rs = $resource( AppService.getUrl() + 'rest/produto/:id',{},{
            'update': { method:'PUT' }
        });
    	var _rsCount = $resource( AppService.getUrl() + 'rest/produto/count',{},{
            'update': { method:'PUT' }
        });

        return {
        	buscar : function(_oFiltros) {
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
    	    }
		};
    }]);

})();
