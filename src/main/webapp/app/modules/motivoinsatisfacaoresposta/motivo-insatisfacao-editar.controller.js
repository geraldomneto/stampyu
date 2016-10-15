(function () { 'use strict;'

	angular
	.module('motivoInsatisfacaoModule')
	.controller('MotivoInsatisfacaoEditarController', [
       '$scope','$rootScope','MotivoInsatisfacaoService','$state','$stateParams',
       function($scope,$rootScope,MotivoInsatisfacaoService,$state,$stateParams) {

    	   if ($stateParams.motivoInstatisfacao) {
    		   $scope.motivoInstatisfacao = $stateParams.motivoInstatisfacao;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.motivoInsatisfacao');
    	   };

    	   $scope.fnSalvar = function () {

    		   if($scope.motivoInstatisfacao.id != null) {
    			   MotivoInsatisfacaoService
        		   .alterar($scope.motivoInstatisfacao)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   MotivoInsatisfacaoService
        		   .inserir($scope.motivoInstatisfacao)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };
       }]);
})()