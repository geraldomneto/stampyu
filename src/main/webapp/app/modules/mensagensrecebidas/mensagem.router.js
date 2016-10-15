(function () { 'use strict;'

	angular
	.module('mensagemModule')
	.config(['$stateProvider', function($stateProvider) {

		$stateProvider
		.state('app.private.mensagem', {
            url         : '/mensagens',
            controller  : 'MensagemController',
            templateUrl : './app/modules/mensagensrecebidas/mensagem.view.html'
        })
        .state('app.private.mensagemEditar', {
            url         : '/mensagens-editar',
            params      : { mensagem : undefined },
            controller  : 'MensagemEditarController',
            templateUrl : './app/modules/mensagensrecebidas/mensagem-editar.view.html'
        });

	}]);
})()
