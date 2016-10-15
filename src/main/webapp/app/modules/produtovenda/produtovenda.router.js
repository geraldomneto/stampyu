(function () { 'use strict';

	angular
	.module('produtoVendaModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.produtovenda', {
            url         : '/produto-venda',
            controller  : 'ProdutoVendaController',
            templateUrl : './app/modules/produtovenda/produtovenda.view.html'
        })
        .state('app.private.produtovendaEditar', {
            url         : '/produto-venda-editar',
            params      : { produtovenda : undefined },
            controller  : 'ProdutoVendaEditarController',
            templateUrl : './app/modules/produtovenda/produtovenda-editar.view.html'
        });

	}]);
})()
