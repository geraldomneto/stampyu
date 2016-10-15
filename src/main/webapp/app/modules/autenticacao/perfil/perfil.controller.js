(function () { 'use strict;'

	angular
	.module('perfilModule')
	.controller('PerfilController', [
       '$scope','$rootScope', 'AutenticacaoService', '$state', '$stateParams'
       ,'uiGridConstants', '$filter','$uibModal',
       function($scope,$rootScope, AutenticacaoService, $state, $stateParams
    		   ,uiGridConstants,$filter,$uibModal) {

           $scope.gridOptions = {
               enableSorting        : true,
               paginationPageSizes  : [10],
               enableColumnResizing : true,
               columnDefs           : [
					{
					    name             : '.',
					    width            : 35,
					    cellClass        : 'text-center',
					    cellTemplate     : '<a href="" ng-click="grid.appScope.fnEditarPerfil(row.entity)"><span class="glyphicon glyphicon-edit"></span></a>',
					    enableSorting    : false,
					    enableColumnMenu : false
					},
                   {
                	   field       : 'nome', 
                	   displayName : 'Nome', 
                	   sort        : {
                		   priority  : 0,
                		   direction : uiGridConstants.ASC
                	   }  
                   },
                   {
                	   field       : 'tipoUsuario.descricao', 
                	   displayName : 'Tipo Usuário'  
                   },/*
                   {
                      	name         : 'Ativo',
                		    cellTemplate : '<div class="ui-grid-cell-contents "> {{row.entity.ativo ? "Sim" : "Não";}}</div>' 
                    },*/
                   {
                	   name             : '..',
                	   width            : 35,
                	   cellClass        : 'text-center',
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirPerfil(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                       enableSorting    : false,
                       enableColumnMenu : false
                   }
               ],
               onRegisterApi : function (gridApi) {
            	   $scope.gridApi = gridApi;
               }
    	   };

           $scope.filterGrid = undefined;

           $scope.fnAplicarFiltro = function () {
        	   $scope.gridOptions.data = $filter('filter')($scope.perfils, $scope.filterGrid, undefined);
           }

           $scope.fnNovoPerfil = function () {
    		   $state.go('app.private.autenticacaoPerfilForm', {funcionalidades: $scope.funcionalidades, tiposUsuario : $scope.tiposUsuario});
    	   };

    	   $scope.fnEditarPerfil = function (_perfil) {
    		   $state.go('app.private.autenticacaoPerfilForm', {perfil: _perfil, funcionalidades: $scope.funcionalidades, tiposUsuario : $scope.tiposUsuario});
    	   };

    	   $scope.fnBuscarPerfil = function () {

    		   AutenticacaoService
    		   .buscarFuncionalidade()
    		   .then(function(_oReturn) {
    			   $scope.funcionalidades = _oReturn.data;
    		   });

    		   AutenticacaoService
    		   .tipoUsuario()
    		   .then(function(_oReturn) {
    			   $scope.tiposUsuario = _oReturn.data;
    		   });

    		   AutenticacaoService
    		   .buscarPerfil()
    		   .then(function(_oReturn) {
    			   $scope.perfils = _oReturn.data;
    			   $scope.gridOptions.data = _oReturn.data;
    		   });
    	   };

    	   $scope.fnExcluirPerfil = function (_oPerfil) {

    		   $uibModal
    		   .open({
    			    controller  : 'ModalCtrl',
    			    templateUrl : 'modal-template.html',
    			    resolve     : {
    			        oObjeto: function () {
    			          return _oPerfil;
    			        }
			        }
			    })
			    .result.then(function (_oModalRetorno) {

			        if (_oModalRetorno.bExcluido) {
			    	   var index = $scope.perfis.indexOf(_oPerfil);
			    	   $scope.perfis.splice(index, 1);
			        }

			    }, function () {
			        console.info('Adeus modal: ' + new Date());
			    });
    	   };
       }])
       .controller('ModalCtrl', 
           ['$scope','$uibModalInstance','oObjeto','AutenticacaoService',
           function ($scope, $uibModalInstance,oObjeto,AutenticacaoService) {

    	   $scope.oModalRetorno = {
    		  bExcluido : false
           };

          $scope.fnModalOk = function () {

        	  AutenticacaoService
		       .deletarPerfil(oObjeto.id)
		       .then(function(_oReturn) {

	    	     $scope.oModalRetorno.bExcluido = true;
	    	     $uibModalInstance.close($scope.oModalRetorno);

		       }, function (_oErroRetorno) {
            	  $uibModalInstance.close($scope.oModalRetorno); 
              });
		  };

		  $scope.fnModalCancel = function () {
		    $uibModalInstance.dismiss('cancel');
		  };
       }]);
})()
