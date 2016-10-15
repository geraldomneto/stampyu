(function () { 'use strict;'

	angular
	.module('motivoInsatisfacaoModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.motivoInsatisfacao', {
            url         : '/motivo-insatisfacao',
            controller  : 'MotivoInsatisfacaoController',
            templateUrl : './app/modules/motivoinsatisfacaoresposta/motivo-insatisfacao.view.html'
        })
        .state('app.private.motivoInsatisfacaoEditar', {
            url         : '/motivo-insatisfacao-editar',
            params      : { motivoInsatisfacao : undefined },
            controller  : 'MotivoInsatisfacaoEditarController',
            templateUrl : './app/modules/motivoinsatisfacaoresposta/motivo-insatisfacao-editar.view.html'
        });

	}]);
})()
