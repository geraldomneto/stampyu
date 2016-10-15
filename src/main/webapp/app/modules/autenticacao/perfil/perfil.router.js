(function () { 'use strict;'

	angular
    .module('perfilModule')
	.config(['$stateProvider' , function($stateProvider) {

		$stateProvider
		.state('app.private.autenticacaoPerfil', {
            url         : '/perfil',
            controller  : 'PerfilController',
        	templateUrl : './app/modules/autenticacao/perfil/perfil.view.html'
        })
        .state('app.private.autenticacaoPerfilForm', {
            url         : '/form-perfil',
            params      : {perfil: undefined, funcionalidades : undefined, tiposUsuario : undefined },
            controller  : 'PerfilEditarController',
            templateUrl : './app/modules/autenticacao/perfil/form-perfil.view.html'
        });
	}]);
})()