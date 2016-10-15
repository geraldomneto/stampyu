(function () { 'use strict;'

	angular
	.module('motivoReclamacaoModule')
	.controller('MotivoReclamacaoEditarController', [
       '$scope','$rootScope','MotivoReclamacaoService','$state','$stateParams',
       function($scope,$rootScope,MotivoReclamacaoService,$state,$stateParams) {

    	   if ($stateParams.motivoReclamacao) {
    		   $scope.motivoReclamacao = $stateParams.motivoReclamacao;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.motivoReclamacao');
    	   };

    	   $scope.fnSalvar = function () {

    		   if($scope.motivoReclamacao.id != null) {
    			   MotivoReclamacaoService
        		   .alterar($scope.motivoReclamacao)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   MotivoReclamacaoService
        		   .inserir($scope.motivoReclamacao)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };
       }]);
})()