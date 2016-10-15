(function () { 'use strict;'

	angular
	.module('fabricanteModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.fabricante', {
            url         : '/fabricante',
            controller  : 'FabricanteController',
            templateUrl : './app/modules/fabricante/fabricante.view.html'
        })
        .state('app.private.fabricanteEditar', {
            url         : '/fabricante-editar',
            params      : { fabricante : undefined },
            controller  : 'FabricanteEditarController',
            templateUrl : './app/modules/fabricante/fabricante-editar.view.html'
        });

	}]);
})()
