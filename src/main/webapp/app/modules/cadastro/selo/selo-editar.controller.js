(function () { 'use strict;'

	angular
	.module('seloModule')
	.controller('SeloEditarController', [
       '$scope','$rootScope','SeloService','$state','$stateParams','$interval','$filter','$uibModal','$base64',
       function($scope,$rootScope,SeloService,$state,$stateParams,$interval,$filter,$uibModal,$base64) {

    	   var POG_IMG_PREVIEW = 'data:image/png;base64,';
    	   
    	  if ($stateParams.selo) {
   		   	$scope.selo = $stateParams.selo;
   		   	if($scope.selo.imagem != undefined ) {
   		   		$scope.selo.imagem = POG_IMG_PREVIEW+$scope.selo.imagem;
   		   	}
    	  }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.selo');
    	   };

    	   var _fnInserirOuAlterar = function () {
    		   if($scope.selo.id != null) {
    			   SeloService
        		   .alterarSelo($scope.selo)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   SeloService
        		   .inserirSelo($scope.selo)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };

    	   $scope.fnSalvarSelo = function () {
    		   console.dir($scope.arquivoSelecionado);
    		   if (!!$scope.arquivoSelecionado) {

    			   var reader = new FileReader();
    		       reader.onload = function(readerEvt) {
    		    	   $scope.selo.imagem = $base64.encode(readerEvt.target.result);
    		    	   _fnInserirOuAlterar();
    		       };
    		       reader.readAsBinaryString($scope.arquivoSelecionado);
    		       return;
    		   }

    		   _fnInserirOuAlterar();
    	   };

       }])
})()
