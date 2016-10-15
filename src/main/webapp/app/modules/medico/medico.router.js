(function () { 'use strict';

	angular
	.module('medicoModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.medico', {
            url         : '/medico',
            controller  : 'MedicoController',
            templateUrl : './app/modules/medico/medico.view.html'
        })
        .state('app.private.medicoEditar', {
            url         : '/medico-editar',
            params      : { medico : undefined },
            controller  : 'MedicoEditarController',
            templateUrl : './app/modules/medico/medico-editar.view.html'
        });

	}]);
})()
