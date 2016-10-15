(function () { 'use strict;'

	angular
	.module('produtoModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.produto', {
            url         : '/produto',
            controller  : 'ProdutoController',
            templateUrl : './app/modules/produto/produto.view.html'
        })
        .state('app.private.produtoEditar', {
            url         : '/produto-editar',
            params      : { produto : undefined },
            controller  : 'ProdutoEditarController',
            templateUrl : './app/modules/produto/produto-editar.view.html'
        });

	}]);
})()
