(function () { 'use strict;'

	angular
	.module('autenticacaoModule')
	.controller('AutenticacaoController', [
       '$scope','$rootScope', 'AutenticacaoService', '$state', '$stateParams'
       , 'uiGridConstants', '$filter','$uibModal',
       function($scope,$rootScope, AutenticacaoService, $state, $stateParams
    		   , uiGridConstants,$filter,$uibModal) {

           $scope.gridOptions = {
               enableSorting        : true,
               paginationPageSizes  : [10],
               enableColumnResizing : true,
               columnDefs           : [
					{
					    name             : '.',
					    width            : 35,
					    cellClass        : 'text-center',
					    cellTemplate     : '<a href="" ng-click="grid.appScope.fnEditarFuncionalidade(row.entity)"><span class="glyphicon glyphicon-edit"></span></a>',
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
                	   field       : 'descricaoResumida', 
                	   displayName : 'Descrição Resumida', 
                   },
                   {
                	   field       : 'modulo.nome', 
                	   displayName : 'Modulo'
                   },
                   {
                      	name         : 'Ativo',
                		    cellTemplate : '<div class="ui-grid-cell-contents "> {{row.entity.ativo ? "Sim" : "Não";}}</div>' 
                      },{
                	   name             : '..',
                	   width            : 35,
                	   cellClass        : 'text-center',
                       cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluirFuncionalidade(row.entity)" data-toggle="modal" data-target="#removeModal"><span class="glyphicon glyphicon-remove"></span></a>',
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

    	   $scope.dtInstance = {};
    	   $scope.reloadData = function () {
    	        var resetPaging = false;
    	        $scope.dtInstance.reloadData(function (json) {
        	        console.info(json);
        	    }, resetPaging);
    	    };

    	   $scope.funcionalidades = [];
    	   if ( $stateParams.funcionalidades != null ){
    		   $scope.funcionalidades = $stateParams.funcionalidades;
    	   }

    	   $scope.usuarios = [];
    	   $scope.tipoUsuario = [];

    	   $scope.funcionalidade = {};
    	   if ( $stateParams.funcionalidade != null ){
    		   $scope.funcionalidade = $stateParams.funcionalidade;
    	   }

    	   $scope.usuario = {};
    	   if ( $stateParams.usuario != null ){
    		   $scope.usuario = $stateParams.usuario;
    	   }

    	   $scope.filtro = {};

    	   $scope.fnGetTipoUsuario = function () {
    		   AutenticacaoService
    		   .tipoUsuario()
    		   .then(function(_oReturn) {
    			   $scope.tipoUsuario = _oReturn.data;
    		   });
    	   }
    	   $scope.fnGetTipoUsuario();

    	   /**
    	    * Funcionalidade
    	    */
    	   $scope.modulos = [];

    	   if ( $stateParams.modulos != null ){
    		   $scope.modulos = $stateParams.modulos;
    	   }

    	   $scope.fnNovoFuncionalidade = function () {
    		   $state.go('app.private.funcionalidadeForm', {modulos:$scope.modulos});
    	   };

    	   $scope.fnBuscarFuncionalidade = function () {

    		   AutenticacaoService
    		   .buscarModulo()
    		   .then(function(_oReturn) {
    			   $scope.modulos = _oReturn.data;
    		   });

    		   AutenticacaoService
    		   .buscarFuncionalidade()
    		   .then(function(_oReturn) {

    			   $scope.gridOptions.data = _oReturn.data;
    			   $scope.funcionalidades = _oReturn.data;
    		   });
    	   };

    	   /**
    	    * 
    	    */
    	   $scope.fnExcluirFuncionalidade = function (_oFuncionalidade) {

    		   $uibModal
    		   .open({
    			    controller  : 'ModalExluirCtrl',
    			    templateUrl : 'modal-exluir-template.html',
    			    resolve     : {
    			        oObjeto: function () {
    			          return {
    			        	    fnCallback : function () {

    			        		    return AutenticacaoService
    			     		       .deletarFuncionalidade(_oFuncionalidade.id);
    			        	    }
    			            };
    			        }
			        }
			    })
			    .result.then(function (_oModalRetorno) {

			    	_oModalRetorno.$promise.then( 
				    		function (_oReturn) {
			        	var index = $scope.funcionalidades.indexOf(_oFuncionalidade);
    		    	   $scope.funcionalidades.splice(index, 1);
			        }, 
		                function (_oErroRetorno) { 
		            		console.error("Erro ao excluir!"); 
		            	}
		            );

			    }, function () {
			        console.info('Adeus modal: ' + new Date());
			    });
    	   };

    	   /**
    	    * 
    	    */
    	   $scope.fnEditarFuncionalidade = function (_funcionalidade) {
    		   $state.go('app.private.funcionalidadeForm', {funcionalidade: _funcionalidade, modulos:$scope.modulos});
    	   };

    	   $scope.fnSalvarFuncionalidade = function () {
    		   if($scope.funcionalidade.id != null) {
        		   AutenticacaoService
        		   .alterarFuncionalidade($scope.funcionalidade)
        		   .then(function(_oReturn) {
        			   $state.go('app.private.funcionalidade');
        		   });
    		   } else {
        		   AutenticacaoService
        		   .inserirFuncionalidade($scope.funcionalidade)
        		   .then(function(_oReturn) {
        			   $state.go('app.private.funcionalidade');
        		   });
    		   }
    	   };

       }]);
})()