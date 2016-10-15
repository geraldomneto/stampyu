(function () { 'use strict';

	angular
	.module('medicoModule')
	.controller('MedicoEditarController', [
       '$scope','$rootScope','MedicoService','$state','$stateParams',
       function($scope,$rootScope,MedicoService,$state,$stateParams) {

    	   if ($stateParams.medico) {
    		   $scope.medico = $stateParams.medico;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.medico');
    	   };

    	   $scope.fnSalvar = function () {

    		   if($scope.medico.id != null) {
    			   MedicoService
        		   .alterar($scope.medico)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   MedicoService
        		   .inserir($scope.medico)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };
       }]);
})()