(function () { 'use strict;'

	angular
	.module('moduloModule')
	.controller('ModuloEditarController', [
       '$scope','$rootScope', 'AutenticacaoService', '$state', '$stateParams',
       function($scope,$rootScope, AutenticacaoService, $state, $stateParams) {
    	   $scope.modulo  = {
        		   ativo : true
    	   };
    	   if ( $stateParams.modulo != null ){
    		   $scope.modulo = $stateParams.modulo;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.autenticacaoModulo');
    	   };

    	   $scope.fnSalvarModulo = function () {

    		   if($scope.modulo.id != null) {
        		   AutenticacaoService
        		   .alterarModulo($scope.modulo)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
        		   AutenticacaoService
        		   .inserirModulo($scope.modulo)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };
       }]);
})()