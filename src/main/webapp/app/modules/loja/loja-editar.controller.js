(function () { 'use strict';

	angular
	.module('lojaModule')
	.controller('LojaEditarController', [
       '$scope','$rootScope','LojaService','$state','$stateParams',
       function($scope,$rootScope,LojaService,$state,$stateParams) {

    	   if ($stateParams.loja) {
    		   $scope.loja = $stateParams.loja;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.loja');
    	   };

    	   $scope.fnSalvar = function () {

    		   if($scope.loja.id != null) {
    			   LojaService
        		   .alterar($scope.loja)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   LojaService
        		   .inserir($scope.loja)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };
       }]);
})()