(function() { 'use strict';

    angular
    .module('produtoVendaModule')
    .factory('ProdutoVendaService', ['$resource', 'AppService', function($resource,AppService) {

    	var _rs = $resource( AppService.getUrl() + 'rest/produto-venda/:id',{},{
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
    	    },
    	    buscarDataSourceGraficoVendas : function() {
    	    	return _rs.get({id:"grafico-vendas"}).$promise;
    	    },
    	    buscarDataSourceGraficoTopProdutosVendas : function() {
    	    	return _rs.get({id:"grafico-top-produtos-vendas"}).$promise;
    	    },
    	    buscarDataSourceGraficoTopRepresentantes : function() {
    	    	return _rs.get({id:"grafico-top-representantes"}).$promise;
    	    },
    	    buscarDataSourceGraficoTopMedicos : function() {
    	    	return _rs.get({id:"grafico-top-medicos-vendas"}).$promise;
    	    }
    	    
		};
    }]);

})();
