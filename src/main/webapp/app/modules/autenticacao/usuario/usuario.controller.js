(function () { 'use strict;'

	angular
	.module('usuarioModule')
	.controller('UsuarioController', [
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
					    cellTemplate     : '<a href="" ng-click="grid.appScope.fnEditarUsuario(row.entity)"><span class="glyphicon glyphicon-edit"></span></a>',
					    enableSorting    : false,
					    enableColumnMenu : false
					},
					{
	                	   field       : 'id', 
	                	   displayName : 'ID'
	                },
					{
	                	   field       : 'login', 
	                	   displayName : 'Login'
	                },{
                	   field       : 'nome', 
                	   displayName : 'Nome', 
                	   sort        : {
                		   priority  : 0,
                		   direction : uiGridConstants.ASC
                	   }  
                    },{
                	   field       : 'tipoUsuario.descricao', 
                	   displayName : 'Tipo Usuário'  
                    },{
                       	name         : 'Ativo',
                        cellTemplate : '<div class="ui-grid-cell-contents "> {{row.entity.ativo ? "Sim" : "Não";}}</div>' 
                    },{
                	   name             : '..',
                	   width            : 35,
                	   cellClass        : 'text-center',
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirUsuario(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
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
        	   $scope.gridOptions.data = $filter('filter')($scope.funcionalidades, $scope.filterGrid, undefined);
           }

    	   $scope.fnNovoUsuario = function () {
    		   $scope.usuario = {};
    		   $state.go('app.private.autenticacaoUsuarioForm');
    	   };

    	   $scope.fnEditarUsuario = function (_usuario) {
    		   $state.go('app.private.autenticacaoUsuarioForm', {usuario: _usuario});
    	   };

    	   $scope.fnBuscarUsuario = function () {

    		   AutenticacaoService
    		   .buscarUsuarioAdmin($scope.filtro)
    		   .then(function(_oReturn) {
    			   $scope.usuarios = _oReturn.data;
    			   $scope.gridOptions.data = _oReturn.data;
    		   });
    	   };

    	   $scope.fnExcluirUsuario = function (_oUsuario) {

    		   $uibModal
    		   .open({
    			    controller  : 'ModalCtrl',
    			    templateUrl : 'modal-template.html',
    			    resolve     : {
    			        oObjeto: function () {
    			          return _oUsuario;
    			        }
			        }
			    })
			    .result.then(function (_oModalRetorno) {

			        if (_oModalRetorno.bExcluido) {
			    	   var index = $scope.usuarios.indexOf(_oUsuario);
			    	   $scope.usuarios.splice(index, 1);  
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
		       .deletarUsuario(oObjeto.id)
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
