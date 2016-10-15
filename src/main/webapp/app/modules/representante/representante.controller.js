(function () { 'use strict;'

    angular
    .module('representanteModule')
    .controller('RepresentanteController', 
    ['$scope','RepresentanteService','FabricanteService','uiGridConstants','$state','$uibModal', 'growl',
     function($scope,RepresentanteService,FabricanteService,uiGridConstants,$state,$uibModal,growl) {

    	$scope.aoData = [];
    	$scope.aFabricantes = [];
    	$scope.quantity = 10;
    	$scope.filtro = { nome : "" , fabricanteId : undefined }; 
    	
    	$scope.fnPesquisaFabricantes = function (sCriterioBusca) {

          	if(sCriterioBusca  && sCriterioBusca.length > 1){
  	        	return FabricanteService
  	            .buscar({ nome : sCriterioBusca})
  	            .then(function(_oReturn) {
  	            	return _oReturn.data;
  	            });
          	}
          	
          	$scope.filtro.fabricante = undefined;
          	$scope.filtro.fabricanteId = undefined;
          	
          };
    	

        $scope.fnBuscar = function () {
        	
        	
        	
        	if($scope.filtro.nome == "" && $scope.filtro.fabricante == undefined ){
        		growl.warning("É necessário selecionar algum filtro para fazer a pesquisa.",{title: 'Atenção!'});
        		return;
        	}
        	
        	if($scope.filtro.fabricante){
        		$scope.filtro.fabricanteId  = $scope.filtro.fabricante.id;
        	}
        	else if($scope.filtro.fabricante == undefined || $scope.filtro.fabricante == ""){
        		$scope.filtro.fabricanteId  = undefined;
        	}
        	
            RepresentanteService
            .buscarLight($scope.filtro)
            .then(function(_oReturn) {
                $scope.aoData = _oReturn.data;
            });
            
            $scope.filtro = { nome : null , fabricanteNome : null , fabricanteId : null , fabricante : undefined }; 
            
        };

        
        RepresentanteService
        .buscarLight($scope.filtro)
        .then(function(_oReturn) {
            $scope.aoData = _oReturn.data;
        });
        
        $scope.fnNovo = function () {
            $state.go('app.private.representanteEditar');
        };

        $scope.fnEditar = function (_oObj) {
        	RepresentanteService.buscarRepresentante(_oObj.id).then(function(_oRetorno) { 
        		_oObj = _oRetorno.data;
 			   $state.go('app.private.representanteEditar', {representante: _oObj});
 		    })
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
 			        		    return RepresentanteService.excluir(_oObj.id);
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
			    console.info('fechando o modal: ' + new Date());
			});
 	   };


        $scope.gridRepresentanteOptions = {
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
                    field       : 'nome',
                 	displayName : 'Nome',
                 	sort        : {
                 	    priority  : 0,
                 	    direction : uiGridConstants.ASC
                    }
                },{
                	field       : 'fabricante.nome',
                 	displayName : 'Fabricante'
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
