(function () { 'use strict;'

	angular
	.module('motivoReclamacaoModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.motivoReclamacao', {
            url         : '/motivo-reclamacao',
            controller  : 'MotivoReclamacaoController',
            templateUrl : './app/modules/motivoreclamacao/motivo-reclamacao.view.html'
        })
        .state('app.private.motivoReclamacaoEditar', {
            url         : '/motivo-reclamacao-editar',
            params      : { motivoReclamacao : undefined },
            controller  : 'MotivoReclamacaoEditarController',
            templateUrl : './app/modules/motivoreclamacao/motivo-reclamacao-editar.view.html'
        });

	}]);
})()
