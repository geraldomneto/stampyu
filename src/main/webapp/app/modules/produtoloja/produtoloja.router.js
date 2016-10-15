(function () { 'use strict';

	angular
	.module('produtoLojaModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.produtoloja', {
            url         : '/produto-loja',
            controller  : 'ProdutoLojaController',
            templateUrl : './app/modules/produtoloja/produtoloja.view.html'
        })
        .state('app.private.produtolojaEditar', {
            url         : '/produto-loja-editar',
            params      : { produtoloja : undefined },
            controller  : 'ProdutoLojaEditarController',
            templateUrl : './app/modules/produtoloja/produtoloja-editar.view.html'
        });

	}]);
})()
