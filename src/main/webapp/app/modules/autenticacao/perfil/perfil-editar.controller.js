(function () { 'use strict;'

	angular
	.module('perfilModule')
	.controller('PerfilEditarController', [
       '$scope','$rootScope', 'AutenticacaoService', '$state', '$stateParams',
       function($scope,$rootScope, AutenticacaoService, $state, $stateParams) {

    	   $scope.perfil  = {
        		   ativo : true,
        		   funcionalidades :[]
    	   };    	   
    	   $scope.tiposUsuario = [];
    	  
    	   if ($stateParams.perfil) {
    		   $scope.perfil = $stateParams.perfil;
    	   }

    	   if ($stateParams.tiposUsuario) {
    		   $scope.tiposUsuario = $stateParams.tiposUsuario;
    	   } else {
    		   AutenticacaoService
    		   .tipoUsuario()
    		   .then(function(_oReturn) {
    			   $scope.tiposUsuario = _oReturn.data;
    		   });
    	   }

    	   if ($stateParams.funcionalidades) {
    		   $scope.funcionalidades = $stateParams.funcionalidades;
    	   } else {
    		   AutenticacaoService
    		   .buscarFuncionalidade()
    		   .then(function(_oReturn) {
    			   $scope.funcionalidades = _oReturn.data;
    		   });
    	   }

    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.autenticacaoPerfil');
    	   };
    	   
    	   var _validateItem = function(list, object) {
	   		    var canAdd = true;
	   		    angular.forEach(list, function(value, key) {
	   		    	if(value.id === object.id) {
	   		    		canAdd = false;
	   		    		return;
	   		    	}
	  		    	});
	   		    return canAdd;
	   		    
	   	   };
   	   
	   	   $scope.fnAddFuncionalidade = function () {
	   		   if ($scope.oFuncionalidadeSelecionado && _validateItem($scope.perfil.funcionalidades, $scope.oFuncionalidadeSelecionado)) {
	   			   $scope.perfil.funcionalidades.push($scope.oFuncionalidadeSelecionado);
	   		   }
	   	   };
   	   
	   	   $scope.fnExcluirFuncionalidade = function (_oObj) {
	   		   if (_oObj) {
	   			   var index = $scope.perfil.funcionalidades.indexOf(_oObj);
	   			   $scope.perfil.funcionalidades.splice(index, 1);
	   		   }
	   	   };
   	   

    	   $scope.gridFuncionalidadesOptions = {
                   data       : 'perfil.funcionalidades',
                   columnDefs : [{
                       field       : 'descricaoResumida',
                       displayName : 'Nome'
                     },{
                       name             : '..',
                       width            : 35,
                       cellClass        : 'text-center',
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirFuncionalidade(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                       enableSorting    : false,
                       enableColumnMenu : false
                   }]
                };
    	   
    	   
    	   $scope.fnSalvarPerfil = function () {
    		   if($scope.perfil.id != null) {
        		   AutenticacaoService
        		   .alterarPerfil($scope.perfil)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
        		   AutenticacaoService
        		   .inserirPerfil($scope.perfil)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };
       }]);
})()