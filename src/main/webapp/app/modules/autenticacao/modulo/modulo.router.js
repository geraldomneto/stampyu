(function () { 'use strict;'

	angular
    .module('moduloModule')
	.config(['$stateProvider' , function($stateProvider) {

		$stateProvider
        .state('app.private.autenticacaoModulo', {
            url         : '/modulo',
            controller  : 'ModuloController',
        	templateUrl : './app/modules/autenticacao/modulo/modulo.view.html'
        })
        .state('app.private.autenticacaoModuloForm', {
            url         : '/form-modulo',
            params      : {modulo: undefined},
            controller  : 'ModuloEditarController',
        	templateUrl : './app/modules/autenticacao/modulo/form-modulo.view.html'
        });
	}]);
})()