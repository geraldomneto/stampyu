(function () { 'use strict;'

    angular
    .module('mensagemModule')
    .controller('MensagemController', [
           '$scope','MensagemService','uiGridConstants','$state','$uibModal','FabricanteService','RepresentanteService', 'growl',
                   function($scope,MensagemService,uiGridConstants,$state,$uibModal,FabricanteService, RepresentanteService,growl) {

    	$scope.aoData = [];

    	$scope.filtro = { titulo : "" , fabricanteId : undefined, representanteId : undefined };
    	
    	$scope.fnPesquisaFabricantes = function (sCriterioBusca) {

          	if(sCriterioBusca  && sCriterioBusca.length > 1){
  	        	return FabricanteService
  	            .buscar({ nome : sCriterioBusca})
  	            .then(function(_oReturn) {
  	            	$scope.fnBuscarRepresentante();
  	            	return _oReturn.data;
  	            });
          	}
          	
          	$scope.filtro.fabricante = undefined;
      		$scope.filtro.fabricanteId = undefined;
      		
          };
          
    	
    	$scope.fnBuscarRepresentante = function() {
    		
    		if($scope.filtro.fabricante){
        		$scope.filtro.fabricanteId  = $scope.filtro.fabricante.id;
        	
        		RepresentanteService
        		.buscarLight($scope.filtro).then(function(_oReturn) {
        				$scope.aRepresentantes = _oReturn.data;
        		});
        		
    		}
        	else if($scope.filtro.fabricante == undefined || $scope.filtro.fabricante == ""){
        		$scope.filtro.fabricanteId  = undefined;
        	}
    		
    			
    	}

        $scope.fnBuscar = function () {
            
        	if( $scope.filtro.titulo == "" && ($scope.filtro.fabricante == undefined || $scope.filtro.fabricante == "")){
        		growl.warning("É necessário selecionar algum filtro para fazer a pesquisa.",{title: 'Atenção!'});
        		return;
        	}
        	
        	if($scope.filtro.fabricante){
        		$scope.filtro.fabricanteId  = $scope.filtro.fabricante.id;
        	}
        	else if($scope.filtro.fabricante == undefined || $scope.filtro.fabricante == ""){
        		$scope.filtro.fabricanteId  = undefined;
        	}
        	
        	MensagemService
            .buscar($scope.filtro)
            .then(function(_oReturn) {
                $scope.aoData = _oReturn.data;
            });
        };

        $scope.fnNovo = function () {
            $state.go('app.private.mensagemEditar');
        };

        $scope.fnEditar = function (_oObj) {
            $state.go('app.private.mensagemEditar', {mensagem: _oObj});
        };

        $scope.fnExcluir = function (_oObj) {

            $uibModal
            .open({
                controller  : 'ModalExluirCtrl',
                templateUrl : 'modal-exluir-template.html',
                resolve     : {
 			        oObjeto: function () {
 			          return {
 			        	    fnCallback : function () {
 			        		    return MensagemService.excluir(_oObj.id);
 			        	    }
 			            };
 			        }
			    }
            })
            .result.then(function (_oModalRetorno) {

                _oModalRetorno.$promise.then(
                    function (_oReturn) {
                        var index = $scope.aoData.indexOf(_oObj);
                        $scope.aoData.splice(index, 1);
                    }, 
                    function (_oErroRetorno) { 
                        console.error("Erro ao excluir!"); 
                    }
                );

			}, function () {
			    console.info('Adeus modal: ' + new Date());
			});
 	   };

        $scope.gridMensagemOptions = {
            data                 : 'aoData',
            enableSorting        : true,
            paginationPageSizes  : [10],
            enableColumnResizing : true,
            columnDefs           : [
                {
                    name             : '.',
                    width            : 35,
                    cellClass        : 'text-center',
                    cellTemplate     : '<a href="" ng-click="grid.appScope.fnEditar(row.entity)"><span class="glyphicon glyphicon-edit"></span></a>',
                    enableSorting    : false,
                    enableColumnMenu : false
 		        },{
                    field       : 'titulo',
                 	displayName : 'Título'
                },{
                    field       : 'mensagem',
                 	displayName : 'Mensagem',
                 	sort        : {
                 	    priority  : 0,
                 	    direction : uiGridConstants.ASC
                    }
                },{
                    name             : '..',
                    width            : 35,
                    cellClass        : 'text-center',
                    cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluir(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
                    enableSorting    : false,
                    enableColumnMenu : false
            }],
            onRegisterApi : function (gridApi) {
                $scope.gridApi = gridApi;
            }
        };

    }]);
})()
