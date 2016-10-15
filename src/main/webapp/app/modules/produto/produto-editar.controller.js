(function () { 'use strict;'

	angular
	.module('produtoModule')
	.controller('ProdutoEditarController', [
       '$scope','$rootScope','ProdutoService','$state','$stateParams','FabricanteService','$base64',
       function($scope,$rootScope,ProdutoService,$state,$stateParams,FabricanteService,$base64) {
    	   FabricanteService
    	   .buscar()
    	   .then(function(_oReturn) {
   	           $scope.aFabricantes = _oReturn.data;
   		   });
    	   
    	   $scope.arquivoSelecionado = undefined;
    	   
    	   if ($stateParams.produto) {
    		   $scope.produto = $stateParams.produto;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.produto');
    	   };

    	   $scope.fnSalvar = function () {
    		   if (!!$scope.arquivoSelecionado) {
    			   var reader = new FileReader();
    		       reader.onload = function(readerEvt) {
    		    	   $scope.produto.foto = "data:" + $scope.arquivoSelecionado.type + ";base64," + $base64.encode(readerEvt.target.result);
    		    	   _fnInserirOuAlterar();
    		       };
    		       reader.readAsBinaryString($scope.arquivoSelecionado);
    		       return;
    		   }
	    	   _fnInserirOuAlterar();

    	   };
    	   
    	   var _fnInserirOuAlterar = function () {
    		   if($scope.produto.id != null) {
    			   ProdutoService
        		   .alterar($scope.produto)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   ProdutoService
        		   .inserir($scope.produto)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   }
       }]);
})()