(function () { 'use strict;'

	angular
	.module('usuarioModule')
	.controller('UsuarioEditarController', [
       '$scope','$rootScope', 'AutenticacaoService', '$state', '$stateParams',
       function($scope,$rootScope, AutenticacaoService, $state, $stateParams) {
    	   
    	   $scope.usuario  = {
        		   ativo : true,
        		   perfis :[]
    	   };

		   AutenticacaoService
		   .tipoUsuario()
		   .then(function(_oReturn) {
			   $scope.tiposUsuario = _oReturn.data;
		   });

		   AutenticacaoService
		   .buscarPerfil()
		   .then(function(_oReturn) {
			   $scope.aPerfils = _oReturn.data;
		   });

		   if ($stateParams.usuario) {
    		   $scope.usuario = $stateParams.usuario;
    	   }
		   
    	   var _fnIrParaListagem = function () {
    		   $state.go('app.private.autenticacaoUsuario');
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
    	   
    	   $scope.fnAddPerfil = function () {
    		   if ($scope.oPerfilSelecionado && _validateItem($scope.usuario.perfis, $scope.oPerfilSelecionado)) {
    			   $scope.usuario.perfis.push($scope.oPerfilSelecionado);
    		   }
    	   };
    	   
    	   $scope.fnExcluirPerfil = function (_oObj) {
    		   if (_oObj) {
    			   var index = $scope.usuario.perfis.indexOf(_oObj);
    			   $scope.usuario.perfis.splice(index, 1);
    		   }
    	   };
    	   
    	   
    	   $scope.gridPerfisOptions = {
                   data       : 'usuario.perfis',
                   columnDefs : [{
                       field       : 'nome',
                       displayName : 'Nome'
                     },{
                       name             : '..',
                       width            : 35,
                       cellClass        : 'text-center',
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirPerfil(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                       enableSorting    : false,
                       enableColumnMenu : false
                   }]
                };
    	   

    	   $scope.fnSalvarUsuario = function () {
    		   if($scope.usuario.id != null) {
        		   AutenticacaoService
        		   .alterarUsuario($scope.usuario)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   } else {
        		   AutenticacaoService
        		   .inserirUsuario($scope.usuario)
        		   .then(function(_oReturn) {
        			   _fnIrParaListagem();
        		   });
    		   }
    	   };

       }]);
})()