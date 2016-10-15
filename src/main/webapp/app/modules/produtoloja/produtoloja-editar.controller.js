(function () { 'use strict';

	angular
	.module('produtoLojaModule')
	.controller('ProdutoLojaEditarController', [
       '$scope','$rootScope','ProdutoLojaService','$state','$stateParams',
       function($scope,$rootScope,ProdutoLojaService,$state,$stateParams) {

    	   if ($stateParams.produtoloja) {
    		   $scope.produtoloja = $stateParams.produtoloja;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.produtoloja');
    	   };

    	   $scope.fnSalvar = function () {

    		   if($scope.produtoloja.id != null) {
    			   ProdutoLojaService
        		   .alterar($scope.produtoloja)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   ProdutoLojaService
        		   .inserir($scope.produtoloja)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };
       }]);
})()