(function () { 'use strict;'

	angular
	.module('loginModule')
	.controller('LoginController', [
          '$scope', '$rootScope', 'LoginService', '$state','$cookies',
          function($scope, $rootScope, LoginService, $state,$cookies) {

       $scope.usuario = null;
       $scope.isLogado = false;
       $scope.infoMessage = "";
       
       $scope.getUsuarioLogado = function () {
    	   return $scope.usuario;
       };

       $scope.fnLogar = function () {

            if($scope.usuario == null 
                    || $scope.usuario.login == null 
                    || $scope.usuario.senha == null) {

                return;
            }

            LoginService
            .fazerLogin($scope.usuario)
            .then(function(_oReturn) {

                $scope.usuario = _oReturn.data;
                $rootScope.usuarioLogado = _oReturn.data;
                $cookies.putObject('usuarioLogado', _oReturn.data);
                $state.go('app.private.home');
		   });
       };

       $scope.fnRecuperarSenha = function () {
		    if($scope.email == null) {
		    	return;
		    }
	   }
       
       $scope.fnAtualizarSenha = function () {
		    if($scope.senha == null || $scope.novaSenha) {
		    	return;
		    }
	   }

    }]);
})()