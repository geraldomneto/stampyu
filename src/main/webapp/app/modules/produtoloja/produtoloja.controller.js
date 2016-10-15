(function () { 'use strict';

    angular
    .module('produtoLojaModule')
    .controller('ProdutoLojaController', 
    ['$scope','ProdutoLojaService','uiGridConstants','$state','$uibModal','ProdutoService','LojaService','growl',
     function($scope,ProdutoLojaService,uiGridConstants,$state,$uibModal,ProdutoService,LojaService,growl) {

        $scope.aoData = [];
        $scope.aLojas = [];
        $scope.aProdutos = [];
        $scope.filtro = { produtoId : undefined , lojaId : undefined  };
        
        
        $scope.fnPesquisaProdutos = function (sCriterioBusca) {

        	if(sCriterioBusca  && sCriterioBusca.length > 2){
	        	return ProdutoService
	            .buscar({ search : sCriterioBusca  })
	            .then(function(_oReturn) {
	            	return _oReturn.data;
	            });
        	}
        	
        	$scope.filtro.produtoId = undefined;
        	$scope.filtro.produto = undefined;
        	
        	
        };
        
        $scope.fnPesquisaLojas = function (sCriterioBusca) {

        	if(sCriterioBusca  && sCriterioBusca.length > 2){
	        	return LojaService
	            .buscar({ nome : sCriterioBusca })
	            .then(function(_oReturn) {
	            	return _oReturn.data;
	            });
        	}
        	
        	$scope.filtro.loja = undefined;
        	$scope.filtro.lojaId = undefined;
        	
        };
        
        
        $scope.fnBuscar = function () {

        	if(($scope.filtro.produto == undefined || $scope.filtro.produto == "")  && ($scope.filtro.loja == undefined || $scope.filtro.loja == "") ){
        		growl.warning("É necessário selecionar algum filtro para fazer a pesquisa.",{title: 'Atenção!'});
        		return;
        	}
        	
        	if($scope.filtro.produto){
        		$scope.filtro.produtoId  = $scope.filtro.produto.id;
        	}
        	else if($scope.filtro.produto == undefined || $scope.filtro.produto == ""){
        		$scope.filtro.produtoId  = undefined;
        	}
        	
        	if($scope.filtro.loja){
        		$scope.filtro.lojaId  = $scope.filtro.loja.id;
        	}
        	else if($scope.filtro.loja == undefined || $scope.filtro.loja == ""){
        		$scope.filtro.lojaId  = undefined;
        	}

        	
            ProdutoLojaService
            .buscar($scope.filtro)
            .then(function(_oReturn) {
                $scope.aoData = _oReturn.data;
            });
        };

        $scope.fnNovo = function () {
            $state.go('app.private.produtolojaEditar');
        };

        $scope.fnEditar = function (_oObj) {
            $state.go('app.private.produtolojaEditar', {produtoloja: _oObj});
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
 			        		    return ProdutoLojaService.excluir(_oObj.id);
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

        
        $scope.gridProdutoLojaOptions = {
            data                 : 'aoData',
            enableSorting        : true,
            paginationPageSizes  : [10],
            enableColumnResizing : true,
            columnDefs           : [
                /*{
                    name             : '.',
                    width            : 35,
                    cellClass        : 'text-center',
                    cellTemplate     : '<a href="" ng-click="grid.appScope.fnEditar(row.entity)"><span class="glyphicon glyphicon-edit"></span></a>',
                    enableSorting    : false,
                    enableColumnMenu : false
 		        },*/
                {
                	field       : 'nomeProduto',
                 	displayName : 'Produto',
                 	sort        : {
                 	    priority  : 0,
                 	    direction : uiGridConstants.ASC
                    }
                },{
                	field       : 'preco',
                 	displayName : 'Preço',
                 	cellFilter	: 'currency'
                },{
                	field       : 'estoqueProduto',
                 	displayName : 'Qtd. em estoque'
                },{
                	field       : 'nomeLoja',
                 	displayName : 'Loja'
                }
//                ,{
//                	name             : '..',
//                    width            : 35,
//                    cellClass        : 'text-center',
//                    cellTemplate     : '<a href="" ng-click="grid.appScope.fnExcluir(row.entity)"><span class="glyphicon glyphicon-remove"></span></a>',
//                    enableSorting    : false,
//                    enableColumnMenu : false
//            }
                ],
            onRegisterApi : function (gridApi) {
                $scope.gridApi = gridApi;
            }
        };

    }]);
})()
