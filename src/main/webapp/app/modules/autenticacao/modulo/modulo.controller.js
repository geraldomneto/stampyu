(function () { 'use strict;'

	angular
	.module('moduloModule')
	.controller('ModuloController', [
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
					    cellTemplate     : '<a href="" ng-click="grid.appScope.fnEditarModulo(row.entity)"><span class="glyphicon glyphicon-edit"></span></a>',
					    enableSorting    : false,
					    enableColumnMenu : false
					},
                   {
                	   field       : 'ordemExibicao', 
                	   displayName : 'Ordem de Exibição',
                	   sort        : {
             	          priority  : 0,
             	          direction : uiGridConstants.ASC
             	       }  
                   },
                   {
                	   field       : 'nome', 
                	   displayName : 'Nome', 
                   },
                   {
                      	name         : 'Ativo',
                		    cellTemplate : '<div class="ui-grid-cell-contents "> {{row.entity.ativo ? "Sim" : "Não";}}</div>' 
                      },{
                	   name             : '..',
                	   width            : 35,
                	   cellClass        : 'text-center', //data-toggle="modal" data-target="#removeModal"
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirModulo(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
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

    	   $scope.fnBuscarModulo = function () {
    		   AutenticacaoService
    		   .buscarModulo()
    		   .then(function(_oReturn) {
    			   $scope.modulos = _oReturn.data;
    			   $scope.gridOptions.data = _oReturn.data;
    		   });
    	   };

    	   $scope.fnExcluirModulo = function (_oModulo) {

    		   $uibModal
    		   .open({
    			    controller  : 'ModuloModalCtrl',
    			    templateUrl : 'modulo-modal-template.html',
    			    resolve     : {
    			        oModulo: function () {
    			          return _oModulo;
    			        }
			        }
			    })
			    .result.then(function (_oModalRetorno) {

			        if (_oModalRetorno.bExcluido) {
			        	var index = $scope.modulos.indexOf(_oModalRetorno);
			    	   $scope.modulos.splice(index, 1);
			        }

			    }, function () {
			       console.info('Adeus modal do Modulo: ' + new Date());
			    });
    	   };

    	   $scope.fnEditarModulo = function (_modulo) {
    		   $state.go('app.private.autenticacaoModuloForm', {modulo: _modulo});
    	   };

    	   $scope.fnNovoModulo = function () {
    		   $state.go('app.private.autenticacaoModuloForm');
    	   };
       }])
       .controller('ModuloModalCtrl', 
           ['$scope','$uibModalInstance','oModulo','AutenticacaoService',
           function ($scope, $uibModalInstance,oModulo,AutenticacaoService) {

    	   $scope.oModalRetorno = {
    		  bExcluido : false
           };

          $scope.fnModalOk = function () {

              AutenticacaoService
              .deletarModulo(oModulo.id)
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
