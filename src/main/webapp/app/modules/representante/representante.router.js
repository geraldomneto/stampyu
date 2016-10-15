(function () { 'use strict;'

	angular
	.module('representanteModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.representante', {
            url         : '/representante',
            controller  : 'RepresentanteController',
            templateUrl : './app/modules/representante/representante.view.html'
        })
        .state('app.private.representanteEditar', {
            url         : '/representante-editar',
            params      : { representante : undefined },
            controller  : 'RepresentanteEditarController',
            templateUrl : './app/modules/representante/representante-editar.view.html'
        });

	}]);
})()
