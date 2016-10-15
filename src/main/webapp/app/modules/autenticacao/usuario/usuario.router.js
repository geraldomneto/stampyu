(function () { 'use strict;'

	angular
    .module('usuarioModule')
	.config(['$stateProvider' , function($stateProvider) {

		$stateProvider
		.state('app.private.autenticacaoUsuario', {
            url         : '/usuario',
            controller  : 'UsuarioController',
        	templateUrl : './app/modules/autenticacao/usuario/usuario.view.html'
        })
        .state('app.private.autenticacaoUsuarioForm', {
            url         : '/form-usuario',
            params      : {usuario: undefined, perfis : undefined, tiposUsuario : undefined },
            controller  : 'UsuarioEditarController',
            templateUrl : './app/modules/autenticacao/usuario/form-usuario.view.html'
        });
	}]);
})()