(function () { 'use strict';

    angular
    .module('produtoVendaModule')
    .controller('ProdutoVendaController', 
    ['$scope','ProdutoVendaService','uiGridConstants','$state','$uibModal','ProdutoService','MedicoService','growl', 
     function($scope,ProdutoVendaService,uiGridConstants,$state,$uibModal,ProdutoService,MedicoService,growl) {

    	$scope.aoData = [];
    	$scope.aMedicos = [];
    	$scope.aProdutos = [];
    	$scope.filtro = { produtoId : undefined , medicoId : undefined , ean : "" };

    	$scope.fnPesquisaProdutos = function (sCriterioBusca) {

    		console.log('chamou fnPesquisaRemota')
        	if(sCriterioBusca  && sCriterioBusca.length > 2){
	        	return ProdutoService
	            .buscar({ search : sCriterioBusca })
	            .then(function(_oReturn) {
	            	return _oReturn.data;
	            });
        	} 
        	
    		$scope.oProdutoSelecionado = undefined;
        	$scope.filtro.produtoId = undefined;
        	
        };

       
        $scope.fnPesquisaRemota = function (sCriterioBusca) {
        	
        	if(sCriterioBusca  && sCriterioBusca.length > 3){
	        	return MedicoService
	            .buscar({ search : sCriterioBusca })
	            .then(function(_oReturn) {
	            	return _oReturn.data;
	            });
	        	
	        	$scope.medicoSelected = undefined;
	        	$scope.filtro.medicoId = undefined;
        	}
        };
        

        $scope.fnBuscar = function () {
        	
        	
        	if( ($scope.oProdutoSelecionado == undefined || $scope.oProdutoSelecionado == "") && ($scope.medicoSelected == undefined || $scope.medicoSelected == "")){
        		growl.warning("É necessário selecionar algum filtro para fazer a pesquisa.",{title: 'Atenção!'});
        		return;
        	}
        	
        	if($scope.oProdutoSelecionado){
        		$scope.filtro.produtoId  = $scope.oProdutoSelecionado.id;
        	}
        	else if($scope.filtro.produto == undefined || $scope.filtro.produto == ""){
        		$scope.filtro.produtoId  = undefined;
        	}
        	
        	if ($scope.medicoSelected) {        		
        		$scope.filtro.medicoId = $scope.medicoSelected.id;
        	}
        	else if($scope.filtro.medicoSelected == undefined || $scope.filtro.medicoSelected == ""){
        		$scope.filtro.medicoId  = undefined;
        	}
        	
        	if($scope.filtro && $scope.filtro.dataVenda)
        		$scope.filtro.dataVenda = $scope.filtro.dataVenda.getTime();
            ProdutoVendaService
            .buscar($scope.filtro)
            .then(function(_oReturn) {
                $scope.aoData = _oReturn.data;
            });
        };

        $scope.fnNovo = function () {
            $state.go('app.private.produtovendaEditar');
        };

        $scope.fnEditar = function (_oObj) {
            $state.go('app.private.produtovendaEditar', {produtovenda: _oObj});
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
 			        		    return ProdutoVendaService.excluir(_oObj.id);
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

 	   
 	  $scope.dateOptions = {
          showWeeks     : false,
          formatYear    : 'yy',
          startingDay   : 1,
          dateDisabled  : false
      };

      $scope.datePickerPopup = {
          opened : false
      };

      $scope.fnOpen = function() {
          $scope.datePickerPopup.opened = true;
      };

        $scope.gridProdutoVendaOptions = {
            data                 : 'aoData',
            enableSorting        : true,
            paginationPageSizes  : [20],
            enableColumnResizing : true,
            columnDefs           : [
                {
                	field       : 'nomeProduto',
                 	displayName : 'Produto'
                 	
                },{
                	field       : 'medicoNome',
                 	displayName : 'Medico'
                },{
                	field       : 'medicoCodigo',
                 	displayName : 'CRM'
                },{
                	field       : 'quantidade',
                 	displayName : 'Quantidade'
                },{
                	field       : 'dataVenda',
                	cellFilter  : 'date:"dd/MM/yyyy"',
                 	displayName : 'Data Venda',
                 	sort        : {
                 	    priority  : 0,
                 	    direction : uiGridConstants.ASC
                    }
                },{
                	field       : 'dataPrescricao',
                	cellFilter  : 'date:"dd/MM/yyyy"',
                 	displayName : 'Data Prescriçao'
                }],
            onRegisterApi : function (gridApi) {
                $scope.gridApi = gridApi;
            }
        };

    }]);
})()
