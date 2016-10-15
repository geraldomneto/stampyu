(function () { 'use strict;'

    angular
    .module('produtoModule')
    .controller('ProdutoController', ['$scope','ProdutoService','uiGridConstants','$state','$uibModal','FabricanteService','growl', 
                                      function($scope,ProdutoService,uiGridConstants,$state,$uibModal,FabricanteService,growl) {

    	$scope.aoData = [];
    	$scope.aFabricantes = [];
    	$scope.filtro = { descricao : "" , ean : "" , fabricanteId : undefined };
    	
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
        	
        	if( $scope.filtro.descricao == "" && ($scope.filtro.fabricante == undefined || $scope.filtro.fabricante == "")  && $scope.filtro.ean == "" ){
        		growl.warning("É necessário selecionar algum filtro para fazer a pesquisa.",{title: 'Atenção!'});
        		return;
        	}
        	
        	if($scope.filtro.fabricante){
        		$scope.filtro.fabricanteId  = $scope.filtro.fabricante.id;
        	}
        	else if($scope.filtro.fabricante == undefined || $scope.filtro.fabricante == ""){
        		$scope.filtro.fabricanteId  = undefined;
        	}
        	
        	ProdutoService
            .buscar($scope.filtro)
            .then(function(_oReturn) {
                $scope.aoData = _oReturn.data;
            });
        };

        
        FabricanteService
    	.buscar()
    	.then(function(_oReturn) {
            $scope.aFabricantes = _oReturn.data;
        });
        
        $scope.fnNovo = function () {
            $state.go('app.private.produtoEditar');
        };

        $scope.fnEditar = function (_oObj) {
            $state.go('app.private.produtoEditar', {produto: _oObj});
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
 			        		    return ProdutoService.excluir(_oObj.id);
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

      

        $scope.gridprodutoOptions = {
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
                	field       : 'digitoVerificador',
                 	displayName : 'Codigo'
                },{
                	field       : 'ean',
                 	displayName : 'EAN'
                },
                {
                    field       : 'descricao',
                 	displayName : 'Descrição',
                 	sort        : {
                 	    priority  : 0,
                 	    direction : uiGridConstants.ASC
                    }
                },{
                	field       : 'preco',
                 	displayName : 'Preço',
                 	cellFilter	: 'currency'
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
