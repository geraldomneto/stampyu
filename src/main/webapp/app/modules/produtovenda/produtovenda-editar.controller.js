(function () { 'use strict';

	angular
	.module('produtoVendaModule')
	.controller('ProdutoVendaEditarController', [
       '$scope','$rootScope','ProdutoVendaService','$state','$stateParams',
       function($scope,$rootScope,ProdutoVendaService,$state,$stateParams) {

    	   if ($stateParams.produtovenda) {
    		   $scope.produtovenda = $stateParams.produtovenda;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.produtovenda');
    	   };

    	   $scope.fnSalvar = function () {

    		   if($scope.produtovenda.id != null) {
    			   ProdutoVendaService
        		   .alterar($scope.produtovenda)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   ProdutoVendaService
        		   .inserir($scope.produtovenda)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };
       }]);
})()