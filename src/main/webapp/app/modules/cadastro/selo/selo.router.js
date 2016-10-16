(function () { 'use strict;'

	angular
    .module('seloModule')
	.config(['$stateProvider' , function($stateProvider) {

		$stateProvider
		.state('app.private.selo', {
            url         : '/selo',
            controller  : 'SeloController',
        	templateUrl : './app/modules/cadastro/selo/selo.view.html'
        })
        .state('app.private.seloForm', {
            url         : '/selo-form',
            params      : {selo: undefined},
            controller  : 'SeloEditarController',
            templateUrl : './app/modules/cadastro/selo/selo-editar.view.html'
        });
	}]);
})()