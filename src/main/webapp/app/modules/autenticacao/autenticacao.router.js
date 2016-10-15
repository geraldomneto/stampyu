(function () { 'use strict;'

	angular
    .module('autenticacaoModule')
	.config(['$stateProvider' ,  
	         function($stateProvider) {

		$stateProvider
		.state('app.private.funcionalidade', {
            url         : '/funcionalidade',
            params      : {modulos:undefined},
            controller  : 'AutenticacaoController',
            templateUrl : './app/modules/autenticacao/funcionalidade.view.html'
        })
        .state('app.private.funcionalidadeForm', {
            url         : '/form-funcionalidade',
            params      : {funcionalidade: undefined, modulos:undefined},
            controller  : 'AutenticacaoController',
            templateUrl : './app/modules/autenticacao/form-funcionalidade.view.html'
        });

	}]);
})()