(function () { 'use strict;'

	angular
	.module('reclamacaoModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.reclamacao', {
            url         : '/reclamacao',
            controller  : 'ReclamacaoController',
            templateUrl : './app/modules/reclamacao/reclamacao.view.html'
        })
        .state('app.private.reclamacaoEditar', {
            url         : '/reclamacao-editar',
            params      : { reclamacao : undefined },
            controller  : 'ReclamacaoEditarController',
            templateUrl : './app/modules/reclamacao/reclamacao-editar.view.html'
        });

	}]);
})()
