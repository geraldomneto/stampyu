(function () { 'use strict';

	angular
	.module('lojaModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.loja', {
            url         : '/loja',
            controller  : 'LojaController',
            templateUrl : './app/modules/loja/loja.view.html'
        })
        .state('app.private.lojaEditar', {
            url         : '/loja-editar',
            params      : { loja : undefined },
            controller  : 'LojaEditarController',
            templateUrl : './app/modules/loja/loja-editar.view.html'
        });

	}]);
})()
