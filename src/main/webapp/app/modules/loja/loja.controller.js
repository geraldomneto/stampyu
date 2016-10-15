(function () { 'use strict';

    angular
    .module('lojaModule')
    .controller('LojaController', 
    ['$scope','uiGridConstants','LojaService','$state','$uibModal', 
     function($scope,uiGridConstants,LojaService,$state,$uibModal) {

    	$scope.aoData = [];

        $scope.fnBuscar = function () {

            LojaService
            .buscar($scope.filtro)
            .then(function(_oReturn) {
                $scope.aoData = _oReturn.data;
            });
        };

        $scope.fnNovo = function () {
            $state.go('app.private.lojaEditar');
        };

        $scope.fnEditar = function (_oObj) {
            $state.go('app.private.lojaEditar', { loja : _oObj });
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
 			        		    return LojaService.excluir(_oObj.id);
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

       $scope.fnBuscar();

        $scope.gridLojaOptions = {
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
                	field       : 'logradouro', 
                 	displayName : 'Logradouro'
                },{
                	field       : 'numero', 
                 	displayName : 'Numero'
                },{
                	field       : 'bairro', 
                 	displayName : 'Bairro'
                },{
                	field       : 'cep', 
                 	displayName : 'CEP'
                },{
                	field       : 'idLojaAraujo', 
                 	displayName : 'Id Araujo'
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
