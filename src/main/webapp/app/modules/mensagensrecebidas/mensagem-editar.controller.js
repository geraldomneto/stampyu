(function () { 'use strict;'

	angular
	.module('mensagemModule')
	.controller('MensagemEditarController', [
       '$scope','$rootScope','MensagemService','$state','$stateParams','FabricanteService','RepresentanteService','growl',
       function($scope,$rootScope,MensagemService,$state,$stateParams,FabricanteService, RepresentanteService,growl) {

    	   $scope.mensagem = {
    			   representantes : []
    	   };
	       	FabricanteService
			.buscar().then(function(_oReturn) {
					$scope.aFabricantes = _oReturn.data;
			});
	    	
	    	$scope.fnBuscarRepresentante = function() {
	    		var filtro = {};
	    		filtro.fabricanteId = $scope.fabricante.id;
	    		RepresentanteService
	    		.buscarLight(filtro).then(function(_oReturn) {
	    				$scope.aRepresentantes = _oReturn.data;
	    		});	
	    	}
	    	
    	   if ($stateParams.mensagem) {
    		   $scope.mensagem = $stateParams.mensagem;
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.mensagem');
    	   };

    	   $scope.fnSalvar = function () {

    		   if($scope.mensagem.representantes.length == 0) {
    			   growl.warning('Informe pelo menos um representante para enviar mensagem.',{title: 'Atenção!'});
    			   return;
    		   }
    		   
	   		    angular.forEach($scope.mensagem.representantes, function(value, key) {
			    	value.listIdProduto = undefined;
		    	});    		   
	   		   $scope.mensagem.usuario = $rootScope.usuarioLogado;
	   		   
    		   if($scope.mensagem.id != null) {
    			   MensagemService
        		   .alterar($scope.mensagem)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
    			   MensagemService
        		   .inserir($scope.mensagem)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };
    	   
    	   
    	   $scope.gridRepresentantesOptions = {
                   data       : 'mensagem.representantes',
                   columnDefs : [{
                       field       : 'nome',
                       displayName : 'Nome'
                     },{
                       name             : '..',
                       width            : 35,
                       cellClass        : 'text-center',
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirRepresentante(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                       enableSorting    : false,
                       enableColumnMenu : false
                   }]
                };
    	   
    	   $scope.fnExcluirRepresentante = function (_oObj) {
    		   if (_oObj) {
    			   var index = $scope.mensagem.representantes.indexOf(_oObj);
    			   $scope.mensagem.representantes.splice(index, 1);
    		   }
    	   };
    	   
    	   $scope.fnAddRepresentante = function () {
    		   if( $scope.representante != undefined ){
    			   if(_validateAddRepresentante($scope.representante)) {
    				   $scope.mensagem.representantes.push($scope.representante);
    			   }
    		   }
    	   };
    	   
    	   $scope.fnAddByFabricante = function() {
    		   angular.forEach($scope.aRepresentantes, function(value, key) {
    			   if(_validateAddRepresentante(value)) {
    				   $scope.mensagem.representantes.push(value);
    			   }
    		    });
    		   
    	   }
    	   
    	   $scope.fnEnviarTodos = function() {
    		   if($scope.todos){
    			   RepresentanteService
    			   .buscarLight().then(function(_oReturn) {
    				   $scope.mensagem.representantes = _oReturn.data;
    			   });	
    		   } else {
    			   $scope.mensagem.representantes = [];
    		   }
    	   }
    	   
    	   
    	   var _validateAddRepresentante = function(representante) {
    		    var canAdd = true;
    		    angular.forEach($scope.mensagem.representantes, function(value, key) {
    		    	if(value.id === representante.id) {
    		    		canAdd = false;
    		    		return;
    		    	}
   		    	});
    		    return canAdd;
    		    
    	   }
    	   
       }]);
})()