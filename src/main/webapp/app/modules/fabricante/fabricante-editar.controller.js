(function () { 'use strict;'

	angular
	.module('fabricanteModule')
	.controller('FabricanteEditarController', [
       '$scope','$rootScope','FabricanteService','$state','$stateParams','$base64',
       function($scope,$rootScope,FabricanteService,$state,$stateParams,$base64) {

    	   $scope.arquivoSelecionado = undefined;
    	   
    	   if ($stateParams.fabricante) {
    		   $scope.fabricante = $stateParams.fabricante;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.fabricante');
    	   };

    	   $scope.fnSalvar = function () {
    		   if (!!$scope.arquivoSelecionado) {
    			   var reader = new FileReader();
    		       reader.onload = function(readerEvt) {
    		    	   $scope.fabricante.logo = "data:" + $scope.arquivoSelecionado.type + ";base64," + $base64.encode(readerEvt.target.result);
    		    	   _fnInserirOuAlterar();
    		       };
    		       reader.readAsBinaryString($scope.arquivoSelecionado);
    		       return;
    		   }
	    	   _fnInserirOuAlterar();
    	   };
    	   
    	   var _fnInserirOuAlterar = function () {
    		   if($scope.fabricante.id != null) {
    			   FabricanteService
        		   .alterar($scope.fabricante)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   FabricanteService
        		   .inserir($scope.fabricante)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   }
       }]);
})()