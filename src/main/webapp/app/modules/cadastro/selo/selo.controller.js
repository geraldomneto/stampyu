(function () { 'use strict;'

	angular
	.module('seloModule')
	.controller('SeloController', [
       '$scope','$rootScope','SeloService','$state','$stateParams','uiGridConstants', '$filter','$uibModal',
       function($scope,$rootScope,SeloService,$state,$stateParams,uiGridConstants,$filter,$uibModal) {

    	   
           $scope.gridOptions = {
               enableSorting        : true,
               enableGridMenu       : true,
               paginationPageSizes  : [10],
               enableColumnResizing : true,
               exporterMenuVisibleData     : false,
               exporterHeaderFilterUseName : false,
               columnDefs           : [
					{
					    name             : '.',
					    width            : 35,
					    cellClass        : 'text-center',
					    cellTemplate     : '<a href="" ng-click="grid.appScope.fnEditarSelo(row.entity)"><span class="glyphicon glyphicon-edit"></span></a>',
					    enableSorting    : false,
					    enableColumnMenu : false,
					    exporterSuppressExport : true
					},{
					   field       : 'id',
                 	   displayName : 'ID'
                    },{
                	   field       : 'numeroSerie', 
                	   displayName : 'Número de Serie'
                    },{
                 	   field       : 'titulo', 
                 	   displayName : 'Título',
                 	   sort        : {
                 		   priority  : 0,
                 		   direction : uiGridConstants.ASC
                 	   }
                     },{
                	   name             : '..',
                	   width            : 35,
                	   cellClass        : 'text-center',
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirSelo(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                       enableSorting    : false,
                       enableColumnMenu : false,
                       exporterSuppressExport : true
                    }
               ],
               onRegisterApi : function (gridApi) {
            	   $scope.gridApi = gridApi;
               }
    	   };

           $scope.filtro = {};
           $scope.filterGrid = undefined;

           $scope.fnAplicarFiltro = function () {
        	   $scope.gridOptions.data = $filter('filter')($scope.funcionalidades, $scope.filterGrid, undefined);
           }

           $scope.fnBuscarSelo = function () {
        	   SeloService
		       .buscarSelos($scope.filtro)
		       .then(function(_oReturnSelo) {
			       $scope.aSelos = _oReturnSelo.data;
			       $scope.gridOptions.data = _oReturnSelo.data;
		       });
           };

           $scope.fnBuscarSelo();

           $scope.fnNovoSelo = function () {
    		   $state.go('app.private.seloForm');
    	   };

    	   $scope.fnEditarSelo = function (_oSelo) {
    		   SeloService.getSelo(_oSelo.id).then(function(_oRetorno) { 
    			   _oSelo = _oRetorno.data;
    			   $state.go('app.private.seloForm', {selo: _oSelo});
    		   })
    	   };

    	   $scope.fnExcluirSelo = function (_oSelo) {
    		   $uibModal
    		   .open({
    			    controller  : 'ModalExluirCtrl',
    			    templateUrl : 'modal-exluir-template.html',
    			    resolve     : {
 			           oObjeto: function () {
 			               return {
 			        	       fnCallback : function () {
 			        		        return SeloService.deletarSelo(_oSelo.id);
 			        	       }
  			              }
			           }
                    }
			    })
			    .result.then(function (_oModalRetorno) {

			    	_oModalRetorno.$promise.then(
					   function (_oReturn) {
			    	       var index = $scope.aSelos.indexOf(_oSelo);
			    	       $scope.aSelos.splice(index, 1);
					   }, 
	                   function (_oErroRetorno) { 
	            		 console.error("Erro ao excluir!"); 
	            	   }
	                );

			    }, function () {
			        console.info('Adeus modal: ' + new Date());
			    });
    	   };
       }]);
})()
